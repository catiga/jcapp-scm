package com.jeancoder.scm.ready.service

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.util.GlobalHolder

class CatalogService {

	static final JCLogger logger = LoggerSource.getLogger();
	
	private static final CatalogService __instance__ = new CatalogService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public static CatalogService INSTANCE() {
		return __instance__;
	}
	
	public List<Catalog> find_by_parent_with_cats(def pid = GlobalHolder.proj.id, def parent, def cat_son_ids) {
		String sql = 'select * from Catalog where flag!=? and proj_id=?';
		def params = [];
		params.add(-1);
		params.add(pid);
		if(parent!=null) {
			sql = sql + ' and parent_id=?';
			params.add(parent.id);
		}
		/* 
		else {
			sql = sql + ' and parent_id is null';
		}
		*/
		sql = sql + ' and (show_dsc=?'; params.add('0');	//取全部显示的
		if(cat_son_ids) {
			sql = sql + ' or id in (';
			for(x in cat_son_ids) {
				sql += '?,';
				params.add(x);
			}
			sql = sql.substring(0, sql.length() - 1) + ')';
		}
		sql = sql + ') order by seq asc, cat_name_cn asc';
		return jc_template.find(Catalog.class, sql, params.toArray());
	}
	
	public List<Catalog> find_by_parent(def pid = GlobalHolder.proj.id, def parent) {
		String sql = 'select * from Catalog where flag!=? and proj_id=?';
		def params = [];
		params.add(-1);
		params.add(pid);
		if(parent!=null) {
			sql = sql + ' and parent_id=?';
			params.add(parent.id);
		} else {
			sql = sql + ' and parent_id is null';
		}
		sql = sql + ' order by seq asc, cat_name_cn asc';
		
		return jc_template.find(Catalog.class, sql, params.toArray());
	}
	
	public Catalog get(def pid = GlobalHolder.proj.id, def id) {
		if(!id) {
			return null;
		}
		String sql = 'select * from Catalog where flag!=? and proj_id=? and id=?';
		return jc_template.get(Catalog.class, sql, -1, pid, id);
	}
	
	def build_catalog_hierars(def id, def buff) {
		Catalog c = this.get(id);
		if(c==null) {
			return buff;
		}
		buff = c.id + ',' + buff;
		build_catalog_hierars(c.parent_id, buff);
	}
	
	public BigInteger save(Catalog c) {
		c.proj_id = GlobalHolder.proj.id;
		c.flag = 0;
		BigInteger id = jc_template.save(c);
		c.id = id;
		this.update(c);
		id;
	}
	
	public void update(Catalog e) {
		def cats_ps = this.build_catalog_hierars(e.id, '');
		e.hierars = cats_ps;
		jc_template.update(e);
	}
	
