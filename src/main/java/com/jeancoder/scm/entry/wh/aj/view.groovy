package com.jeancoder.scm.entry.wh.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService

def id = JC.request.param('id')
if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}

WareHouseService w_ser = WareHouseService.INSTANCE();

WareHouse e = w_ser.get(id);
if(e==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}

//return AjaxUtil.successs('', e);
return SimpleAjax.available('', RetObj.build(e));

