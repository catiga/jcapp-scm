package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.order.OrderConstants
import com.jeancoder.scm.ready.service.OrderService

def order_id = JC.request.param('order_id');

OrderInfo order = OrderService.INSTANCE().get(order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

if(order.oss!=OrderConstants._order_status_payed_ && order.oss!=OrderConstants._order_status_payed_cod_) {
	return ProtObj.fail(210001, '订单状态错误');
}

order.oss = OrderConstants._order_status_delivering_;
JcTemplate.INSTANCE().update(order);

return ProtObj.success(1);
