package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderShip
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder

//保存快递单上的提醒信息
def order_id = JC.request.param('id');
def print_info = JC.request.param('print_info');

OrderInfo order = OrderService.INSTANCE().get(order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

//查找订单运单信息
def sql = 'select * from OrderShip where flag!=? and order_id=? and pid=?';
List<OrderShip> order_ships = JcTemplate.INSTANCE().find(OrderShip, sql, -1, order_id, GlobalHolder.proj.id);

OrderShip os = null;

if(order_ships==null || order_ships.empty) {
	os = new OrderShip();
	os.flag = 0;
	os.a_time = new Date();
	os.pid = GlobalHolder.proj.id;
	os.order_id = order.id;
	os.exp_str = print_info;
	
	JcTemplate.INSTANCE().save(os);
} else if(order_ships.size()==1) {
	os = order_ships.get(0);
	os.exp_str = print_info;
	
	JcTemplate.INSTANCE().update(os);
} else {
	
}

return ProtObj.success(1);



