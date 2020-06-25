package com.jeancoder.scm.entry.incall.ampi.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.OrderUtil

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

SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm');

def orderInfo = [:];
def orderGoods = [];
def goodsCount = 0;
def textCode = [pay:false, close:false, delivery:false, receive:false, success:false];

if(order) {
	def order_status_text = OrderUtil.order_text(order.oss);
	
	if(order.oss!='0000'&&order.oss!='1010'&&order.oss!='9000') {
		textCode['pay'] = true;
	}
	if(order.oss=='9000') {
		textCode['close'] = true;
	}
	if(order.oss=='2000') {
		textCode['delivery'] = true;
	}
	if(order.oss=='3000') {
		textCode['receive'] = true;
	}
	
	List<OrderItem> x_items = OrderService.INSTANCE().find_order_items(order);
	x_items.each({
		item->
		goodsCount += item.buy_num;
		orderGoods.add([list_pic_url:item.pic_url]);
	});
	//def d = [id:order.id, add_time:_sdf_.format(order.a_time), offline_pay:0, order_status_text:order_status_text, goodsCount:goodsCount, actual_price:order.pay_amount/100, freight_price:0, goodsList:goodsList];
	//orderGoods.add(d);

	orderInfo['id'] = order.id;
	orderInfo['order_sn'] = order.order_no; 
	orderInfo['add_time'] = _sdf_.format(order.a_time);
	orderInfo['order_status_text'] = order_status_text;
	orderInfo['consignee'] = order.buyername?order.buyername:'';
	orderInfo['mobile'] = order.buyerphone?order.buyerphone:'';
	orderInfo['full_region'] = order.buyerprovincename ? order.buyerprovincename:'' + order.buyercityname? order.buyercityname:'' + order.buyerzonename? order.buyerzonename:'';
	orderInfo['address'] = order.buyeraddr;
	
	orderInfo['shipping_status'] = 1;
	orderInfo['goods_price'] = order.total_amount/100;
	orderInfo['freight_price'] = 0;
	orderInfo['postscript'] = order.remark;
	orderInfo['actual_price'] = order.pay_amount/100;
	orderInfo['change_price'] = order.pay_amount/100;
	
	//获取订单付款信息
	
}

return ProtObj.success([goodsCount:goodsCount, orderInfo:orderInfo, orderGoods:orderGoods, textCode:textCode]);

