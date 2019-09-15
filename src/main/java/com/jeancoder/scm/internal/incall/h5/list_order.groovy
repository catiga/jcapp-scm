package com.jeancoder.scm.internal.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.entity.OrderInfo

def apid = JC.internal.param('apid');
def oss = JC.internal.param('oss');			//逗号分割的订单状态
def ds = JC.internal.param('ds');			// 150:190  电商订单或到店订单

if(!oss) {
	oss = null;
} else {
	oss = oss.split(',');
	def buff = new StringBuffer();
	if(oss && !oss.empty) {
		for(x in oss) {
			buff.append("'" + x + "',");
		}
		oss = buff.substring(0, buff.length - 1);
	} else {
		oss = null;
	}
}

if(!ds) {
	ds = null;
}

OrderService order_service= OrderService.INSTANCE();
def orders = order_service.find_user_order(apid, ds, oss);

if(orders==null||orders.empty) {
	return new ArrayList<OrderInfo>();
}

orders.each({
	it.items = order_service.find_order_items(it);
});

return orders;








