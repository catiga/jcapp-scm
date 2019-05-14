package com.jeancoder.scm.entry.catalog.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService

CatalogService service = CatalogService.INSTANCE();

def e = JC.request.param('e');
def p = JC.request.param('p');

Catalog aim_node = null;
if(e) {
	aim_node = service.get(e);
}
if(aim_node==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}
Catalog parent = null;
if(p) {
	parent = service.get(p);
}
if(parent==null) {
	aim_node.parent_id = null;
} else {
	aim_node.parent_id = parent.id;
}

service.update(aim_node);
//return AjaxUtil.success();
return SimpleAjax.available();
