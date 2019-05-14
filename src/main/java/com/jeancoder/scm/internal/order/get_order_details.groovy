package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.order.OrderInfoDto
import com.jeancoder.scm.ready.dto.order.OrderItemDto
import com.jeancoder.scm.ready.dto.order.OrderNermDto
import com.jeancoder.scm.ready.dto.order.OrderItemPackDto
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.OrderItemPack
import com.jeancoder.scm.ready.entity.OrderNerm
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.StringUtil

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
List<OrderItemDto> list = new ArrayList<OrderItemDto>();
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));
	OrderInfo order_info =  OrderService.__instance__.get_goods_order_info(order_no);
	OrderNerm nerm = OrderService.__instance__.get_order_nerm(order_info);
	OrderInfoDto orderinfodto = new OrderInfoDto();
	orderinfodto.nerm = new OrderNermDto(nerm);
	orderinfodto.pay_amount = order_info.pay_amount;
	List<OrderItem> order_items =  OrderService.__instance__.get_goods_Item_order_with_pack(order_info.id);
	for (OrderItem order: order_items) {
		OrderItemDto  orderitemdto = new OrderItemDto();
		orderitemdto.pic_url = order.pic_url;
		orderitemdto.goods_name = order.goods_name;
		orderitemdto.unit_amount = order.unit_amount;
		orderitemdto.pay_amount = order.pay_amount;
		orderitemdto.buy_num = order.buy_num;
		orderitemdto.tycode = order.tycode;
		orderitemdto.remark = order.remark;
		orderitemdto.verts = new ArrayList<OrderItemPackDto>();
		if (order.verts == null || order.verts.isEmpty()) {
			list.add(orderitemdto);
			continue;
		}
		for (OrderItemPack pack : order.verts) {
			orderitemdto.verts.add(new OrderItemPackDto(pack))
		}
		list.add(orderitemdto);
	}
	orderinfodto.items = list;
	return SimpleAjax.available("",orderinfodto);
} catch (e) {
	Logger.error("商品网售售卖报表列表查询失败",e);
	return SimpleAjax.notAvailable("商品查询失败");
}