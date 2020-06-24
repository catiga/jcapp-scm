package com.jeancoder.scm.entry.incall.ampi.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AccountSession
import com.jeancoder.scm.ready.dto.FrontOrderAddr
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.ShoppingCart
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = GlobalHolder.proj.id;

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

if(request_data==null) {
	return ProtObj.fail(110001, '请检查参数');
}
try {
	request_data = JackSonBeanMapper.jsonToMap(request_data);
} catch(any) {
	return ProtObj.fail(110001, '信息参数错误');
}

def addressId = request_data['addressId'];
def offlinePay = request_data['offlinePay'];
def postscript = request_data['postscript'];
def actualPrice = request_data['actualPrice'];
def freightPrice = request_data['freightPrice'];

//查找收货地址
def checkedAddress = null;
if(addressId &&addressId!='0') {
	SimpleAjax ajax = JC.internal.call(SimpleAjax, 'crm', '/h5/address/get', [pid:pid, id:addressId]);
	if(ajax && ajax.available && ajax.data) {
		checkedAddress = ajax.data;
	}
}
if(!checkedAddress) {
	return ProtObj.fail(110002, '请选择收货地址');
}
AccountSession session = new AccountSession(ap_id:ap_id);
FrontOrderAddr order_addr = new FrontOrderAddr();
order_addr.session = session;
order_addr.provincecode = checkedAddress['province_code'];
order_addr.provincename = checkedAddress['province'];
order_addr.citycode = checkedAddress['city_code'];
order_addr.cityname = checkedAddress['city'];
order_addr.zonecode = checkedAddress['zone_code'];
order_addr.zonename = checkedAddress['zone'];
order_addr.address = checkedAddress['address'];
order_addr.contact_name = checkedAddress['name'];
order_addr.contact_phone = checkedAddress['mobile'];
order_addr.point = '';

def goods_list = []; //GoodsInfo, GoodsSku, num
//查找购物车数据，只选择用户选中的购物车商品
def sql = 'select * from ShoppingCart where pid=? and flag=? and ap_id=? order by c_time desc';
List<ShoppingCart> result = JcTemplate.INSTANCE().find(ShoppingCart, sql, pid, 0, ap_id);
if(!result) {
	return ProtObj.fail(110001, '购物车商品为空');
}
//查找购物车对应商品
for(x in result) {
	GoodsInfo g = JcTemplate.INSTANCE().get(GoodsInfo, 'select a.* from GoodsInfo a where a.flag!=? and a.proj_id=? and a.id=?', -1, pid, x.goods_id);
	GoodsSku sku = null;
	if(x.goods_sku_id) {
		sku = JcTemplate.INSTANCE().get(GoodsSku, 'select * from GoodsSku where flag!=? and goods_id=? and id=?', -1, x.goods_id, x.goods_sku_id);
	}
	goods_list.add([g, sku, x.number, '']);
}

if(goods_list.empty) {
	return ProtObj.fail(110001, '购物车商品为空');
}

def order_result = OrderService.INSTANCE().create_order_front(goods_list, null, '130', order_addr, pid, postscript);
OrderInfo order = order_result[0];
List<OrderItem> order_items = order_result[1];
order.items = order_items;

def order_data = JackSonBeanMapper.toJson(order);

def tnum = '';
//开始去交易中心注册订单
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'1000','od':order_data, tnum:tnum, pid:pid]);

def ret_data = [:];
ret_data['orderInfo'] = order;
ret_data['tradeInfo'] = trade.data;

return ProtObj.success(ret_data);


