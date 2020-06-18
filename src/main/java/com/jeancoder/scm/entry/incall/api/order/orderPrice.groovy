package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.order.OrderConstants
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.MoneyUtil

def orderId = JC.request.param('orderId');
def actualPrice = JC.request.param('actualPrice');
def freightPrice = JC.request.param('freightPrice');
def goodsPrice = JC.request.param('goodsPrice');

OrderInfo order = OrderService.INSTANCE().get(orderId);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}
if(order.oss!=OrderConstants._order_status_create_) {
	return ProtObj.fail(210001, '订单状态不允许改价');
}

//检查actualprice
try {
	actualPrice = MoneyUtil.get_cent_from_yuan(actualPrice);
} catch(any) {
}
if(actualPrice==null || actualPrice <0) {
	return ProtObj.fail(310001, '价格设置错误');
}

order.pay_amount = actualPrice;
JcTemplate.INSTANCE().update(order);

return ProtObj.success(1);
