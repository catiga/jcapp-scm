package com.jeancoder.scm.entry.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AccountSession
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.RemoteUtil

/**
 * '/incall/cashier/create_order?gs=******'
 * 创建商品订单
 * 商品协议串
 * 参数名称：gs, s_id
 * gs参数格式：tyc,商品id,SKU_ID,数量,remark;商品id,SKU_ID,数量,remark
 *           普通商品：tyc=100
 *           套餐商品：tyc=200
 * s_id参数：门店id
 * @author jackielee
 */

//首先校验当前用户
def apid = BigInteger.valueOf(Long.valueOf(JC.request.param('apid')));
AccountSession user_session = new AccountSession(ap_id:apid);

def on = JC.request.param('on')?.trim();

OrderService order_service = OrderService.INSTANCE();

List<OrderInfo> orders = order_service.find_order_by_num(on);
if(orders==null||orders.empty) {
	return SimpleAjax.available();
}

OrderInfo o = orders.get(0);
if(o.getBuyerid().toString()!=user_session?.ap_id?.toString()) {
	return SimpleAjax.available();
}

List<OrderItem> items = order_service.find_order_items(o);
o.items = items;

return SimpleAjax.available('', o);












