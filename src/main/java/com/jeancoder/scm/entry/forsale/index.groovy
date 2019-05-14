package com.jeancoder.scm.entry.forsale

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.UnitUtil

GoodsService goods_service = GoodsService.INSTANCE();

def g_no = JC.request.param('g_no')?.trim();
def pn = JC.request.param('pn')?.trim();
try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = pn;
page.ps = 20;

page = goods_service.for_sale_list(page, g_no);

Result view = new Result();
view.setView("forsale/index");
view.addObject("page", page);

view.addObject('units', UnitUtil.toList());
view.addObject('g_no', g_no);
return view;