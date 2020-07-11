package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GfCatalog
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsForSale
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsModelProp
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.gen.GoodsNoGenerator
import com.jeancoder.scm.ready.util.GlobalHolder

class GoodsService {
	
	private static final GoodsService __instance__ = new GoodsService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	CatalogService cat_service = CatalogService.INSTANCE();
	
	JCLogger logger = LoggerSource.getLogger();
	
	public static GoodsService INSTANCE() {
		return __instance__;
	}

	public JcPage<GoodsInfo> find(JcPage<GoodsInfo> page, def pid = GlobalHolder.proj.id) {
		def sql = 'select * from GoodsInfo where flag!=?';
		def param = []; param.add(-1);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(now_project.root!=1) {
			sql += ' and (proj_id=? or proj_id=?)';
			param.add(now_project.id); param.add(BigInteger.valueOf(1l));
		}
		
		page = jc_template.find(GoodsInfo, page, sql, param.toArray());
		return page;
	}
	
	public JcPage<GoodsInfo> find_by_num(JcPage<GoodsInfo> page, def g_no, def pid = GlobalHolder.proj.id) {
		def sql = 'select * from GoodsInfo where flag!=?';
		def param = [];
		param.add(-1);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(now_project.root!=1) {
			sql += ' and (proj_id=? or proj_id=?)';
			param.add(now_project.id);
			param.add(BigInteger.valueOf(1l));
		}
		if(g_no!=null&&g_no!='') {
			sql += ' and ( goods_id=? or goods_name=? or goods_out_no=? )'; 
			param.add(g_no);
			param.add(g_no);
			param.add(g_no);
		}
		sql += ' order by a_time desc';
		page = jc_template.find(GoodsInfo, page, sql, param.toArray());
		for(x in page.result) {
			if(x.goods_picturelink!=null) {
				if(!x.goods_picturelink.startsWith('http')) {
					//x.goods_picturelink = 'http://pe1s.static.pdr365.com/' + x.goods_picturelink;
					//x.goods_picturelink = 'https://cdn.iplaysky.com/' + x.goods_picturelink;
					x.goods_picturelink = GlobalHolder.INSTANCE.img_path() + '/' + x.goods_picturelink;
				}
			}
		}
		return page;
	}
	
	public GoodsInfo get(def id, def pid = GlobalHolder.proj.id) {
		def sql = 'select * from GoodsInfo where id=?';
		def params = []; 
		params.add(id);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(!now_project.root) {
			sql += ' and (proj_id=? or proj_id=?)';
			params.add(now_project.id);
			params.add(now_project.dbpid);
		}
		return jc_template.get(GoodsInfo.class, sql, params.toArray());
	}
	
	public List<GoodsInfo> get_by_num(def goods_num, def pid = GlobalHolder.proj.id) {
		if(goods_num==null||goods_num.toString().trim().equals('')) {
			return null;
		}
		String sql = 'select * from GoodsInfo where ( goods_id=? or goods_out_no=? or goods_code=? ) and flag!=?';
		def params = []; params.add(goods_num); params.add(goods_num); params.add(goods_num); params.add(-1);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(!now_project.root) {
			sql += ' and (proj_id=? or proj_id=?)';
			params.add(now_project.id);
			params.add(now_project.dbpid);
		}
		return jc_template.find(GoodsInfo.class, sql, params.toArray());
	}
	
	public void update(GoodsInfo entity) {
		entity.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		if(entity.goods_id==null||entity.goods_id.trim()=='') {
			entity.goods_id = '100' + GoodsNoGenerator.generateNo();
		}
		jc_template.update(entity);
	}
	
	public BigInteger save(GoodsInfo entity) {
		entity.proj_id = GlobalHolder.proj.id;
		entity.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		entity.flag = 0;
		entity.c_time = entity.a_time;
		//新的生成编号
		if(entity.goods_id==null||entity.goods_id.trim()=='') {
			entity.goods_id = '100' + GoodsNoGenerator.generateNo();
		}
		//检查编号
		def sql = 'select * from GoodsInfo where goods_id=?';
		GoodsInfo exist_g_by_num = jc_template.get(GoodsInfo, sql, entity.goods_id);
		if(exist_g_by_num!=null) {
			return -1;
		}
		BigInteger id = jc_template.save(entity);
		return id;
	}
	
