package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GdMergeItem
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.OrderItemPack
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.util.DateUtil
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.MoneyUtil

class SaleMgrService {
	
	static SaleMgrService INSTANCE = new SaleMgrService();

	static JCLogger LOGGER = JCLoggerFactory.getLogger('GOODS_ORDER_NOTIFY');
	
	static JcTemplate jc_template = JcTemplate.INSTANCE();
	
	static GoodsService goods_service = GoodsService.INSTANCE();
	static StockService stock_service = StockService.INSTANCE();
	static CmpGoodsService cmp_goods_service = CmpGoodsService.INSTANCE();
	static MergeGoodsService merge_goods_service = MergeGoodsService.INSTANCE();
	
	def pub_goods(StockOpBatch batch, WareHouse default_wh, OrderInfo x, List<OrderItem> order_items) {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		def fail_ops = [];
		for(OrderItem item : order_items) {
			if(item.tycode=='100') {
				def gs = [] ;
				GoodsInfo g = goods_service.get(item.goods_id, x.pid);
				GoodsSku sku = goods_service.get_sku(item.goods_sku_id);
				def nums = item.buy_num;
				gs.add([g, sku, nums, item]);
				def fail_ops_1 = stock_service.out_gs_form(batch, default_wh, null, gs, ts, x.id, GlobalHolder.token?.user, x.pid);
				if(fail_ops_1!=null&&!fail_ops_1.empty) {
					LOGGER.error('item.order_id=' + item.order_id + ', pub_stock_result=' + fail_ops_1);
					fail_ops.add(fail_ops_1);
				}
			} else if(item.tycode=='200') {
				//组合套餐类商品
				GoodsPack pack = cmp_goods_service.get_pack(item.goods_id, x.pid);
				def pack_nums = item.buy_num;	//购买的套餐的数量
				//开始取套餐包含的商品
				List<GoodsPackItem> pack_items = cmp_goods_service.get_pack_items(item.goods_id);
				List<OrderItemPack> pack_order_items = cmp_goods_service.find_order_pack_items(item);
				def pack_choosen = [];
				if(pack_order_items) {
					for(OrderItemPack oip in pack_order_items) {
						def map = [:];
						map['fid'] = oip.goods_id;
						map['fkid'] = oip.goods_sku_id;
						map['tyc'] = oip.tycode;
						map['num'] = oip.buy_num;
						pack_choosen.add(map);
					}
				} else {
					for(GoodsPackItem oip in pack_items) {
						def map = [:];
						map['fid'] = oip.fid
						map['fkid'] = oip.fkid;
						map['tyc'] = oip.it_code;
						map['num'] = oip.num;
						pack_choosen.add(map);
					}
				}
				def gs = [] ;
				for(gpi in pack_choosen) {
					def tycode = gpi['tyc'];
					if(tycode=='100') {
						//需要补充合成品情况
						GoodsInfo g = goods_service.get(gpi['fid']);
						GoodsSku sku = goods_service.get_sku(gpi['fkid']);
						def nums = gpi['num'];
						nums = pack_nums.multiply(nums);
						gs.add([g, sku, nums, item]);
						def fail_ops_1 = stock_service.out_gs_form(batch, default_wh, null, gs, ts, x.id, GlobalHolder.token?.user, x.pid);
						if(fail_ops_1!=null&&!fail_ops_1.empty) {
							LOGGER.error('item.order_id=' + item.order_id + ', pub_stock_result=' + fail_ops_1);
							fail_ops.add(fail_ops_1);
						}
					}
				}
			} else if(item.tycode=='300') {
				//合成品类商品
				GdMerge merge = merge_goods_service.get(item.goods_id, x.pid);
				def pack_nums = item.buy_num;	//购买的套餐的数量
				//开始取合成品内商品
				List<GdMergeItem> merge_items = merge_goods_service.get_items(item.goods_id);
				
				for(GdMergeItem gpi : merge_items) {
					GoodsInfo g = goods_service.get(gpi.fid);
					GoodsSku sku = goods_service.get_sku(gpi.fkid);
					def nums = gpi.num;
					nums = pack_nums.multiply(nums);
					def gs = [] ;
					gs.add([g, sku, nums, item]);
					def fail_ops_1 = stock_service.out_gs_form(batch, default_wh, null, gs, ts, x.id, GlobalHolder.token?.user, x.pid);
					if(fail_ops_1!=null&&!fail_ops_1.empty) {
						LOGGER.error('item.order_id=' + item.order_id + ', pub_stock_result=' + fail_ops_1);
						fail_ops.add(fail_ops_1);
					}
				}
			}
		}
		return fail_ops;
	}
	
	
	
