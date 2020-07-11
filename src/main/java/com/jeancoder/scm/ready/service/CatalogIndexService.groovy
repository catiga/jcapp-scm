package com.jeancoder.scm.ready.service

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.util.ConvertToGoods

class CatalogIndexService {
	
	JCLogger logger = JCLoggerFactory.getLogger('');
	
	final static CatalogIndexService INSTANCE = new CatalogIndexService();

	List<Catalog> find_dsc_catalogs(def pid, def dscs, def parent) {
		def cat_code_ids = null;
		if(dscs) {
			def sql = 'select cat_id from CatalogDsc where dsc_id in';
			def son_sql = 'select id from Dsc where flag!=? and pid=? and codec in (';
			
			def params = []; params.add(-1); params.add(pid);
			for(x in dscs.split(",")) {
				son_sql += '?,';
				params.add(x);
			}
			son_sql = son_sql.substring(0, son_sql.length() - 1) + ')';
			sql = sql + ' (' + son_sql + ')';
			
			cat_code_ids = JcTemplate.INSTANCE().find(BigInteger, sql, params.toArray());
		}
		
		Catalog parent_catalog = null;
		if(parent) {
			parent_catalog = CatalogService.INSTANCE().get(parent);
		}
		
		// List<Catalog> result = cat_service.find_by_parent(pid, parent_catalog);
		List<Catalog> result = CatalogService.INSTANCE().find_by_parent_with_cats(pid, parent_catalog, cat_code_ids);
		
		return result;
	}
	
	
	
	def find_catalog_merge_goods(def pid, def cat_id, def typecode, def pn, def ps) {
		CatalogService cat_service = CatalogService.INSTANCE();
		
		Catalog node = null;
		if(cat_id) {
			node = cat_service.get(pid, cat_id);
		}
		
		def project = JC.internal.call('project', '/incall/project_by_id', [pid:pid]);
		def img_path = '//';
		try {
			project = JackSonBeanMapper.jsonToMap(project);
			img_path = img_path + project['domain'] + '/img_server';
		} catch(any) {
		}
		
		if(typecode!=null) {
			//只有在不传typecode的情况下，才取所有商品
			if(typecode!='100'&&typecode!='200'&&typecode!='300') {
				typecode = '100';
			}
		}
		
		//分别设置需要返回的三类
		def page_goods = new JcPage<>(); page_goods.pn = pn; page_goods.ps = ps;
		def page_pack = new JcPage<>(); page_pack.pn = pn; page_pack.ps = ps;
		def page_merge = new JcPage<>(); page_merge.pn = pn; page_merge.ps = ps;
		
		if(typecode==null) {
			page_goods = cat_service.find_sale_goods(page_goods, node, '100', pid);
			page_pack = cat_service.find_sale_goods(page_pack, node, '200', pid);
			page_merge = cat_service.find_sale_goods(page_merge, node, '300', pid);
		} else if(typecode=='100') {
			//取商品
			page_goods = cat_service.find_sale_goods(page_goods, node, '100', pid);
		} else if(typecode=='200') {
			//取套餐
			page_pack = cat_service.find_sale_goods(page_pack, node, '200', pid);
		} else if(typecode=='300') {
			//取合成品
			page_merge = cat_service.find_sale_goods(page_merge, node, '300', pid);
		}
		
		//需要统一所有的page数据返回为goods的格式
		def ret_data = [];
		if(page_goods.result) {
			ret_data.addAll(page_goods.result);
			ret_data.each({
				it[1].gt = '100';
				def g_sku = it[0];
				if(!g_sku.id) {
					//it[0].id = new BigInteger('-' + it[1].id + '' + nextInt(1000, 9999));
					//it[0].id = new BigInteger('-' + it[1].id);
					try {
						it[0].id = new BigInteger('-' + it[1].goods_id);
					} catch(any) {
						it[0].id = new BigInteger('-' + it[1].id);
					}
					def sku_name = ['商品名称':it[1].goods_name];
					it[0].skus = JackSonBeanMapper.mapToJson(sku_name);
				}
			});
		}
		if(page_pack.result) {
			ret_data.addAll(ConvertToGoods.convert_pack(page_pack.result));
		}
		if(page_merge.result) {
			ret_data.addAll(ConvertToGoods.convert_merge(page_merge.result));
		}
		
//		ret_data.each({it->
//			it[1].goods_picturelink = img_path + '/' + it[1].goods_picturelink;
//			it[1].goods_picturelink_big = img_path + '/' + it[1].goods_picturelink_big;
//			it[1].goods_picturelink_middle = img_path + '/' + it[1].goods_picturelink_middle;
//			it[0].sku_img = it[1].goods_picturelink
//		});
		
		for(it in ret_data) {
			if(it[1].goods_picturelink!=null && !it[1].goods_picturelink.startsWith(img_path)) {
				it[1].goods_picturelink = img_path + '/' + it[1].goods_picturelink;
			}
			if(it[1].goods_picturelink_big!=null && !it[1].goods_picturelink_big.startsWith(img_path)) {
				it[1].goods_picturelink_big = img_path + '/' + it[1].goods_picturelink_big;
			}
			if(it[1].goods_picturelink_middle!=null && !it[1].goods_picturelink_middle.startsWith(img_path)) {
				it[1].goods_picturelink_middle = img_path + '/' + it[1].goods_picturelink_middle;
			}
			it[0].sku_img = it[1].goods_picturelink
		}
		def result = ret_data;
		
		
		//检查用户选择的门店
		def sid = JC.internal.param('sid')?.trim();
		
		WareHouse default_wh = WareHouseService.INSTANCE().get_default_warehouse_by_store(sid, pid);
		logger.info(JackSonBeanMapper.toJson(default_wh));
		if(default_wh==null) {
			return SimpleAjax.notAvailable('sys_set_error,请绑定门店仓库');
		}
		//开始处理 typecode==100 即普通商品情况下的库存
		//查询库存
		//result.result.each({
		result.each({
			if(it[1].gt=='100') {
				BigDecimal stock = GoodsService.INSTANCE().get_goods_sku_stock_by_id(it[0].goods_id, it[0].id, default_wh);
				if(stock==null) {
					stock = new BigDecimal(0);
				}
				it[0].stock = stock;
			}
		});
		
		
		return result;
	}
	
	def nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
}
