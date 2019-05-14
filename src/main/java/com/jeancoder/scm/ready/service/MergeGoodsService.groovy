package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GdMergeItem
import com.jeancoder.scm.ready.entity.GfCatalog
import com.jeancoder.scm.ready.entity.GoodsForSale
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.gen.GoodsNoGenerator
import com.jeancoder.scm.ready.util.GlobalHolder

class MergeGoodsService {

	private static final MergeGoodsService __instance__ = new MergeGoodsService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	CatalogService cat_service = CatalogService.INSTANCE();
	
	public static MergeGoodsService INSTANCE() {
		return __instance__;
	}
	
	def sale_catalog_for_goods(List<GdMerge>  result, Catalog cat) {
		def buff_c_ids = cat_service.build_catalog_hierars(cat.id, '');
		Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for(GdMerge g : result) {
			String sql = 'select * from GfCatalog where flag!=? and g_id=? and tycode=? and c_id=?';
			GfCatalog gc = jc_template.get(GfCatalog.class, sql, -1, g.id, '300', buff_c_ids);
			if(gc==null) {
				GfCatalog c = new GfCatalog();
				c.c_id = buff_c_ids;
				c.c_time = time;
				c.flag = 0;
				c.g_id = g.id;
				c.pid = g.pid;
				c.tycode = '300';
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
		String sql = 'select * from GdMerge where flag!=? and id in (';
		params.add(-1);
		for(x in goods_ids) {
			sql = sql + '?,';
			params.add(x);
		}
		sql = sql.substring(0, sql.length() - 1) + ')';
		return jc_template.find(GdMerge.class, sql, params.toArray());
	}
	
	public JcPage<GdMerge> find(JcPage<GdMerge> page) {
		String sql = 'select * from GdMerge where flag!=? and pid=? order by a_time desc';
		return jc_template.find(GdMerge.class, page, sql, -1, GlobalHolder.proj.id);
	}
	
	public JcPage<GdMerge> find_by_num(JcPage<GdMerge> page, def g_no) {
		String sql = 'select * from GdMerge where flag!=?';
		def param = []; param.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?'; param.add(GlobalHolder.proj.id);
		}
		if(g_no!=null&&g_no!='') {
			sql += ' and (sn=? or id in (select gpid from GdMergeItem where fno=?))';
			param.add(g_no);param.add(g_no);
		}
		sql += ' order by a_time desc';
		return jc_template.find(GdMerge, page, sql, param.toArray());
	}
	
	public GdMerge get(def id, def pid = GlobalHolder.proj.id) {
		String sql = 'select * from GdMerge where flag!=? and id=?';
		def params = []; params.add(-1); params.add(id);
		
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(!now_project.root) {
			sql += ' and (pid=? or pid=?)';
			params.add(now_project.id);
			params.add(now_project.dbpid);
		}
		
		return jc_template.get(GdMerge.class, sql, params.toArray());
	}
	
	public List<GdMergeItem> get_items(def pack_id) {
		String sql = 'select * from GdMergeItem where flag!=? and gpid=?';
		return jc_template.find(GdMergeItem.class, sql, -1, pack_id);
	}
	
	public BigInteger save_merge(GdMerge pack) {
		//新的生成编号
		if(pack.sn==null||pack.sn.trim()=='') {
			pack.sn = '300' + GoodsNoGenerator.generateNo();
		}
		//检查编号
		def sql = 'select * from GdMerge where sn=?';
		GdMerge exist_g_by_num = jc_template.get(GdMerge, sql, pack.sn);
		if(exist_g_by_num!=null) {
			return -1;
		}
		return jc_template.save(pack);
	}
	
	public void update_merge(GdMerge pack) {
		if(pack.sn==null||pack.sn.trim()=='') {
			pack.sn = '300' + GoodsNoGenerator.generateNo();
		}
		jc_template.update(pack);
	}
	
	public void save_merge_item(GdMerge pack, List<?> items) {
		//首先清除现有item数据
		String sql = 'update GdMergeItem set flag=-1 where gpid=?';
		jc_template.batchExecute(sql, pack.id);
		Timestamp a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for(x in items) {
			GoodsInfo g = x[0];
			GoodsSku s = x[1];
			BigDecimal num = x[2];
			
			GdMergeItem gpi = new GdMergeItem();
			gpi.a_time = a_time;
			gpi.flag = 0;
			
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
			gpi.spec_unit = g.spec_unit;
			
			gpi.gpid = pack.id;
			
			gpi.unit = g.unit;
			
			jc_template.save(gpi);
		}
	}
	
	/** 上下架操作 **/
	def for_sale(GdMerge g, def all_proj) {
		String sql = 'select * from GoodsForSale where flag!=? and pid=? and g_id=? and tycode=?';
		GoodsForSale aim_one = jc_template.get(GoodsForSale.class, sql, -1, all_proj, g.id, '300');
		if(aim_one==null) {
			aim_one = new GoodsForSale();
			aim_one.g_id = g.id;
			aim_one.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			aim_one.c_time = aim_one.a_time;
			aim_one.flag = 0;
			aim_one.pid = all_proj;
			aim_one.tycode = '300';
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
		String sql = 'select * from GdMerge where flag!=? and id in (select g_id from GfCatalog where flag!=? and c_id like "' + cats + ',%")';
		return jc_template.find(GdMerge.class, sql, -1, -1);
	}
	
}
