package com.jeancoder.scm.entry.catalog.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService

CatalogService service = CatalogService.INSTANCE();

def id = JC.request.param('id');
if(!id) {
	return SimpleAjax.notAvailable('param_error');
}
Catalog catalog = service.get(id);
if(catalog==null) {
	return SimpleAjax.notAvailable('obj_not_found');
}

return SimpleAjax.available('', RetObj.build(catalog));