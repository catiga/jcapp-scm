package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GfCatalog
import com.jeancoder.scm.ready.entity.GoodsForSale
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.OrderItemPack
import com.jeancoder.scm.ready.gen.GoodsNoGenerator
import com.jeancoder.scm.ready.util.GlobalHolder

class CmpGoodsService {

	private static final CmpGoodsService __instance__ = new CmpGoodsService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	CatalogService cat_service = CatalogService.INSTANCE();
	
	public static CmpGoodsService INSTANCE() {
		return __instance__;
	}
	
	def sale_catalog_for_goods(List<GoodsPack>  result, Catalog cat) {
		def buff_c_ids = cat_service.build_catalog_hierars(cat.id, '');
		Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for(GoodsPack g : result) {
			String sql = 'select * from GfCatalog where flag!=? and g_id=? and tycode=? and c_id=?';
			GfCatalog gc = jc_template.get(GfCatalog.class, sql, -1, g.id, '200', buff_c_ids);
			if(gc==null) {
				GfCatalog c = new GfCatalog();
				c.c_id = buff_c_ids;
				c.c_time = time;
				c.flag = 0;
				c.g_id = g.id;
				c.pid = g.pid;
				c.tycode = '200';
				jc_template.save(c);
			}
		}
	}
	
	/**
	 * @param goods_ids as List Object
	 * @return
	 */
	def find_goods_by_ids(List<?> goods_ids) {
		def params = [];
		String sql = 'select * from GoodsPack where flag!=? and id in (';
		params.add(-1);
		for(x in goods_ids) {
			sql = sql + '?,';
			params.add(x);
		}
		sql = sql.substring(0, sql.length() - 1) + ')';
		return jc_template.find(GoodsPack.class, sql, params.toArray());
	}
	
	public JcPage<GoodsPack> find_packs(JcPage<GoodsPack> page, String g_no = null, BigInteger pid = null) {
		String sql = 'select * from GoodsPack where flag!=?';
		def params = []; params.add(-1);
		if(pid==null && GlobalHolder.proj!=null) {
			pid = GlobalHolder.proj.id;
			sql += ' and pid=?'; params.add(pid);
		}
		if(g_no!=null) {
			sql += ' and sn like "' + g_no + '%"';
		}
		return jc_template.find(GoodsPack.class, page, sql, params.toArray());
	}
	
