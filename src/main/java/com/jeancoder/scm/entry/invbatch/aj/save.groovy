package com.jeancoder.scm.entry.invbatch.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.form.BatchForm
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService

StockService stock_service = StockService.INSTANCE();
WareHouseService war_service = WareHouseService.INSTANCE();

StockOpBatch batch = null;
BatchForm form = JC.extract.fromRequest(BatchForm.class);

def id = JC.request.param('id');

println id.getMetaClass();

if(id!='0') {
	batch = stock_service.get(id);
	if(batch==null) {
		//return AjaxUtil.fail('obj_not_found', null);
		return SimpleAjax.notAvailable('obj_not_found');
	}
} else {
	batch = new StockOpBatch();
}

batch.bname = form.name;
batch.binfo = form.info;
batch.in_house = form.in_house;
batch.op_type = form.op_type;
batch.sn = form.sn;
if(!batch.sn) {
	batch.sn = System.currentTimeMillis() + '';
}

BigInteger source_wh = form.source_wh;
if(source_wh) {
	WareHouse ware_house = war_service.get(source_wh);
	if(ware_house!=null) {
		batch.s_wh_id = source_wh;
		batch.s_wh_name = ware_house.name;
		batch.store_id = ware_house.store_id;
		batch.store_name = ware_house.store_name;
	}
}

if(id!='0') {
	stock_service.update(batch);
} else {
	batch = stock_service.save(batch);
}

//return AjaxUtil.successs('', batch);
return SimpleAjax.available('', RetObj.build(batch));


