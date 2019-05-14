package com.jeancoder.scm.entry.stocktake

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockTakeService
import com.jeancoder.scm.ready.service.WareHouseService

StockTakeService ss_service = StockTakeService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

def pn = JC.request.param('pn');
if(!pn) {
	pn = 1;
} else {
	try {
		pn = Integer.valueOf(pn);
	} catch(any) {
		pn = 1;
	}
}
JcPage<StockTakeForm> page = new JcPage<StockTakeForm>();
page.pn = pn;
page.ps = 20;

def w = JC.request.param('w');
def s = JC.request.param('s');
def n = JC.request.param('n');

WareHouse ware = null;
if(w) {
	ware = wh_service.get(w);
}


def form_no = n;
def status = s;
def warehouse = ware==null?null:ware.id;

page = ss_service.find(page, form_no, s, warehouse);


List<WareHouse> all_whs = wh_service.find();

Result view = new Result();
view.setView("stocktake/index");
view.addObject("page", page);

view.addObject('all_whs', all_whs);

view.addObject('w', w);
view.addObject('s', s);
view.addObject('n', n);

return view;