package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService

def order_id = JC.request.param('order_id');

OrderInfo order = OrderService.INSTANCE().get(order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

//查找订单的快递信息

return ProtObj.fail(220002, '快递信息为空');
