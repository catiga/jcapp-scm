package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService

def sid = JC.internal.param('sid')?.toString()?.trim();
def pid = JC.internal.param('pid')?.toString()?.trim();

def o_id = JC.internal.param('o_id')?.toString()?.trim();


OrderInfo order = OrderService.INSTANCE().get(pid, o_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,订单未找到');
}
if(order.store_id.toString()!=sid) {
	return SimpleAjax.notAvailable('obj_not_found,订单未找到' + sid);
}

if(order.oss!='1020') {
	//不是待制作订单，不可操作
	return SimpleAjax.notAvailable('status_invalid,订单状态错误' + order.oss);
}

//修改订单状态
order.oss = '2000';
JcTemplate.INSTANCE().update(order);

//通知交易系统修改订单状态
//不需要通知，因为有取货操作

return SimpleAjax.available();





