package com.jeancoder.scm.entry.incall.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.TradeInfo
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.RemoteUtil

def onum = JC.request.param('onum');
def order_id = JC.request.param('oid');

//首先校验当前用户
AuthToken token = GlobalHolder.token;
AuthUser user = token?.user;
def token_key = token?.session?.token;
//if(user==null) {
//	//检查是否传入
//	def token_key = JC.request.param('token');
//	token = RemoteUtil.connect(AuthToken.class, 'project', '/incall/token_validate', ['token':token_key]);
//	if(token==null) {
//		return SimpleAjax.notAvailable('need_login, 用户未登陆');
//	}
//}

if(token_key==null) {
	token_key = JC.request.param('token');
}

//SimpleAjax ret_obj = RemoteUtil.connect(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key]);
def domain = JC.request.get().getServerName();
SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key, domain:domain]);

if(ret_obj==null) {
	return SimpleAjax.notAvailable('trade_server_error,请检查交易服务');
}
if(!ret_obj.available) {
	return ret_obj;
}



OrderService order_service = OrderService.INSTANCE();
OrderInfo order = order_service.get(order_id);
if(order==null) {
	return SimpleAjax.notAvailable('order_not_found,订单未找到');
}

//去检查这笔订单的交易状态
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/drawback_trade_order', [oid:order.id,onum:order.order_no,oc:'1000',pid:GlobalHolder.proj.id.toString()]);

return trade;





