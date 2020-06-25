package com.jeancoder.scm.entry.incall.ampi.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
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

def orderGoods = [];
if(order) {
	List<OrderItem> x_items = OrderService.INSTANCE().find_order_items(order);
	x_items.each({
		item->
		orderGoods.add([list_pic_url:item.pic_url, goods_name:item.goods_name, goods_specifition_name_value:item.goods_sku_name, retail_price:item.pay_amount/100, number:item.buy_num]);
	});

}

return ProtObj.success(orderGoods);

