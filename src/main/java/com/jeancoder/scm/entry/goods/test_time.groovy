package com.jeancoder.scm.entry.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.UnitUtil

def start = System.currentTimeMillis();

def pn = JC.request.param('pn');
if(!pn) {
	pn = 1;
}
GoodsService ss_service = GoodsService.INSTANCE();

JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = pn;
page.ps = 20;
page = ss_service.find(page);

Result view = new Result();
view.setView("goods/test_time");
view.addObject("page", page);

view.addObject('units', UnitUtil.toList());

def end = System.currentTimeMillis();

println 'entry:goods/test_time execute time=' + ((end - start)/1000) + '秒';
return view;