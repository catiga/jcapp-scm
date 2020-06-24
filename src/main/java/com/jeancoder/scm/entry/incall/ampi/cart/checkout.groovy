package com.jeancoder.scm.entry.incall.ampi.cart

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.ShoppingCart
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.incall.api.dto.goods.SkuWithGoods
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder

def addressId = JC.request.param('addressId');
def addType = JC.request.param('addType');
def orderFrom = JC.request.param('orderFrom');
def type = JC.request.param('type');

def pid = GlobalHolder.proj.id;

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];

BigDecimal _000_ = new BigDecimal('0.00');

def data = [:];

def checkedGoodsList = [];	//用户选中商品
def checkedAddress = 0;		//用户选择收货地址
BigDecimal actualPrice = _000_;	//实际价格
BigDecimal freightPrice = _000_;	//运费价格
BigDecimal goodsTotalPrice = _000_;	//商品总价
BigDecimal orderTotalPrice = _000_;	//订单总价
def goodsCount = 0;
def outStock = 0;		//库存状态

//查找购物车数据，只选择用户选中的购物车商品
def sql = 'select * from ShoppingCart where pid=? and flag=? and ap_id=? order by c_time desc';
List<ShoppingCart> result = JcTemplate.INSTANCE().find(ShoppingCart, sql, pid, 0, ap_id);
if(!result) {
	return ProtObj.fail(110001, '购物车商品为空');
}

List<?> shopcart_goods = [];
//查找购物车对应商品
for(x in result) {
	sql = 'select a.*, b.id as sku_id, b.sku_price, b.weight as sku_weight from GoodsInfo a left join GoodsSku b on a.id=b.goods_id where a.flag!=? and a.proj_id=? and b.flag!=?';
	def params = []; params.add(-1); params.add(pid); params.add(-1);
	sql += ' and a.id=?'; params.add(x.goods_id);
	if(x.goods_sku_id) {
		sql += ' and b.id=?'; params.add(x.goods_sku_id);
	}
	def r = JcTemplate.INSTANCE().find(SkuWithGoods, sql, params.toArray());
	shopcart_goods.add(r);
	
	goodsCount += x.number;	//计算商品总数量
	actualPrice = x.add_price.multiply(x.number).add(actualPrice);	//计算商品总金额
	freightPrice = freightPrice;		//计算商品运费
	goodsTotalPrice = x.retail_price.multiply(x.number).add(goodsTotalPrice);	//商品总金额
	orderTotalPrice = actualPrice;		//计算订单总金额
}

if(addressId &&addressId!='0') {
	SimpleAjax ajax = JC.internal.call(SimpleAjax, 'crm', '/h5/address/get', [pid:pid, id:addressId]);
	if(ajax && ajax.available && ajax.data) {
		checkedAddress = ajax.data;
	}
}

checkedGoodsList = shopcart_goods;

data['checkedGoodsList'] = checkedGoodsList;
data['checkedAddress'] = checkedAddress;
data['actualPrice'] = actualPrice;
data['freightPrice'] = freightPrice;
data['goodsTotalPrice'] = goodsTotalPrice;
data['orderTotalPrice'] = orderTotalPrice;
data['goodsCount'] = goodsCount;
data['outStock'] = outStock;

return ProtObj.success(data);

