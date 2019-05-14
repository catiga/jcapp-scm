package com.jeancoder.scm.entry.stocktake.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.service.StockTakeService

def id = JC.request.param('id');
StockTakeForm form = StockTakeService.INSTANCE().get(id);
if(form==null) {
	//return AjaxUtil.fail('form_not_found', null);
	return SimpleAjax.notAvailable('form_not_found');
}

if(!form.icss.equals('0000')) {
	//return AjaxUtil.fail('form_status_error', null);
	return SimpleAjax.notAvailable('form_status_error');
}

boolean success = StockTakeService.INSTANCE().start_form(form);
if(!success) {
	//return AjaxUtil.fail('ing_repeat', null);
	return SimpleAjax.notAvailable('ing_repeat');
}
//return AjaxUtil.successs('', form);
return SimpleAjax.available('', RetObj.build(form));