	def refund_goods(StockOpBatch batch, WareHouse default_wh, OrderInfo x, List<OrderItem> order_items) {
		//库存回退首先要找到对应的销售出库form
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		def fail_ops = [];
		for(OrderItem item : order_items) {
			if(item.tycode=='100') {
				def gs = [] ;
				GoodsInfo g = goods_service.get(item.goods_id, x.pid);
				GoodsSku sku = goods_service.get_sku(item.goods_sku_id);
				def nums = item.buy_num;
				gs.add([g, sku, nums, item]);
				//def fail_ops_1 = stock_service.out_gs_form(batch, default_wh, null, gs, ts, x.id, GlobalHolder.token?.user);
				GsForm pub_form = null;			//暂时不查找
				this.in_gs_form(batch, default_wh, gs, x.id, GlobalHolder.token?.user, pub_form, x.pid);
			} else if(item.tycode=='200') {
				//组合套餐类商品
				GoodsPack pack = cmp_goods_service.get_pack(item.goods_id, x.pid);
				def pack_nums = item.buy_num;	//购买的套餐的数量
				//开始取套餐包含的商品
				List<GoodsPackItem> pack_items = cmp_goods_service.get_pack_items(item.goods_id);
				List<OrderItemPack> pack_order_items = cmp_goods_service.find_order_pack_items(item);
				def pack_choosen = [];
				if(pack_order_items) {
					for(OrderItemPack oip in pack_order_items) {
						def map = [:];
						map['fid'] = oip.goods_id;
						map['fkid'] = oip.goods_sku_id;
						map['tyc'] = oip.tycode;
						map['num'] = oip.buy_num;
						pack_choosen.add(map);
					}
				} else {
					for(GoodsPackItem oip in pack_items) {
						def map = [:];
						map['fid'] = oip.fid
						map['fkid'] = oip.fkid;
						map['tyc'] = oip.it_code;
						map['num'] = oip.num;
						pack_choosen.add(map);
					}
				}
				def gs = [] ;
				for(gpi in pack_choosen) {
					def tycode = gpi['tyc'];
					if(tycode=='100') {
						GoodsInfo g = goods_service.get(gpi['fid']);
						GoodsSku sku = goods_service.get_sku(gpi['fkid']);
						def nums = gpi['num'];
						nums = pack_nums.multiply(nums);
						gs.add([g, sku, nums, item]);
						//def fail_ops_1 = stock_service.out_gs_form(batch, default_wh, null, gs, ts, x.id, GlobalHolder.token?.user);
						GsForm pub_form = null;			//暂时不查找
						this.in_gs_form(batch, default_wh, gs, x.id, GlobalHolder.token?.user, pub_form, x.pid);
					}
				}
			} else if(item.tycode=='300') {
				//合成品类商品
				GdMerge merge = merge_goods_service.get(item.goods_id, x.pid);
				def pack_nums = item.buy_num;	//购买的套餐的数量
				//开始取合成品内商品
				List<GdMergeItem> merge_items = merge_goods_service.get_items(item.goods_id);
				
				def gs = [] ;
				for(GdMergeItem gpi : merge_items) {
					GoodsInfo g = goods_service.get(gpi.fid);
					GoodsSku sku = goods_service.get_sku(gpi.fkid);
					def nums = gpi.num;
					nums = pack_nums.multiply(nums);
					gs.add([g, sku, nums, item]);
					//def fail_ops_1 = stock_service.out_gs_form(batch, default_wh, null, gs, ts, x.id, GlobalHolder.token?.user);
					GsForm pub_form = null;			//暂时不查找
					this.in_gs_form(batch, default_wh, gs, x.id, GlobalHolder.token?.user, pub_form, x.pid);
				}
			}
		}
		return fail_ops;
	}
	
	
	
