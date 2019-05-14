package com.jeancoder.scm.internal.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AccountSession
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.prefer.CouponPrefer
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.OrderService

/**
 * '/incall/cashier/create_order?gs=******'
 * 创建商品订单
 * 商品协议串
 * 参数名称：gs, s_id
 * gs参数格式：tyc,商品id,SKU_ID,数量,remark;商品id,SKU_ID,数量,remark
 *           普通商品：tyc=100
 *           套餐商品：tyc=200
 * s_id参数：门店id
 * @author jackielee
 */

//首先校验当前用户
def apid = BigInteger.valueOf(Long.valueOf(JC.internal.param('apid')));
AccountSession user_session = new AccountSession(ap_id:apid);

def pid = JC.internal.param('pid');

def on = JC.internal.param('on')?.toString()?.trim();
def coupon_id = JC.internal.param('coupon_id');

OrderService order_service = OrderService.INSTANCE();

List<OrderInfo> orders = order_service.find_order_by_num(pid, on);
if(orders==null||orders.empty) {
	return SimpleAjax.available();
}

OrderInfo o = orders.get(0);
if(o.getBuyerid().toString()!=user_session?.ap_id?.toString()) {
	return SimpleAjax.available();
}

List<OrderItem> items = order_service.find_order_items(o);
o.items = items;
def pref_data = null;
if(coupon_id) {
	GoodsService goods_service = GoodsService.INSTANCE();
	def param = [];
	
	for(x in items) {
		GoodsInfo g = goods_service.get(x.goods_id, o.pid);
		if(g==null) {
			//该商品调过，不减金额
			continue;
		}
		def cats = goods_service.get_goods_cats(x.goods_id, x.tycode);
		param.add([x.goods_id, x.tycode, cats, x.unit_amount, x.buy_num]);
	}
	CouponPrefer computer = new CouponPrefer();
	
	def order_param = [:];
	order_param['on'] = o.order_no;
	order_param['oc'] = '1000';
	order_param['pid'] = o.pid;
	order_param['id'] = o.id;
	order_param['sid'] = o.store_id;
	
	def op = '';	//使用传use
	SimpleAjax ret_obj = computer.compute(order_param, coupon_id, op, param);
	
	pref_data = ret_obj;
}


return SimpleAjax.available('', [o, pref_data]);