	public GoodsSku get_sku(def id) {
		if(!id) {
			return null;
		}
		try {
			String sql = 'select * from GoodsSku where flag!=? and id=?';
			return jc_template.get(GoodsSku.class, sql, -1, new BigDecimal(id));
		}catch(any){
			return null;
		}
	}
	
	public List<GoodsSku> get_sku_by_no(def sku_no) {
		if(sku_no==null||sku_no.toString().trim().equals('')) {
			return null;
		}
		String sql = 'select * from GoodsSku where flag!=? and sku_no=?';
		return jc_template.find(GoodsSku.class, sql, -1, sku_no);
	}
	
	public List<GoodsSku> find_goods_skus(GoodsInfo goods) {
		String sql = 'select * from GoodsSku where flag!=?';
		def params = [];
		params.add(-1);
		if(goods!=null) {
			sql = sql + ' and goods_id=?';
			params.add(goods.id);
		}
		return jc_template.find(GoodsSku.class, sql, params.toArray());
	}
	
	public BigInteger save_sku(GoodsSku sku) {
		return jc_template.save(sku);
	}
	
	public void update_sku(GoodsSku sku) {
		jc_template.update(sku);
	}
	
	/**
	 * 获取指定商品SPU，指定仓库，下的所有SKU及数量
	 * @param goods
	 * @param ware
	 * @return
	 */
	public List<GoodsStock> find_goos_stock(GoodsInfo goods, WareHouse ware) {
		String sql = 'select * from GoodsStock where flag!=?';
		def params = [];
		params.add(-1);
		if(goods!=null) {
			sql = sql + ' and goods_id=?';
			params.add(goods.id);
		}
		if(ware!=null) {
			sql = sql + ' and wh_id=? ';
			params.add(ware.id);
		}
		return jc_template.find(GoodsStock.class, sql, params.toArray());
	}
	
	/**
	 * 获取指定商品SPU，指定仓库，下的所有SKU及数量
	 * @param goods
	 * @param ware
	 * @return
	 */
	public List<GoodsStock> find_goos_stock(WareHouse ware) {
		String sql = 'select * from GoodsStock where flag!=?';
		def params = [];
		params.add(-1);
		if(ware!=null) {
			sql = sql + ' and wh_id=?';
			params.add(ware.id);
		}
		return jc_template.find(GoodsStock, sql, params.toArray());
	}
	
	/**
	 * 获取指定商品SKU，指定仓库，下的变动数量
	 * @param goods
	 * @param ware
	 * @return
	 */
	public List<GoodsStock> find_goos_sku_stock(GoodsInfo goods, GoodsSku sku, WareHouse ware, String orderby) {
		String sql = 'select * from GoodsStock where flag!=?';
		def params = [];
		params.add(-1);
		if(ware!=null) {
			sql = sql + ' and wh_id=?';
			params.add(ware.id);
		}
		if(goods!=null) {
			sql = sql + ' and goods_id=?';
			params.add(goods.id);
		}
		if(sku!=null) {
			sql = sql + ' and goods_sku_id=?';
			params.add(sku.id);
		}
		if(orderby!=null) {
			sql = sql + ' order by ' + orderby;
		} else {
			sql = sql + ' order by in_house asc, op_type asc';
		}
		return jc_template.find(GoodsStock.class, sql, params.toArray());
	}
	
	/**
	 * 支持按照商品，单品，统计库存，可以不传入仓库信息
	 * 统计剩余库存
	 * @param goods
	 * @param sku
	 * @param ware
	 * @return
	 */
	public BigDecimal get_goods_sku_stock(GoodsInfo goods, GoodsSku sku, WareHouse ware) {
		/*
		if(ware==null) {
			return new BigDecimal(0);
		}
		*/
		String sql = 'select sum(stock) from GoodsStock where flag!=? and goods_id=?';
		def params = [];
		params.add(-1);
		params.add(goods.id);
		if(ware!=null) {
			sql = sql + ' and wh_id=?';
			params.add(ware.id);
		}
		if(sku!=null) {
			sql = sql + ' and goods_sku_id=?';
			params.add(sku.id);
		}
		BigDecimal x_stock = jc_template.get(BigDecimal.class, sql, params.toArray());
		if(x_stock==null) {
			x_stock = new BigDecimal(0);
		}
		return x_stock;
	}
	