	public JcPage<GoodsPack> find_packs_by_num(JcPage<GoodsPack> page, def g_no) {
		String sql = 'select * from GoodsPack where flag!=?';
		def param = []; param.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?'; param.add(GlobalHolder.proj.id);
		}
		if(g_no!=null&&g_no!='') {
			sql += ' and (sn=? or id in (select gpid from GoodsPackItem where fno=?))';
			param.add(g_no);param.add(g_no);
		}
		sql += ' order by a_time desc';
		return jc_template.find(GoodsPack.class, page, sql, param.toArray());
	}
	
	public GoodsPack get_pack(def id, def pid = GlobalHolder.proj.id) {
		if(!id) {
			return null;
		}
		String sql = 'select * from GoodsPack where flag!=? and id=?';
		def params = []; params.add(-1); params.add(id);
		
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(!now_project.root) {
			sql += ' and (pid=? or pid=?)';
			params.add(now_project.id);
			params.add(now_project.dbpid);
		}
		
		return jc_template.get(GoodsPack.class, sql, params.toArray());
	}
	
	public List<GoodsPackItem> get_pack_items(def pack_id) {
		String sql = 'select * from GoodsPackItem where flag!=? and gpid=?';
		return jc_template.find(GoodsPackItem.class, sql, -1, pack_id);
	}
	
	public GoodsPackItem get_pack_item(def pack_item_id) {
		String sql = 'select * from GoodsPackItem where flag!=? and id=?';
		return jc_template.get(GoodsPackItem, sql, -1, pack_item_id);
	}
	
	public BigInteger save_pack_info(GoodsPack pack) {
		//新的生成编号
		if(pack.sn==null||pack.sn.trim()=='') {
			pack.sn = '200' + GoodsNoGenerator.generateNo();
		}
		//检查编号
		def sql = 'select * from GoodsPack where sn=?';
		GoodsPack exist_g_by_num = jc_template.get(GoodsPack, sql, pack.sn);
		if(exist_g_by_num!=null) {
			return -1;
		}
		
		return jc_template.save(pack);
	}
	
	public void update_pack(GoodsPack pack) {
		if(pack.sn==null||pack.sn.trim()=='') {
			pack.sn = '200' + GoodsNoGenerator.generateNo();
		}
		jc_template.update(pack);
	}
	
	public void save_pack_item(GoodsPack pack, List<?> items) {
		//首先取出该套餐内所有商品
		def sql = 'select * from GoodsPackItem where flag!=? and gpid=?';
		List<GoodsPackItem> exist_result = jc_template.find(GoodsPackItem, sql, -1, pack.id);
		Iterator<?> items_iters = items.iterator();
		while(items_iters.hasNext()) {
			def x = items_iters.next();
			def g = x[0];
			def s = x[1];
			BigDecimal num = x[2];
			def item_type = x[3];
			for(GoodsPackItem y in exist_result) {
				if(g.id==y.fid&&item_type==y.it_code) {
					items_iters.remove();
				}
			}
		}
		
		//首先清除现有item数据
//		sql = 'update GoodsPackItem set flag=-1 where gpid=?';
//		jc_template.batchExecute(sql, pack.id);
		
		Timestamp a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for(x in items) {
			def item_type = x[3];
			
			GoodsPackItem gpi = new GoodsPackItem();
			gpi.a_time = a_time;
			gpi.flag = 0;
			if(item_type=='100') {
				GoodsInfo g = x[0];
				GoodsSku s = x[1];
				BigDecimal num = x[2];
				gpi.cost_price = g.cost_price;
				gpi.rec_price = g.goods_original_price;
				gpi.sale_price = g.goods_price;
				gpi.fid = g.id;
				gpi.fno = g.goods_id;
				gpi.fname = g.goods_name;
				gpi.fkid = s==null?null:s.id;
				gpi.fkno = s==null?null:s.sku_no;
				gpi.fkname = s==null?null:s.skus;
				gpi.num = num;
				gpi.gpid = pack.id;
				gpi.unit = g.unit;
				gpi.it_code = '100';
			} else if(item_type=='500') {
				GdMerge g = x[0];
				BigDecimal num = x[2];
				gpi.cost_price = g.cost_price;
				gpi.rec_price = g.rec_price;
				gpi.sale_price = g.sale_price;
				gpi.fid = g.id;
				gpi.fno = g.sn;
				gpi.fname = g.name;
				gpi.num = num;
				gpi.gpid = pack.id;
				gpi.unit = g.unit;
				gpi.it_code = '500';
			}
			
			jc_template.save(gpi);
		}
	}
	
	/** 上下架操作 **/
	def for_sale(GoodsPack g, def all_proj) {
		String sql = 'select * from GoodsForSale where flag!=? and pid=? and g_id=? and tycode=?';
		GoodsForSale aim_one = jc_template.get(GoodsForSale.class, sql, -1, all_proj, g.id, '200');
		if(aim_one==null) {
			aim_one = new GoodsForSale();
			aim_one.g_id = g.id;
			aim_one.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			aim_one.c_time = aim_one.a_time;
			aim_one.flag = 0;
			aim_one.pid = all_proj;
			aim_one.tycode = '200';
			return jc_template.save(aim_one);
		}
	}
	def cancel_sale(def g_id, def typecode) {
		String sql = 'update GoodsForSale set flag = -1 where g_id=? and tycode=?';
		jc_template.batchExecute(sql, g_id, typecode);
		return true;
	}
	/** 上下架操作 **/
	
	def get_cat_goods(Catalog cat) {
		def cats = cat_service.build_catalog_hierars(cat.id, '');
		String sql = 'select * from GoodsPack where flag!=? and id in (select g_id from GfCatalog where flag!=? and c_id like "' + cats + ',%")';
		return jc_template.find(GoodsPack.class, sql, -1, -1);
	}
	
	//查找卖品替代品
	def find_pack_verts(GoodsPack belong_pack, def ids) {
		if(!ids) {return null}
		def params = [];
		String sql = 'select * from GoodsPackItemVert where flag!=? and gpid=? and id in (';
		params.add(-1); params.add(belong_pack.id);
		for(x in ids) {
			sql += '?,';
			params.add(x);
		}
		sql = sql.substring(0, sql.length() - 1) + ')';
		def result = JcTemplate.INSTANCE().find(GoodsPackItemVert, sql, params.toArray());
		return result;
	}
	
	//查找套餐内全部替代品
	List<GoodsPackItemVert> find_pack_all_verts(GoodsPack pack) {
		String sql = 'select * from GoodsPackItemVert where flag!=? and gpid=? order by item_id asc';
		return jc_template.find(GoodsPackItemVert, sql, -1, pack.id);
	}
	
	List<OrderItemPack> find_order_pack_items(OrderItem item) {
		def sql = 'select * from OrderItemPack where flag!=? and order_item_id=?';
		return JcTemplate.INSTANCE().find(OrderItemPack, sql, -1, item.id);
	}
}
