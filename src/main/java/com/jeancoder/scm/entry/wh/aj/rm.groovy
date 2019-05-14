package com.jeancoder.scm.entry.wh.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService

def id = JC.request.param('id');
if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}

WareHouseService w_ser = WareHouseService.INSTANCE();

WareHouse entity = w_ser.get(id);
if(entity==null) {
	//return AjaxUtil.fail('not_found', null);
	return SimpleAjax.notAvailable('not_found');
}
entity.flag = -1;
w_ser.update(entity);

//return AjaxUtil.success();
return SimpleAjax.available();

