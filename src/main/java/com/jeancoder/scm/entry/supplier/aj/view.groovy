package com.jeancoder.scm.entry.supplier.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.service.SupplierService

def id = JC.request.param('id')
if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}
SupplierService service = SupplierService.INSTANCE();
Provider objinfo = service.get(id);
if(objinfo==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}

//return AjaxUtil.successs('', objinfo);
return SimpleAjax.available('', RetObj.build(objinfo));

