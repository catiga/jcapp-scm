package com.jeancoder.scm.entry.stocktake

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockTakeService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.UnitUtil

WareHouseService wh_service = WareHouseService.INSTANCE();
List<WareHouse> all_whs = wh_service.find();
StockTakeService ss_service = StockTakeService.INSTANCE();

def id = JC.request.param('id');
StockTakeForm form = null;
if(id) {
	form = ss_service.get(id);
}
if(form==null) {
	form = new StockTakeForm();
}

Result view = new Result();
view.setView("stocktake/item");

view.addObject('all_whs', all_whs);
view.addObject('form', form);

def items = ss_service.get_items(form);
view.addObject("items", items);
view.addObject('units', UnitUtil.toList());
return view;