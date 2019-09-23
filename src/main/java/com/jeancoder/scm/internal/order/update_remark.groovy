package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService

def pid = JC.internal.param('pid')?.toString()?.trim();
def o_id = JC.internal.param('o_id')?.toString()?.trim();
def o_rem = JC.internal.param('o_rem')?.toString()?.trim();

def addr_id = JC.internal.param('addr_id')?.toString()?.trim();

if(o_rem) {
	o_rem = URLDecoder.decode(o_rem, 'UTF-8');
	o_rem = URLDecoder.decode(o_rem, 'UTF-8');
}

def addr = null;
if(addr_id) {
	SimpleAjax addr_aj = JC.internal.call(SimpleAjax, 'crm', '/h5/address/get', [pid:pid, id:addr_id]);
	if(addr_aj && addr_aj.available) {
		addr = addr_aj.data;
	}
}

OrderInfo order = OrderService.INSTANCE().get(pid, o_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,订单未找到');
}

if(addr!=null) {
	order.buyeraddr = addr.address;
	order.buyercitycode = addr.city_code;
	order.buyercityname = addr.city;
	order.buyername = addr.name;
	order.buyerphone = addr.mobile;
	order.buyerprovincecode = addr.province_code;
	order.buyerprovincename = addr.province;
	order.buyerzonecode = addr.zone_code;
	order.buyerzonename = addr.zone;
	JcTemplate.INSTANCE().update(order);
}

//修改订单备注
List<OrderItem> items = OrderService.INSTANCE().find_order_items(order);
if(items) {
	for(x in items) {
		x.remark = o_rem;
		JcTemplate.INSTANCE().update(x);
	}
}

return SimpleAjax.available();





