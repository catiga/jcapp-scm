package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.FrontOrderAddr
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.OrderItemPack
import com.jeancoder.scm.ready.entity.OrderNerm
import com.jeancoder.scm.ready.gen.OrderNoGenerator
import com.jeancoder.scm.ready.order.OrderConstants
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.OrderUtil

class OrderService {

	private static final OrderService __instance__ = new OrderService();

	JcTemplate jc_template = JcTemplate.INSTANCE();

	StockService stock_service = StockService.INSTANCE();

	public static OrderService INSTANCE() {
		return __instance__;
	}

	def find_user_order(def apid, def ds, def oss) {
		if(apid==null) {
			return null;
		}
		def sql = 'select * from OrderInfo where flag!=? and buyerid=?';
		def params = []; params.add(-1); params.add(apid);
		if(ds!=null) {
			sql += ' and dss=?';
		}
		if(oss!=null) {
			sql += ' and oss in (?)'; params.add(oss);
		}
		sql += ' order by a_time desc';
		return JcTemplate.INSTANCE().find(OrderInfo, sql, params.toArray());
	}
	
	OrderInfo get(def pid = GlobalHolder.proj.id, def order_id) {
		String sql = 'select * from OrderInfo where flag!=? and pid=? and id=?';
		return jc_template.get(OrderInfo.class, sql, -1, pid, order_id);
	}

	List<OrderInfo> find_order_by_num(def pid = GlobalHolder.proj.id, def order_num) {
		String sql = 'select * from OrderInfo where flag!=? and pid=? and order_no=?';
		return jc_template.find(OrderInfo.class, sql, -1, pid, order_num);
	}

	List<OrderItem> find_order_items(OrderInfo order) {
		String sql = 'select * from OrderItem where flag!=? and order_id=?';
		return jc_template.find(OrderItem.class, sql, -1, order.id);
	}

	JcPage<OrderInfo> find_goods(JcPage<OrderInfo> page, def o_num, def g_num, def oss) {
		def params = [];
		String sql = 'select * from OrderInfo where flag!=?';
		params.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?';
			params.add(GlobalHolder.proj.id);
		}

		if(o_num!=null&&!o_num.toString().trim().equals('')) {
			sql = sql + ' and order_no=?'
			params.add(o_num);
		} else {
			if(g_num!=null&&!g_num.toString().trim().equals('')) {
				sql = sql + ' and id in (select order_id from OrderItem where goods_no=?)';
				params.add(g_num);
			}
		}

		if(oss) {
			sql += ' and oss=?';
			params.add(oss);
		}