	/**
	 * 统计剩余库存
	 * 修改以支持不按仓库统计
	 * @param goods
	 * @param sku
	 * @param ware
	 * @return
	 */
	public BigDecimal get_goods_sku_stock_by_id(BigInteger goods_id, BigInteger sku_id, WareHouse ware) {
//		if(ware==null) {
//			return new BigDecimal(0);
//		}
		String sql = 'select sum(stock) from GoodsStock where flag!=? and goods_id=?';
		def params = [];
		params.add(-1);
		params.add(goods_id);
		if(ware!=null) {
			sql = sql + ' and wh_id=?';
			params.add(ware.id);
		}
		if(sku_id!=null&&sku_id>0) {
			sql = sql + ' and goods_sku_id=?';
			params.add(sku_id);
		}
		return jc_template.get(BigDecimal.class, sql, params.toArray());
	}
	
	public GoodsContent get_content(def goods_id) {
		String sql = 'select * from GoodsContent where flag!=? and goods_id=?';
		return jc_template.get(GoodsContent.class, sql, -1, goods_id);
	}
	
	public BigInteger save_content(GoodsContent content) {
		BigInteger id = jc_template.save(content);
		return id;
	}
	
	public void update_content(GoodsContent content) {
		jc_template.update(content);
	}
	
