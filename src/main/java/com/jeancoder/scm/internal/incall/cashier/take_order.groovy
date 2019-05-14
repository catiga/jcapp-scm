package com.jeancoder.scm.internal.incall.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.service.OrderService

def onum = JC.internal.param('onum');
def order_id = JC.internal.param('oid');
def pid = JC.internal.param('pid');

//首先校验当前用户
def token_key = JC.internal.param('token');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key]);
if(!ret_obj.available()) {
	return SimpleAjax.notAvailable('need_login,用户未登陆');
}

if(ret_obj==null) {
	return SimpleAjax.notAvailable('trade_server_error,请检查交易服务');
}
if(!ret_obj.available) {
	return ret_obj;
}

OrderService order_service = OrderService.INSTANCE();
OrderInfo order = order_service.get(pid, order_id);
if(order==null) {
	return SimpleAjax.notAvailable('order_not_found,订单未找到');
}

if(!order.oss.startsWith('2')) {
	//只有2开头的订单才可以取货
	return SimpleAjax.notAvailable('status_invalid,订单状态不支持取货操作');
}

//去检查这笔订单的交易状态
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/take_trade_order', [oid:order.id,onum:order.order_no,oc:'1000', token:token_key]);

if(trade&&trade.available) {
	//开始修改订单状态，并通知交易中心订单状态修改
	order.oss = '3000';
	JcTemplate.INSTANCE().update(order);
}

return trade;





