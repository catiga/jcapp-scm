package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.core.util.StringUtil
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.service.OrderService

Result result = new Result();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName())
def p = CommunicationSource.getParameter("orders");
def pid = CommunicationSource.getParameter("pid");
try {
	if (StringUtil.isEmpty(p)||StringUtil.isEmpty(pid)) {
		return SimpleAjax.notAvailable('参数不可为空');
	}
	List<OrderInfo> order = new ArrayList();
	String [] list = p.split(";");//参数为："编号+oss"
	for (int i = 0 ; i<list.length;i++) {
		def item = list[i].split(",");
		OrderInfo info = new OrderInfo();
		info.pid = new BigInteger(pid);
		info.order_no = item[0];
		def result_data = OrderService.__instance__.update_order_status(info);
		order.add(result_data);
	}
	return SimpleAjax.available("",order);
} catch (Exception e) {
	Logger.error("修改订单状态失败", e);
	return SimpleAjax.notAvailable("修改订单状态失败");
}