	public List<GoodsModel> find_goods_models() {
		String sql = "select * from GoodsModel where flag!=?";
		def params = []; params.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and (proj_id=?)';
			params.add(GlobalHolder.proj.id);
		}
		sql += ' order by c_time desc';
		List<GoodsModel> result = jc_template.find(GoodsModel, sql, params.toArray());
		return result;
	}
	
	public GoodsModel get_model(def id) {
		String sql = "select * from GoodsModel where flag!=? and id=?";
		def params = []; params.add(-1); params.add(id);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and (proj_id=?)';
			params.add(GlobalHolder.proj.id);
		}
		return jc_template.get(GoodsModel.class, sql, params.toArray());
	}
	
	public BigInteger save_model(GoodsModel model) {
		model.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		model.flag = 0;
		model.proj_id = GlobalHolder.proj.id;
		BigInteger id = jc_template.save(model);
		return id;
	}
	
	public void update_model(GoodsModel model) {
		jc_template.update(model);
	}
	
	public List<GoodsModelProp> find_gm_props(def gmid) {
		String sql = 'select * from GoodsModelProp where flag!=? and gm_id=?';
		return jc_template.find(GoodsModelProp.class, sql, -1, gmid);
	}
	
	public void save_gm_props(GoodsModel model, def props) {
		String sql = 'update GoodsModelProp set flag=-1 where gm_id=?';
		jc_template.batchExecute(sql, model.id);
		
		for(x in props) {
			GoodsModelProp p = new GoodsModelProp();
			p.attr_k = x;
			p.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			p.flag = 0;
			p.gm_id = model.id;
			jc_template.save(p);
		}
	}
	
	def save_img(GoodsImg obj) {
		BigInteger id = jc_template.save(obj);
		obj.id = id;
		return obj;
	}
	
	public List<GoodsImg> find_goods_imgs(def goods_id, def tyc) {
		String sql = 'select * from GoodsImg where flag!=? and goods_id=? and tycode=?';
		if(!tyc) {
			tyc = '100';
		}
		return jc_template.find(GoodsImg.class, sql, -1, goods_id, tyc);
	}
	
	/**
	 * @param goods_ids as List Object
	 * @return
	 */
	def find_goods_by_ids(List<?> goods_ids) {
		def params = [];
		String sql = 'select * from GoodsInfo where flag!=? and id in (';
		params.add(-1);
		for(x in goods_ids) {
			sql = sql + '?,';
			params.add(x);
		}
		sql = sql.substring(0, sql.length() - 1) + ')';
		return jc_template.find(GoodsInfo.class, sql, params.toArray());
	}
	
	/** 上下架操作 **/
	def for_sale(GoodsInfo g, def all_proj) {
		String sql = 'select * from GoodsForSale where flag!=? and pid=? and g_id=? and tycode=?';
		GoodsForSale aim_one = jc_template.get(GoodsForSale.class, sql, -1, all_proj, g.id, '100');
		if(aim_one==null) {
			aim_one = new GoodsForSale();
			aim_one.g_id = g.id;
			aim_one.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			aim_one.c_time = aim_one.a_time;
			aim_one.flag = 0;
			aim_one.pid = all_proj;
			aim_one.tycode = '100';
			return jc_template.save(aim_one);
		}
	}
	def cancel_sale(def g_id, def typecode) {
		//首先删除该在售商品
		String sql = 'update GoodsForSale set flag=-1 where g_id=? and tycode=?';
		jc_template.batchExecute(sql, g_id, typecode);
		//其次删除该商品对应的在售分类
		sql = 'update GfCatalog set flag=-1 where g_id=? and tycode=?';
		jc_template.batchExecute(sql, g_id, typecode);
		return true;
	}
	/** 上下架操作 **/
	
	/**
	 * 获取在售商品列表
	 * @param page
	 * @return
	 */
	def for_sale_list(JcPage<GoodsInfo> page, def g_no) {
		String sql = 'select * from GoodsInfo where flag!=?';
		def param = []; param.add(-1);
		sql += ' and id in (select g_id from GoodsForSale where flag!=? and tycode=?)';
		param.add(-1);param.add('100');
		if(g_no!=null&&g_no!='') {
			sql += ' and goods_id=?'; param.add(g_no);
		}
		if(GlobalHolder.proj.root!=1) {
//			sql += ' and proj_id=?'; 
//			param.add(GlobalHolder.proj.id);
			sql += ' and (proj_id=? or proj_id=?)';
			param.add(GlobalHolder.proj.id);
			param.add(BigInteger.valueOf(1l));
		}
		return jc_template.find(GoodsInfo, page, sql, param.toArray());
	}
	
	def for_sale_pack(JcPage<GoodsPack> page, def g_no) {
		String sql = 'select * from GoodsPack where flag!=? and id in (select g_id from GoodsForSale where flag!=? and tycode=?)';
		def param = [];
		param.add(-1); param.add(-1); param.add('200');
		if(g_no!=null&&g_no!='') {
			sql += ' and sn=?';
			param.add(g_no);
		}
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?'; param.add(GlobalHolder.proj.id);
		}
		return jc_template.find(GoodsPack, page, sql, param.toArray());
	}
	
	def for_sale_merge(JcPage<GdMerge> page, def g_no) {
		String sql = 'select * from GdMerge where flag!=? and id in (select g_id from GoodsForSale where flag!=? and tycode=?)';
		def param = [];
		param.add(-1); param.add(-1); param.add('300');
		if(g_no!=null&&g_no!='') {
			sql += ' and sn=?';
			param.add(g_no);
		}
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?'; param.add(GlobalHolder.proj.id);
		}
		return jc_template.find(GdMerge, page, sql, param.toArray());
	}
	
	def sale_catalog_for_goods(List<GoodsInfo>  result, Catalog cat) {
		def buff_c_ids = cat_service.build_catalog_hierars(cat.id, '');
		Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for(GoodsInfo g : result) {
			String sql = 'select * from GfCatalog where flag!=? and g_id=? and tycode=? and c_id=?';
			GfCatalog gc = jc_template.get(GfCatalog.class, sql, -1, g.id, '100', buff_c_ids);
			if(gc==null) {
				GfCatalog c = new GfCatalog();
				c.c_id = buff_c_ids;
				c.c_time = time;
				c.flag = 0;
				c.g_id = g.id;
				c.pid = g.proj_id;
				c.tycode = '100';
				jc_template.save(c);
			}
		}
	}
	
	def get_cat_goods(Catalog cat, def pid = GlobalHolder.proj.id) {
		if(cat.proj_id!=pid) {
			return null;
		}
		def cats = cat_service.build_catalog_hierars(cat.id, '');
		logger.info('get_cat_goods:' + cats);
		String sql = 'select * from GoodsInfo where flag!=? and id in (select g_id from GfCatalog where flag!=? and c_id like "' + cats + '%")';
		return jc_template.find(GoodsInfo.class, sql, -1, -1);
	}
	
	def get_goods_cats(def g_id, def tyc) {
		String sql = 'select c_id from GoodsCatalog where flag!=? and g_id=? and tycode=?';
		return jc_template.find(String.class, sql, -1, g_id, tyc);
	}
}
