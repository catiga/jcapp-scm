package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService

JCLogger logger = JCLoggerFactory.getLogger('');
logger.info('saveAddress status');

def user_id = JC.request.param('user_id');
def buyername = JC.request.param('name');
def buyerphone = JC.request.param('mobile');
def remark = JC.request.param('postscript');
def address = JC.request.param('address');

def full_address = JC.request.param('cAddress');
def order_sn = JC.request.param('order_sn');
def order_id = JC.request.param('order_id');

def prov_code = JC.request.param('addOptions[0]');
def prov_name = JC.request.param('ao_names[0]');

def city_code = JC.request.param('addOptions[1]');
def city_name = JC.request.param('ao_names[1]');

def zone_code = JC.request.param('addOptions[2]');
def zone_name = JC.request.param('ao_names[2]');

//查找订单
OrderInfo order = OrderService.INSTANCE().get(order_id);
if(order==null) {
	return ProtObj.fail(110001, '订单未找到');
}

order.buyername = buyername;
order.buyeraddr = full_address;
order.remark = remark;
order.buyercitycode = city_code;
order.buyercityname = city_name;
order.buyerphone = buyerphone;
order.buyerprovincecode = prov_code;
order.buyerprovincename = prov_name;
order.buyerzonecode = zone_code;
order.buyerzonename = zone_name;

JcTemplate.INSTANCE().update(order);

return ProtObj.success(1);