	/**
	 * 分类项下货品
	 */
	JcPage<GoodsInfo> find_goods(JcPage<GoodsInfo> page, Catalog aim_catalog, def typecode) {
		def params = [];
		String sql = 'select g_id from GoodsCatalog where flag!=? and pid=?';
		params.add(-1);
		params.add(GlobalHolder.proj.id);
		if(aim_catalog!=null) {
			sql = sql + ' and c_id like \'%' + aim_catalog.id + ',%\'';
		}
		if(typecode!=null) {
			sql = sql + ' and tycode=?';
			params.add(typecode);
		}
		
		sql = 'select * from GoodsInfo where id in (' + sql + ')';
		
		return jc_template.find(GoodsInfo.class, page, sql, params.toArray());
	}
	
	
	/**
	 * 分类项下商品
	 */
	def find_sale_goods(def page, Catalog aim_catalog, def typecode, def pid = GlobalHolder.proj.id) {
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		
		def params = [];
		String sql = 'select g_id from GfCatalog where flag!=?';
		params.add(-1);
		if(!now_project.root) {
			sql += ' and (pid=? or pid=?)';
			params.add(pid);
			//params.add(BigInteger.valueOf(1l));
			params.add(now_project.dbpid);
		}
		if(aim_catalog!=null) {
			sql = sql + ' and c_id like \'%' + aim_catalog.id + ',%\'';
		}
		if(typecode!=null) {
			sql = sql + ' and tycode=?';
			params.add(typecode);
		}
		
		def result = null;
		if(typecode=='100') {
			sql = 'select g.id as goods_id, s.* from GoodsSku s right join GoodsInfo g on s.goods_id=g.id where g.id in (' + sql + ')';
			result = jc_template.find(GoodsSku.class, page, sql, params.toArray());
			if(result&&result.result&&result.result.size()>0) {
				def param = [];
				def new_result = [];
				sql = 'select * from GoodsInfo where flag!=-1 and id in (';
				for(x in result.result) {
					BigInteger goods_id = x.goods_id;
					if(x.id==null||x.id==BigInteger.valueOf(0l)) {
						x = new GoodsSku();
						x.goods_id = goods_id;
					}
					new_result.add([x]);
					if(param.contains(x.goods_id)) {
						continue;
					}
					param.add(x.goods_id);
					sql = sql + '?,';
				}
				sql = sql.substring(0, sql.length() - 1) + ")";
				
				def goods_result = jc_template.find(GoodsInfo, sql, param.toArray());
				
				for(x in new_result) {
					for(y in goods_result) {
						if(x.get(0).goods_id==y.id) {
							x.add(y);
							break;
						}
					}
				}
				result.result = new_result;
			}
		} else if(typecode=='200') {
			sql = 'select * from GoodsPack where id in (' + sql + ')';
			result = jc_template.find(GoodsPack.class, page, sql, params.toArray());
		} else if(typecode=='300') {
			sql = 'select * from GdMerge where id in (' + sql + ')';
			result = jc_template.find(GdMerge.class, page, sql, params.toArray());
		}
		return result;
	}
	
	
	/**
	 * 分类项下商品，不取sku
	 */
	def find_sale_goods_without_sku(def page, Catalog aim_catalog, def typecode, def pid = GlobalHolder.proj.id) {
		def params = [];
		String sql = 'select g_id from GfCatalog where flag!=? and pid=?';
		params.add(-1);
		params.add(pid);
		if(aim_catalog!=null) {
			sql = sql + ' and c_id like \'%' + aim_catalog.id + ',%\'';
		}
		if(typecode!=null) {
			sql = sql + ' and tycode=?';
			params.add(typecode);
		}
		
		def result = null;
		if(typecode=='100') {
			sql = 'select * from GoodsInfo where id in (' + sql + ')';
			result = jc_template.find(GoodsInfo, page, sql, params.toArray());
		} else if(typecode=='200') {
			sql = 'select * from GoodsPack where id in (' + sql + ')';
			result = jc_template.find(GoodsPack.class, page, sql, params.toArray());
		} else if(typecode=='300') {
			sql = 'select * from GdMerge where id in (' + sql + ')';
			result = jc_template.find(GdMerge.class, page, sql, params.toArray());
		}
		return result;
	}
	
	
	def find_sale_goods_by_num(def page, def goods_no, def typecode, def pid = GlobalHolder.proj.id) {
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		
		def params = [];
		String sql = 'select g_id from GfCatalog where flag!=?';
		params.add(-1);
		if(!now_project.root) {
			sql += ' and (pid=? or pid=?)';
			params.add(pid);
			params.add(now_project.dbpid);
		}
		if(typecode!=null) {
			sql = sql + ' and tycode=?';
			params.add(typecode);
		}
		
		def result = null;
		if(typecode=='100') {
			sql = 'select g.id as goods_id, s.* from GoodsSku s right join GoodsInfo g on s.goods_id=g.id where g.id in (' + sql + ') and ( g.goods_out_no=? or g.goods_code=? or g.goods_id=?)';
			params.add(goods_no); params.add(goods_no); params.add(goods_no);
			logger.info("find_sale_goods_by_num :" + sql );
			logger.info("find_sale_goods_by_num p:" + params.toArray());
			result = jc_template.find(GoodsSku, page, sql, params.toArray());
			if(result&&result.result&&result.result.size()>0) {
				def param = [];
				def new_result = [];
				sql = 'select * from GoodsInfo where flag!=-1 and id in (';
				for(x in result.result) {
					BigInteger goods_id = x.goods_id;
					if(x.id==null||x.id==BigInteger.valueOf(0l)) {
						x = new GoodsSku();
						x.goods_id = goods_id;
					}
					new_result.add([x]);
					if(param.contains(x.goods_id)) {
						continue;
					}
					param.add(x.goods_id);
					sql = sql + '?,';
				}
				sql = sql.substring(0, sql.length() - 1) + ")";
				
				def goods_result = jc_template.find(GoodsInfo, sql, param.toArray());
				
				for(x in new_result) {
					for(y in goods_result) {
						if(x.get(0).goods_id==y.id) {
							x.add(y);
							break;
						}
					}
				}
				result.result = new_result;
			}
		} else if(typecode=='200') {
			sql = 'select * from GoodsPack where id in (' + sql + ') and sn=?';
			params.add(goods_no);
			result = jc_template.find(GoodsPack, page, sql, params.toArray());
		} else if(typecode=='300') {
			sql = 'select * from GdMerge where id in (' + sql + ') and sn=?';
			params.add(goods_no);
			result = jc_template.find(GdMerge, page, sql, params.toArray());
		}
		return result;
	}
	
}
