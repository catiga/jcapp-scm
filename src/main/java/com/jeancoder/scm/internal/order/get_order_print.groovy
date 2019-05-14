package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.printer.FeedPrinter
import com.jeancoder.scm.ready.service.OrderService

def sid = JC.internal.param('sid')?.toString()?.trim();
def pid = JC.internal.param('pid')?.toString()?.trim();

def o_id = JC.internal.param('o_id')?.toString()?.trim();


OrderInfo order = OrderService.INSTANCE().get(pid, o_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,订单未找到');
}
if(order.store_id.toString()!=sid) {
	return SimpleAjax.notAvailable('obj_not_found,订单未找到' + sid);
}

List<OrderItem> items = OrderService.INSTANCE().find_order_items(order);
order.items = items;

def smarttemplate = new FeedPrinter().get_printer_content(order, items);
println  "smarttemplate__）））______" + JackSonBeanMapper.toJson(smarttemplate);

return SimpleAjax.available('', [order, smarttemplate]);





