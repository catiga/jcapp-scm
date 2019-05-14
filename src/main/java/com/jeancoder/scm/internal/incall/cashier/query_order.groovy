package com.jeancoder.scm.internal.incall.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.RemoteUtil

def pid = JC.internal.param('pid');
def onum = JC.internal.param('onum');

//首先校验当前用户
def token_key = JC.internal.param('token');

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key]);
if(!ret_obj.available()) {
	return SimpleAjax.notAvailable('need_login,用户未登陆');
}


OrderService order_service = OrderService.INSTANCE();
List<OrderInfo> orders = order_service.find_order_by_num(pid, onum);
if(orders==null||orders.isEmpty()) {
	return SimpleAjax.notAvailable('order_not_found,订单未找到');
}

if(orders.size()>1) {
	//这种情况应该非常少见
	return SimpleAjax.available('', orders);
}

OrderInfo o = orders.get(0);
List<OrderItem> items = order_service.find_order_items(o);
if(items) {
	for(x in items) {
		if(!x.goods_sku_name) {
			def sku_name = ['商品名称':x.goods_name];
			x.goods_sku_name = JackSonBeanMapper.toJson(sku_name);
		}
	}
}
return SimpleAjax.available('', [o, items]);







