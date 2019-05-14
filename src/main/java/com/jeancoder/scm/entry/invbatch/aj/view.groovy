package com.jeancoder.scm.entry.invbatch.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.service.StockService

StockService stock_service = StockService.INSTANCE();

def id = JC.request.param('id');
if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}
StockOpBatch batch = stock_service.get(id);
if(batch==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}

//return AjaxUtil.successs('', batch);
return SimpleAjax.available('', RetObj.build(batch));
