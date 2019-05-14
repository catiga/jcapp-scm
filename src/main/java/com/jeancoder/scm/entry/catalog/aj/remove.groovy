package com.jeancoder.scm.entry.catalog.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

CatalogService service = CatalogService.INSTANCE();

def id = JC.request.param('id');
Catalog node = service.get(id);
if(node==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}

node.flag = -1;
service.update(node);
//return AjaxUtil.success();
return SimpleAjax.available();
