package com.jeancoder.scm.entry.stockin.alloc.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.GoodsStockFullForm
import com.jeancoder.scm.ready.service.StockService

StockService stock_service = StockService.INSTANCE();

def id = JC.request.param('id');

if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}

GoodsStockFullForm form = stock_service.get_gs_form(id);

//return AjaxUtil.successs('', form);
return SimpleAjax.available('', RetObj.build(form));

