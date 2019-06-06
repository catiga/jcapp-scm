package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.order.OrderInfoDto
import com.jeancoder.scm.ready.dto.order.OrderItemDto
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
List<OrderItemDto> list = new ArrayList<OrderItemDto>();
try {
	def order_no = JC.internal.param("order_no");
	def order_id = JC.internal.param('order_id');
	
	OrderInfo order_info = null;
	if(order_id) {
		order_info = JcTemplate.INSTANCE().get(OrderInfo, 'select * from OrderInfo where id=?', order_id);
	} else {
		order_info = OrderService.__instance__.get_goods_order_info(order_no);
	}
	if(order_info==null) {
		return SimpleAjax.notAvailable('商品订单未找到');
	}
	
	OrderInfoDto orderinfodto = new OrderInfoDto();
	orderinfodto.pay_amount = order_info.pay_amount;
	List<OrderItem> order_items = new ArrayList<OrderItem>();

	order_items = OrderService.__instance__.get_goods_order_item(order_info.id);

	for (OrderItem order: order_items) {
		OrderItemDto  orderitemdto = new OrderItemDto();

		orderitemdto.pic_url = order.pic_url;
		orderitemdto.goods_name = order.goods_name;
		orderitemdto.unit_amount = order.unit_amount;
		orderitemdto.pay_amount = order_info.pay_amount;
		orderitemdto.buy_num = order.buy_num;

		list.add(orderitemdto);
	}
	orderinfodto.items = list;
	return SimpleAjax.available("",orderinfodto);
} catch (e) {
	Logger.error("商品网售售卖报表列表查询失败",e);
	return SimpleAjax.notAvailable("商品查询失败");
}
