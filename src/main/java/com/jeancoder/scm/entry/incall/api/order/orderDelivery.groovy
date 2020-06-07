package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.DicExpress
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderShip
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder

def order_id = JC.request.param('orderId');
def shipper_id = JC.request.param('shipper');				//快递公司id
def method = JC.request.param('method');					//投递类型：1:快递；2:手动输入快递；3:自提
def logistic_code = JC.request.param('logistic_code');		//快递单号

def sql = 'select * from DicExpress where flag!=? order by id asc';

List<DicExpress> result = JcTemplate.INSTANCE().find(DicExpress, sql, -1);

DicExpress de = null;
for(x in result) {
	if(x.id.toString()==shipper_id) {
		de = x;
		break;
	}
}

if(de==null) {
	return ProtObj.fail(110001, '快递公司未找到');
}

OrderInfo order = OrderService.INSTANCE().get(order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

//查找订单运单信息
sql = 'select * from OrderShip where flag!=? and order_id=? and pid=?';
List<OrderShip> order_ships = JcTemplate.INSTANCE().find(OrderShip, sql, -1, order_id, GlobalHolder.proj.id);

OrderShip os = null;

if(order_ships==null || order_ships.empty) {
	os = new OrderShip();
	os.flag = 0;
	os.a_time = new Date();
	os.pid = GlobalHolder.proj.id;
	os.order_id = order.id;
	
	os.exp_id = de.id;
	os.exp_odd = logistic_code;
	os.exp_name = de.name;
	os.exp_code = de.code;
	os.ship_type = method;
	
	JcTemplate.INSTANCE().save(os);
} else if(order_ships.size()==1) {
	os = order_ships.get(0);
	
	os.exp_id = de.id;
	os.exp_odd = logistic_code;
	os.exp_name = de.name;
	os.exp_code = de.code;
	os.ship_type = method;
	
	JcTemplate.INSTANCE().update(os);
} else {
	
}

def data = ["id":os.id,"order_id":order.id,"shipper_id":de.id,"shipper_name":de.name,"shipper_code":de.code,"logistic_code":os.exp_odd,"traces":"[]","is_finish":0,"request_count":0,"request_time":0,"add_time":1575036264,"update_time":0,"express_type":4,"region_code":"140天津"];

return ProtObj.success(data);

/*
return 
["errno":0,"errmsg":"",
	"data":
	["id":125,"order_id":1330,"shipper_id":5,"shipper_name":"圆通速递","shipper_code":"YTO","logistic_code":"YT2880409397161","traces":"[]","is_finish":0,"request_count":0,"request_time":0,"add_time":1575036264,"update_time":0,"express_type":4,"region_code":"140天津"]
];
*/