	/**
	 * 销售库存回退
	 * @param batch
	 * @param warehouse
	 * @param gs		[g, sku, nums, item]
	 * @param a_time
	 * @param remark
	 * @param happen_user
	 * @param original_form
	 * @return
	 */
	public def in_gs_form(StockOpBatch batch, WareHouse warehouse, List<?> gs, 
		def remark, AuthUser happen_user, GsForm original_form, def pid = GlobalHolder.proj.id) {
		Timestamp a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		//保存form
		GsForm form = new GsForm();
		form.a_time = a_time;
		form.batch_id = batch.id;
		form.batch_name = batch.bname;
		form.c_time = a_time;
		form.flag = 0;
		form.happen_time = a_time;
		form.pid = pid;
		form.remark = remark;
		form.wh_id = warehouse.id;
		form.wh_name = warehouse.name;
		if(happen_user!=null) {
			form.happen_uid = happen_user.id;
			form.happen_uname = happen_user.name;
		}
		AuthToken token = GlobalHolder.token;
		AuthUser op_user = token==null?null:token.user;
		if(op_user!=null) {
			form.op_uid = op_user.id;
			form.op_uname = op_user.name;
		}
		if(original_form!=null) {
			//如果需要则寻找回退表单
			form.origdata = original_form.id;
		}
		
		BigInteger form_id = jc_template.save(form);
		
		for(m in gs) {
			this.in_goods_batch(batch, warehouse, m, a_time, form_id);
		}
		form.id = form_id;
		return form;
	}
	
	/**
	 * 单个商品销售退单回库存
	 * @param batch
	 * @param house
	 * @param arr_param
	 * 协议数组
	 * [g, sku, nums, item]
	 *
	 * @param a_time
	 * @param form
	 * @return
	 */
	protected def in_goods_batch(StockOpBatch batch, WareHouse house, List<?> arr_param, Timestamp a_time, BigInteger form, def pid = GlobalHolder.proj.id) {
		GoodsInfo g = arr_param[0];
		GoodsSku sku = arr_param[1];
		BigDecimal num = arr_param[2];
		OrderItem order_item = arr_param[3];
			
		GoodsStock gs = new GoodsStock();
		gs.pid = pid;
		gs.a_time = a_time;
		gs.flag = 0;
		gs.goods_id = g.id;
		gs.goods_name = g.goods_name;
		gs.goods_no = g.goods_id;
		gs.goods_sku_id = sku==null?null:sku.id;
		gs.goods_sku_name = sku==null?null:sku.skus;
		gs.goods_sku_no = sku==null?null:sku.sku_no;
		gs.in_house = batch.in_house;
		gs.op_type = batch.op_type;
		gs.opbatchid = batch.id;
		gs.opbatchsn = batch.sn;
		gs.stock = num;
		
		//设置入库相关的单位规格信息 开始
		gs.unit = g.unit;
		gs.weight = g.weight;
		gs.spec_unit = g.spec_unit;
		//设置入库相关的单位规格信息 结束
		
		gs.form_id = form;
		gs.store_id = house.store_id;
		gs.store_name = house.store_name;
		gs.wh_id = house.id;
		gs.wh_name = house.name;
		gs.remark = order_item.id;
		gs.parainfo = JackSonBeanMapper.toJson(order_item);
		
		jc_template.save(gs);
	}
	
}
