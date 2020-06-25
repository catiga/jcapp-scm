package com.jeancoder.scm.entry.incall.ampi.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.OrderUtil

def pn = JC.request.param('page');
def ps = JC.request.param('size');

try {
	pn = Integer.valueOf(pn);
	ps = Integer.valueOf(ps);
	if(pn<1) {
		pn = 1;
	}
	if(ps<8) {
		ps = 8;
	}
} catch(any) {
	pn = 1;
	ps = 8;
}
JcPage<OrderInfo> page = new JcPage<OrderInfo>();
page.pn = pn;
page.ps = ps;

/*
 * 0:全部
 * 1:待付款
 * 2:待发货
 * 3:待收货
 */
def showType = JC.request.param('showType');

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = GlobalHolder.proj.id;

def oss = null;
if(showType=='1') {
	oss = ['0000'];
} else if(showType=='2') {
	oss = ['1000', '1010'];
} else if(showType=='3') {
	oss = ['2000'];
}

JcPage<OrderInfo> user_orders = OrderService.INSTANCE().find_user_order(page, ap_id, null, oss);
def count = 0;
count = user_orders.totalCount;

SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm');
def data = [];
if(user_orders) {
	for(x in user_orders.result) {
		def order_status_text = OrderUtil.order_text(x.oss);
		
		List<OrderItem> x_items = OrderService.INSTANCE().find_order_items(x);
		def goodsCount = 0;
		def goodsList = [];
		x_items.each({
			item->
			goodsCount += item.buy_num;
			goodsList.add([list_pic_url:item.pic_url]);
		});
		def d = [id:x.id, add_time:_sdf_.format(x.a_time), offline_pay:0, order_status_text:order_status_text, goodsCount:goodsCount, actual_price:x.pay_amount/100, freight_price:0, goodsList:goodsList];
		data.add(d);
	}
}

return ProtObj.success([count:count, data:data]);


/**
{
                "id":9,
                "order_no":"130200624203959130001",
                "a_time":1593002399000,
                "c_time":1593003389000,
                "pay_time":null,
                "flag":0,
                "total_amount":1800.00,
                "pay_amount":1800.00,
                "oss":"9000",
                "dss":"130",
                "buyerid":7,
                "buyername":null,
                "buyerphone":null,
                "buyerprovincecode":"220000",
                "buyerprovincename":"吉林省",
                "buyercitycode":"220200",
                "buyercityname":"吉林市",
                "buyerzonecode":"220281",
                "buyerzonename":"",
                "buyeraddr":"325f",
                "buyerpoint":null,
                "pid":1,
                "store_id":null,
                "store_name":null,
                "ouid":null,
                "ouname":null,
                "pay_type":null,
                "remark":"--0",
                "admin_memo":null,
                "items":null
            }
 */



