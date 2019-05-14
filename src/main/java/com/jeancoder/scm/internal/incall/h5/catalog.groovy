package com.jeancoder.scm.internal.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

CatalogService cat_service = CatalogService.INSTANCE();

def pid = JC.internal.param('pid');
def token = JC.internal.param('token');
def parent = JC.internal.param('parent');
//获取前端传入的访问渠道参数
def dscs = JC.internal.param('dscs');

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
	parent_catalog = cat_service.get(parent);
}

// List<Catalog> result = cat_service.find_by_parent(pid, parent_catalog);
List<Catalog> result = cat_service.find_by_parent_with_cats(pid, parent_catalog, cat_code_ids);

//增加二级返回
for(x in result) {
	//x.sons = cat_service.find_by_parent(pid, x);
	x.sons = cat_service.find_by_parent_with_cats(pid, x, cat_code_ids);
	
	x.cat_icon = GlobalHolder.INSTANCE.img_path() + '/' + x.cat_icon;
}

return SimpleAjax.available('', result);

