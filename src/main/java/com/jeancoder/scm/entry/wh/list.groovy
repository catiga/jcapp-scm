package com.jeancoder.scm.entry.wh

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService

WareHouseService w_ser = WareHouseService.INSTANCE();

def pn = JC.request.param('pn');
if(!pn) {
	pn = 1;
}
JcPage<WareHouse> page = new JcPage<WareHouse>();
page.pn = pn;
page.ps = 20;

page = w_ser.find(page);

Result view = new Result();
view.setView("wh/list");
view.addObject("page", page);

return view;