		sql = sql + ' order by a_time desc';
		return jc_template.find(OrderInfo.class, page, sql, params.toArray());
	}

	/**
	 * @param pre_orders [GoodsInfo, GoodsSku, num, remark]
	 * @return
	 */
	def create_order(def pre_orders, def sid, def sname, AuthUser user, def pid = GlobalHolder.proj.id) {
		OrderInfo o = new OrderInfo();
		def items = [];

		o.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		o.c_time = o.a_time;
		o.flag = 0;
		o.dss = '100';
		o.oss = OrderUtil._create_;
		o.order_no = '101' + OrderNoGenerator.generateNo();

		def total_amount = new BigDecimal(0);

		//计算金额
		for(x in pre_orders) {
			BigDecimal num = x[2];
			OrderItem item = new OrderItem();
			item.buy_num = num;
			item.flag = 0;
			if(x.size()>3) {
				item.remark = x[3];
			}
			if(x[0] in GoodsInfo) {
				GoodsInfo g = x[0];
				GoodsSku sku = x[1];
				item.goods_id = g.id;
				item.goods_name = g.goods_name;
				item.goods_no = g.goods_id;
				item.goods_sku_id = sku==null?0:sku.id;
				item.goods_sku_name = sku==null?null:sku.skus;
				item.goods_sku_no = sku==null?null:sku.sku_no;
				item.unit_amount = g.goods_price;
				item.pay_amount = g.goods_price;
				item.pic_url = g.goods_picturelink;
				item.tycode = '100';
			} else if(x[0] in GoodsPack) {
				GoodsPack g = x[0];
				def verts = x[1];
				def unit_amount = g.sale_price;

				//查找套餐用户选择替代品
				List<GoodsPackItem> standard_pack_items = CmpGoodsService.INSTANCE().get_pack_items(g.id);
				if(verts) {
					verts = CmpGoodsService.INSTANCE().find_pack_verts(g, verts);
					if(verts) {
						for(GoodsPackItemVert pack_item_verts in verts) {
							//重新计算金额
							unit_amount = unit_amount.add(pack_item_verts.fdd_price);
						}
					}
				}
				List<OrderItemPack> pack_sku = [];
				for(GoodsPackItem gpi : standard_pack_items) {
					OrderItemPack oip = new OrderItemPack(gpi);
					if(verts) {
						for(GoodsPackItemVert pack_item_verts in verts) {
							if(gpi.id==pack_item_verts.item_id) {
								//rebuild the aim item
								oip = new OrderItemPack(pack_item_verts);
								break;
							}
						}
					}
					pack_sku.add(oip);
				}

				def pay_amount = unit_amount;
				item.goods_id = g.id;
				item.goods_name = g.name;
				item.goods_no = g.sn;
				item.unit_amount = unit_amount;
				item.pay_amount = pay_amount;
				item.pic_url = g.pic_url;
				if(pack_sku) {
					item.verts = pack_sku;
				}
				item.tycode = '200';
			} else if (x[0] in GdMerge) {
				GdMerge g = x[0];
				item.goods_id = g.id;
				item.goods_name = g.name;
				item.goods_no = g.sn;
				item.unit_amount = g.sale_price;
				item.pay_amount = g.sale_price;
				item.pic_url = g.pic_url;
				item.tycode = '300';
			}
			items.add(item);
			total_amount = total_amount.add(num.multiply(item.unit_amount));
		}

		o.total_amount = total_amount;
		o.pay_amount = total_amount;
		o.pid = pid;
		if(sid!=null) {
			o.store_id = sid;
			o.store_name = sname;
		}

		if(user) {
			o.ouid = user.id;
			o.ouname = user.name;
		}
		BigInteger order_id = jc_template.save(o);
		for(OrderItem x in items) {
			x.order_id = order_id;
			x.id = jc_template.save(x);
			if(x.verts) {
				for(OrderItemPack y in x.verts) {
					y.order_id = order_id;
					y.order_item_id = x.id;
					jc_template.save(y);
				}
			}
		}
		o.id = order_id;
		return [o, items];
	}

	def get_goods_order(JcPage<OrderInfo> page,def pid,String start_data, String end_data,String oss,String flag) {
		String sql = "select * from OrderInfo Where 1=1 ";
		if (!StringUtil.isEmpty(start_data) && !StringUtil.isEmpty(end_data)) {
			sql +=  "and (a_time between '"+start_data+"' and '"+end_data+"') "
		}
		if (pid != null) {
			sql +=  " and pid="+ pid;
		}
		if(flag.equals("1")){
			sql += " and ouid is not null"
		}else{
			sql += " and ouid is null"
		}
		if (oss != null) {
			sql += " and oss in ("+oss+")";
		}
		sql += " order by a_time desc"
		return jc_template.find(OrderInfo.class, page,sql, null);
	}

	public List<OrderItem> get_goods_Item_order(def id){
		String sql = "select * from OrderItem where order_id = ?";
		return jc_template.find(OrderItem.class, sql, id);
	}

	public OrderInfo get_goods_order_info(def order_no){
		String sql = "select * from OrderInfo where order_no = ?";
		return jc_template.get(OrderInfo.class, sql, order_no);
	}

	public List<OrderItem> get_goods_order_item(def id){
		String sql = "select * from OrderItem where order_id =?";
		return jc_template.find(OrderItem.class, sql, id);
	}

	public List<OrderItem> get_goods_Item_order_with_pack(def id){
		String sql = "select * from OrderItem where order_id = ?";
		List<OrderItem> order_items = jc_template.find(OrderItem.class, sql, id);
		if(order_items) {
			for(OrderItem item in order_items) {
				if(item.tycode=='200') {
					//套餐则要查明细
					sql = 'select * from OrderItemPack where flag!=? and order_item_id=?';
					List<OrderItemPack> o_packs = jc_template.find(OrderItemPack, sql, -1, item.id);
					item.verts = o_packs;
				}
			}
		}
		return order_items;
	}

	/**
	 * 创建用户订单
	 * @param pre_orders
	 * @param store
	 * @param session
	 * @return
	 */
	def create_order_front(def pre_orders, StoreInfo store, def code, FrontOrderAddr order_addr, def pid = GlobalHolder.proj.id) {
		OrderInfo o = new OrderInfo();
		def items = [];

		o.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		o.c_time = o.a_time;
		o.flag = 0;
		o.dss = code;
		o.oss = OrderUtil._create_;
		o.order_no = code + OrderNoGenerator.generateNo();

		def total_amount = new BigDecimal(0);

		//计算金额
		for(x in pre_orders) {
			BigDecimal num = x[2];
			OrderItem item = new OrderItem();
			item.buy_num = num;
			item.flag = 0;
			if(x.size()>3) {
				item.remark = x[3];
			}
			if(x[0] in GoodsInfo) {
				GoodsInfo g = x[0];
				GoodsSku sku = x[1];
				item.goods_id = g.id;
				item.goods_name = g.goods_name;
				item.goods_no = g.goods_id;
				item.goods_sku_id = sku==null?0:sku.id;
				item.goods_sku_name = sku==null?null:sku.skus;
				item.goods_sku_no = sku==null?null:sku.sku_no;
				item.unit_amount = g.goods_price;
				item.pay_amount = g.goods_price;
				item.tycode = '100';
				item.pic_url = g.goods_picturelink;
			} else if(x[0] in GoodsPack) {
				GoodsPack g = x[0];
				item.goods_id = g.id;
				item.goods_name = g.name;
				item.goods_no = g.sn;
				item.unit_amount = g.sale_price;
				item.pay_amount = g.sale_price;
				item.tycode = '200';
				item.pic_url = g.pic_url;
			} else if (x[0] in GdMerge) {
				GdMerge g = x[0];
				item.goods_id = g.id;
				item.goods_name = g.name;
				item.goods_no = g.sn;
				item.unit_amount = g.sale_price;
				item.pay_amount = g.sale_price;
				item.tycode = '300';
				item.pic_url = g.pic_url;
			}
			items.add(item);
			total_amount = total_amount.add(num.multiply(item.unit_amount));
		}

		o.total_amount = total_amount;
		o.pay_amount = total_amount;
		o.pid = pid;
		if(store!=null) {
			o.store_id = store.id;
			o.store_name = store.store_name;
		}

		o.buyerid = order_addr.session.ap_id;
		o.buyerprovincecode = order_addr.provincecode;
		o.buyerprovincename = order_addr.provincename;
		o.buyercitycode = order_addr.citycode;
		o.buyercityname = order_addr.cityname;
		o.buyerzonecode = order_addr.zonecode;
		o.buyerzonename = order_addr.zonename;
		o.buyeraddr = order_addr.address;
		if(order_addr.point)
			o.buyerpoint = order_addr.point;
		BigInteger order_id = jc_template.save(o);
		for(x in items) {
			x.order_id = order_id;
			x.id = jc_template.save(x);
		}
		o.id = order_id;
		return [o, items];
	}
	OrderInfo get_item(OrderInfo order){
		String sql = 'select * from OrderInfo where pid = ? and order_no =? ';
		return jc_template.get(OrderInfo, sql, order.pid,order.order_no)
	}
	OrderInfo update_order_status(OrderInfo order){
		OrderInfo item = get_item(order);
		item.oss = OrderConstants._order_status_closed_;
		item.c_time = new Timestamp(new Date().getTime());
		jc_template.update(item);
		return item;
	}

	//订单备注各种号码牌
	def update_order_nerm(OrderInfo order, String caller_num, String table_num, String room_num) {
		OrderNerm nerm = jc_template.get(OrderNerm, 'select * from OrderNerm where flag!=? and order_id=?', -1, order.id);
		boolean update = true;
		if(!nerm) {
			nerm = new OrderNerm();
			nerm.flag = 0;
			nerm.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			nerm.order_id = order.id;
			update = false;
		}
		nerm.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		nerm.caller_num = caller_num;
		nerm.table_num = table_num;
		nerm.room_num = room_num;
		if(update) {
			jc_template.update(nerm);
		} else {
			jc_template.save(nerm);
		}
	}

	//根据订单号取号码
	OrderNerm get_order_nerm(OrderInfo order) {
		OrderNerm nerm = jc_template.get(OrderNerm, 'select * from OrderNerm where flag!=? and order_id=?', -1, order.id);
		return nerm;
	}
}
