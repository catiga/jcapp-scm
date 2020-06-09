package com.jeancoder.scm.entry.incall.api.category

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService

def cat_id = JC.request.param('id');

Catalog cat = CatalogService.INSTANCE().get(cat_id);

if(cat==null && cat.cat_icon) {
	return ProtObj.success(1);
}

def path = cat.cat_icon;

def cdn_root_path = '/Users/jackielee/Desktop';		//默认路径

SimpleAjax sys_conf = JC.internal.call(SimpleAjax, 'project', '/sys/get_root_path', null);
if(sys_conf && sys_conf.available) {
	cdn_root_path = sys_conf.data;
}

File f = new File(cdn_root_path + '/' + path);
if(f.exists() && f.isFile()) {
	def del_result = f.delete();
	if(del_result) {
		cat.cat_icon = null;
		JcTemplate.INSTANCE().update(cat);
	}
}
return ProtObj.success(1);

