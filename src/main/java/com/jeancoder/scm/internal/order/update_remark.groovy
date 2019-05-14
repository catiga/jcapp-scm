package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService

def pid = JC.internal.param('pid')?.toString()?.trim();
def o_id = JC.internal.param('o_id')?.toString()?.trim();
def o_rem = JC.internal.param('o_rem')?.toString()?.trim();

if(!o_rem) {
	return SimpleAjax.notAvailable('param_error,未设置备注信息');
}
o_rem = URLDecoder.decode(o_rem, 'UTF-8');
o_rem = URLDecoder.decode(o_rem, 'UTF-8');

OrderInfo order = OrderService.INSTANCE().get(pid, o_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,订单未找到');
}

//修改订单备注
List<OrderItem> items = OrderService.INSTANCE().find_order_items(order);
if(items) {
	for(x in items) {
		x.remark = o_rem;
		JcTemplate.INSTANCE().update(x);
	}
}

return SimpleAjax.available();





