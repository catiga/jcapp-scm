package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService

//{"errno":0,"errmsg":"","data":1}

def order_id = JC.request.param('orderId');
def status = JC.request.param('status');

OrderInfo order = OrderService.INSTANCE().get(order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

order.oss = status;
JcTemplate.INSTANCE().update(order);

JCLogger logger = JCLoggerFactory.getLogger('');
logger.info('change status');

return ProtObj.success(1);
