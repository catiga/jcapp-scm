package com.jeancoder.scm.entry.stocktake.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockTakeService
import com.jeancoder.scm.ready.service.WareHouseService

StockTakeService stock_service = StockTakeService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

def id = Long.valueOf(JC.request.param('id'));
StockTakeForm form = null;
if(id&&id>0l) {
	form = stock_service.get(id);
	if(form==null) {
		return SimpleAjax.notAvailable('form_not_found,盘点表未找到');
	}
}

def icname = JC.request.param('icname');
def icinfo = JC.request.param('icinfo');

def wh = JC.request.param('wh');
def ictype = JC.request.param('ictype')?.trim();

if(ictype==null||(ictype!='10'&&ictype!='50')) {
	return SimpleAjax.notAvailable('type_error,盘点类型错误');
}

WareHouse ware = wh_service.get(wh);
if(ware==null) {
	return SimpleAjax.notAvailable('wh_not_found,请选择盘点仓库');
}

if(form==null) {
	id = stock_service.save_form(id, icname, icinfo, ictype, ware);
	if(id<=0) {
		return SimpleAjax.notAvailable('create_repeat,您有未结束的盘点表，请先进行结束操作');
	}
} else {
	form.icname = icname;
	form.icinfo = icinfo;
	stock_service.update_form(form);
}

return SimpleAjax.available('', id);


