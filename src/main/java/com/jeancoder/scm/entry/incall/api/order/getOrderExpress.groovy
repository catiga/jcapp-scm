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

OrderInfo order = OrderService.INSTANCE().get(order_id);

//查找订单运单信息
sql = 'select * from OrderShip where flag!=? and order_id=? and pid=?';
OrderShip os = JcTemplate.INSTANCE().get(OrderShip, sql, -1, order_id, GlobalHolder.proj.id);

if(os==null) {
	return ProtObj.fail(110001, '快递信息为空');
}

DicExpress de = JcTemplate.INSTANCE().get(DicExpress, 'select * from DicExpress where flag!=? and id=?', -1, os.exp_id);

def data = ["id":os.id,"order_id":order.id,"shipper_id":de.id,"shipper_name":de.name,"shipper_code":de.code,"logistic_code":os.exp_odd,"traces":"[]","is_finish":0,"request_count":0,"request_time":0,"add_time":1575036264,"update_time":0,"express_type":4,"region_code":"140天津"];

return ProtObj.success(data);

//return '{"errno":0,"errmsg":"","data":{"id":125,"order_id":1330,"shipper_id":5,"shipper_name":"圆通速递","shipper_code":"YTO","logistic_code":"YT2880409397161","traces":"[]","is_finish":0,"request_count":0,"request_time":0,"add_time":1575036264,"update_time":0,"express_type":4,"region_code":"140天津"}}';
