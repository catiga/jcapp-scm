package com.jeancoder.scm.entry.incall.ampi.cart

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.ShoppingCart
import com.jeancoder.scm.ready.incall.api.ProtObj

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = front_user_case['pid'];

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

if(request_data==null) {
	return ProtObj.fail(110001, '请检查参数');
}
try {
	request_data = JackSonBeanMapper.jsonToMap(request_data);
} catch(any) {
	return ProtObj.fail(110001, '信息参数错误');
}

//用户勾选的购物车里面的商品项目
def productIds = request_data['productIds'];
def isChecked = request_data['isChecked'];

productIds = productIds?productIds.toString().split(',') : [];

if(productIds) {
	//直接执行相关更新操作
	def sql = 'update ShoppingCart';
	if(isChecked.toString()=='0') {
		//取消选中
		sql += ' set flag=1';
	} else {
		//选中
		sql += ' set flag=0';
	}
	sql += ' where pid=? and ap_id=? and flag!=?';
	def params = []; params.add(pid); params.add(ap_id); params.add(-1);
	sql += ' and id in (';
	for(x in productIds) {
		sql += '?,';
		params.add(x);
	}
	sql = sql.substring(0, sql.length() - 1) + ')';
	def update_result = JcTemplate.INSTANCE().batchExecute(sql, params.toArray());
	println update_result;
}

//查找购物车数据
def sql = 'select * from ShoppingCart where pid=? and ap_id=? and flag!=?';
def params = []; params.add(pid); params.add(ap_id); params.add(-1);
sql += ' order by c_time desc';
List<ShoppingCart> result = JcTemplate.INSTANCE().find(ShoppingCart, sql, params.toArray());

def goodsCount = 0;
def goodsAmount = new BigDecimal('0.00');
def checkedGoodsCount = 0;
def checkedGoodsAmount = new BigDecimal('0.00');
def numberChange = 0;


def data = [];

if(result) {
	for(x in result) {
		def x_item = [id:x.id, user_name:x.user_name, nick_name:x.nick_name, goods_id:x.goods_id, goods_no:x.goods_no, goods_name:x.goods_name,
			goods_pic:x.goods_pic, goods_sku_id:x.goods_sku_id, goods_sku_no:x.goods_sku_no, goods_sku_name:x.goods_sku_name, weight:x.weight,
			number:x.number, add_price:x.add_price, retail_price:x.retail_price, is_on_sale:x.is_on_sale, a_time:x.a_time, flag:x.flag];
		x_item['checked'] = x.flag==0?1:0;
		
		if(x_item['checked']==1) {
			goodsCount += x.number;
			goodsAmount = goodsAmount.add(x.add_price);
			checkedGoodsAmount = goodsCount;
			checkedGoodsAmount = goodsAmount;
		}
		data.add(x_item);
	}
}

def cartTotal = ["goodsCount":goodsCount,"goodsAmount":goodsAmount,"checkedGoodsCount":checkedGoodsCount,"checkedGoodsAmount":checkedGoodsAmount,"user_id":ap_id,"numberChange":numberChange];
def cartList = data;

return ProtObj.success([cartList:cartList, cartTotal:cartTotal]);

