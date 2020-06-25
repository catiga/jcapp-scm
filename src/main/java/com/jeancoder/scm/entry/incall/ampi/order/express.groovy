package com.jeancoder.scm.entry.incall.ampi.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderShip
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = GlobalHolder.proj.id;


def orderId = JC.request.param('orderId');

OrderInfo order = OrderService.INSTANCE().get(orderId);

if(order!=null && order.buyerid!=ap_id) {
	order = null;
}

def express = [:];
//查询快递信息
if(order) {
	def sql = 'select * from OrderShip where flag!=? and order_id=? order by a_time asc';
	OrderShip ship = JcTemplate.INSTANCE().get(OrderShip, sql, -1, order.id);
	
	if(ship) {
		express['shipper_name'] = ship.exp_name;
		express['logistic_code'] = ship.exp_odd;
		express['is_finish'] = 0;
	}
}
express['traces'] = '[]';

return ProtObj.success(express);



