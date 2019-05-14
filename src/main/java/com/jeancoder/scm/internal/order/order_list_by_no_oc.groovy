package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
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
def  list = [];
try {
	String os = JC.internal.param("os");
	def pid = JC.internal.param("pid");
	if (StringUtil.isEmpty(os)) {
		return SimpleAjax.available("",list);
	}
	String[] oss = os.split(";")
	String scm_no = ""
	for (def str : oss) {
		String[] order = str.split(",");
		scm_no +=  "'" +  order[0] + "',"
	}
	if (StringUtil.isEmpty(scm_no)) {
		return SimpleAjax.available("",list)
	}
	
	JcTemplate jcTemplate = JcTemplate.INSTANCE()
	scm_no = scm_no.substring(0,scm_no.length()-1);
	String sql = "select  * from  order_info WHERE  order_no in ("+scm_no +");"
	List<OrderInfo> infoList = jcTemplate.find(OrderInfo, sql, null);
	if (infoList == null || infoList.isEmpty()) {
		return SimpleAjax.available("",list)
	}
	for (def order_info : infoList) {
		OrderNerm nerm = OrderService.__instance__.get_order_nerm(order_info);
		OrderInfoDto orderinfodto = new OrderInfoDto(order_info);
		orderinfodto.nerm = new OrderNermDto(nerm);
		List<OrderItem> order_items =  OrderService.__instance__.get_goods_Item_order_with_pack(order_info.id);
		List<OrderItemDto> itemList = new ArrayList<OrderItemDto>();
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
				itemList.add(orderitemdto);
				continue;
			}
			for (OrderItemPack pack : order.verts) {
				orderitemdto.verts.add(new OrderItemPackDto(pack))
			}
			itemList.add(orderitemdto);
		}
		orderinfodto.items = itemList;
		list.add(orderinfodto);
	}
	SimpleAjax.available("",list);
} catch (e) {
	Logger.error("查询失败",e);
	return SimpleAjax.notAvailable("查询失败");
}
