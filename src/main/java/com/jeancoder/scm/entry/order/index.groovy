package com.jeancoder.scm.entry.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.DataTrans
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.OrderDss
import com.jeancoder.scm.ready.util.RemoteUtil

OrderService order_service = OrderService.INSTANCE();

def o_num = JC.request.param('o_num')?.trim();
def g_num = JC.request.param('g_num')?.trim();
def s = JC.request.param('s');

JcPage<OrderInfo> page = new JcPage<OrderInfo>();
def pn = 1;
try {
	pn = Integer.valueOf(JC.request.param('pn'));
	if(pn<1) {pn = 1;}
} catch(any) {}
page.pn = pn;
page.ps = 20;

page = order_service.find_goods(page, o_num, g_num, s);

List<StoreInfo> stores = RemoteUtil.connectAsArray(StoreInfo.class, 'project', '/incall/mystore', null);

Result view = new Result();
view.setView("order/index");
view.addObject('page', page);
view.addObject('stores', stores);
view.addObject('o_num', o_num);
view.addObject('g_num', g_num);
view.addObject('s', s);

view.addObject('dss', OrderDss._ss_);

DataTrans dt = GlobalHolder.dt;
view.addObject('oss', GlobalHolder.dt?.oss);


return view;



