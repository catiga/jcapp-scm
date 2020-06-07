package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.incall.api.ProtObj

JCLogger logger = JCLoggerFactory.getLogger('');
logger.info('saveAdminMemo status');

def order_id = JC.request.param('id');
def text = JC.request.param('text');

//更新管理员备注
OrderInfo order = JcTemplate.INSTANCE().get(OrderInfo, 'select * from OrderInfo where flag!=? and id=?', -1, order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

order.admin_memo = text;
JcTemplate.INSTANCE().update(order);

return ProtObj.success(1);
