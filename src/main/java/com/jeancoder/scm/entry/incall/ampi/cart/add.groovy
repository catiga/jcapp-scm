package com.jeancoder.scm.entry.incall.ampi.cart

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.ShoppingCart
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

/**

{"addType":0,"goodsId":"1086015","number":2,"productId":251}

*/

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

def addType = request_data['addType'];
def goodsId = request_data['goodsId'];
def productId = request_data['productId'];
def number = request_data['number'];

//开始校验商品ID
GoodsInfo goods = JcTemplate.INSTANCE().get(GoodsInfo, 'select * from GoodsInfo where id=? and proj_id=? and flag!=?', goodsId, GlobalHolder.proj.id, -1);
if(goods==null) {
	return ProtObj.fail(220001, '商品已经下架');
}

GoodsSku goods_sku = JcTemplate.INSTANCE().get(GoodsSku, 'select * from GoodsSku where goods_id=? and id=? and flag!=?', goods.id, productId, -1);


ShoppingCart s_cart = new ShoppingCart();
s_cart.a_time = new Date();
s_cart.add_price = goods.goods_price;
s_cart.ap_id = ap_id;
s_cart.basic_id = 0;
s_cart.user_name = front_user_case['realname'];
s_cart.nick_name = front_user_case['nickname'];

s_cart.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
s_cart.flag = 0;
s_cart.goods_id = goods.id;
s_cart.goods_name = goods.goods_name;
s_cart.goods_pic = goods.goods_picturelink;
s_cart.goods_no = goods.goods_id;
s_cart.goods_pic = goods.goods_picturelink;
s_cart.weight = goods.weight;
s_cart.retail_price = goods.goods_price;

s_cart.goods_sku_id = goods_sku!=null?goods_sku.id: 0;
s_cart.goods_sku_name = goods_sku!=null?goods_sku.skus:'';

s_cart.is_on_sale = 1;

s_cart.number = number;
s_cart.pid = GlobalHolder.proj.id;

s_cart.id = JcTemplate.INSTANCE().save(s_cart);

def data = [:];
data['cartTotal'] = [goodsCount:number];

return ProtObj.success(data);

