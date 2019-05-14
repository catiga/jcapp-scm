package com.jeancoder.scm.entry.stockin.alloc.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService

WareHouseService wh_service = WareHouseService.INSTANCE();
StockService stock_service = StockService.INSTANCE();

def wh = JC.request.param('wh');
if(!wh) {
	//return AjaxUtil.fail();
	return SimpleAjax.notAvailable();
}
WareHouse ware_house = wh_service.get(wh);

if(ware_house==null) {
	//return AjaxUtil.fail();
	return SimpleAjax.notAvailable();
}

//根据仓库信息查找对应的出库单
List<GsForm> pub_form = stock_service.find_out_put_form(ware_house);

//return AjaxUtil.successs('', pub_form);
return SimpleAjax.available('', RetObj.build(pub_form));




