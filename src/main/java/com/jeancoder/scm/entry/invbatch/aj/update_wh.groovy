package com.jeancoder.scm.entry.invbatch.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.form.BatchForm
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService

def id = JC.request.param('id');
def wh = JC.request.param('wh');

WareHouseService ws = WareHouseService.INSTANCE();

WareHouse aim_warehouse = ws.get(wh);
if(aim_warehouse==null) {
	//return AjaxUtil.fail('warehouse_not_found', null);
	return SimpleAjax.notAvailable('warehouse_not_found');
}

StockService stock_service = StockService.INSTANCE();

if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}
StockOpBatch batch = stock_service.get(id);

if(batch==null) {
	return AjaxUtil.fail('batch_not_found', null);
}


batch.a_wh_id = aim_warehouse.id;
batch.a_wh_name = aim_warehouse.name;
batch.aim_store_id = aim_warehouse.store_id;
batch.aim_store_name = aim_warehouse.store_name;

stock_service.update(batch);

//return AjaxUtil.successs('', batch);
return SimpleAjax.available('', RetObj.build(batch));



