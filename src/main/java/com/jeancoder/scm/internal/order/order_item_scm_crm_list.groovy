package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.order.OrderInfoDto
import com.jeancoder.scm.ready.dto.order.OrderItemDto
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.DateUtil

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
Result result = new Result();
List<OrderItemDto> list = new ArrayList<OrderItemDto>();
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));
	OrderInfo order_info = new OrderInfo();
	order_info = OrderService.__instance__.get_goods_order_info(order_no);
	OrderInfoDto orderinfodto = new OrderInfoDto();
	orderinfodto.total_amount = order_info.total_amount;
	orderinfodto.pay_amount = order_info.pay_amount;
	orderinfodto.store_name = order_info.store_name;
	orderinfodto.order_no = order_info.order_no;
	orderinfodto.pay_time1 = DateUtil.format_yyyyMMdd(order_info.pay_time);
	orderinfodto.refund_time = DateUtil.format_yyyyMMdd(order_info.a_time) ;
	List<OrderItem> order_items = new ArrayList<OrderItem>();

	order_items = OrderService.__instance__.get_goods_order_item(order_info.id);

	for (OrderItem order: order_items) {
		OrderItemDto  orderitemdto = new OrderItemDto();

		orderitemdto.goods_name = order.goods_name;
		orderitemdto.buy_num = order.buy_num;
		orderitemdto.goods_no = order.goods_no;
		list.add(orderitemdto);
	}
	orderinfodto.items = list;
	return SimpleAjax.available("",orderinfodto);
} catch (e) {
	Logger.error("商品网售售卖报表列表查询失败",e);
	return SimpleAjax.notAvailable("商品查询失败");
}