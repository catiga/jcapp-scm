package com.jeancoder.scm.entry.incall.ampi.cart

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.ShoppingCart
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = front_user_case['pid'];
pid = GlobalHolder.proj.id;

//查找购物车数据
def sql = 'select * from ShoppingCart where pid=? and flag!=? and ap_id=? order by c_time desc';
List<ShoppingCart> result = JcTemplate.INSTANCE().find(ShoppingCart, sql, pid, -1, ap_id);

def goodsCount = 0;
def goodsAmount = new BigDecimal('0.00');
def checkedGoodsCount = 0;
def checkedGoodsAmount = new BigDecimal('0.00');
def numberChange = 0;
def data = [];
if(result) {
	for(x in result) {
		if(x.flag==0){
			goodsCount += x.number;
			goodsAmount = goodsAmount.add(x.add_price.multiply(new BigDecimal(x.number)));
			checkedGoodsCount = goodsCount;
			checkedGoodsAmount = goodsAmount;
		}
		
		def x_item = [id:x.id, user_name:x.user_name, nick_name:x.nick_name, goods_id:x.goods_id, goods_no:x.goods_no, goods_name:x.goods_name,
			goods_pic:x.goods_pic, goods_sku_id:x.goods_sku_id, goods_sku_no:x.goods_sku_no, goods_sku_name:x.goods_sku_name, weight:x.weight,
			number:x.number, add_price:x.add_price, retail_price:x.retail_price, is_on_sale:x.is_on_sale, a_time:x.a_time, flag:x.flag];
		x_item['checked'] = x.flag==0?1:0;
		data.add(x_item);
	}
}

def cartTotal = ["goodsCount":goodsCount,"goodsAmount":goodsAmount,"checkedGoodsCount":checkedGoodsCount,"checkedGoodsAmount":checkedGoodsAmount,"user_id":ap_id,"numberChange":numberChange];
def cartList = data;

return ProtObj.success([cartList:cartList, cartTotal:cartTotal]);

/*
return ["errno":0,"errmsg":"",
	"data":[
		"cartList":[],
		"cartTotal":["goodsCount":0,"goodsAmount":"0.00","checkedGoodsCount":0,"checkedGoodsAmount":"0.00","user_id":0,"numberChange":0]
	]
];
*/

