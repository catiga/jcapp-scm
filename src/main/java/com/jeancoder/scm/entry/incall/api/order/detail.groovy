package com.jeancoder.scm.entry.incall.api.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.OrderShip
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.OrderUtil

def order_id = JC.request.param('orderId');

SimpleDateFormat _sdf_yyyyMMdd_hh = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');

OrderInfo order = OrderService.INSTANCE().get(order_id);

def x = order;

def offline_pay = 0;	//是否为线下支付订单，默认为否
def order_type = x.dss;	//订单类型，派送；自取；送餐到座等
def pay_name = x.pay_type;
def full_region = x.buyerprovincename?x.buyerprovincename:'' + x.buyercityname?x.buyercityname:'' + x.buyerzonename?x.buyerzonename:'';
def order_status_text = OrderUtil.order_text(x.oss);
def a_time = x.a_time?_sdf_yyyyMMdd_hh.format(new Date(x.a_time.getTime())):null;
def pay_time = x.pay_time?_sdf_yyyyMMdd_hh.format(new Date(x.pay_time.getTime())):null;

def print_info= '';
BigDecimal shipping_fee = 0;
BigDecimal exp_value = 0;
def exp_tips = '';
//查找当前订单对应的发货信息
def sql = 'select * from OrderShip where flag!=? and order_id=? and pid=?';
OrderShip order_ship = JcTemplate.INSTANCE().get(OrderShip, sql, -1, order_id, GlobalHolder.proj.id);
if(order_ship) {
	print_info = order_ship.exp_str;	//快递单上打印的预设内容
	shipping_fee = order_ship.exp_price?order_ship.exp_price/100:0;
	exp_value = order_ship.exp_value?order_ship.exp_value/100:0;
	exp_tips = order_ship.exp_tips;
}

//查找当前订单对应的商品信息
List<OrderItem> order_items = OrderService.INSTANCE().get_goods_Item_order(x.id);
def goodsCount = 0;
def goodsList = [];
if(order_items) {
	goodsCount = order_items.size();
	for(y in order_items) {
		def g_obj = ["id":y.goods_id,"product_id":y.goods_sku_id,"goods_name":y.goods_sku_name?y.goods_sku_name:y.goods_name,"goods_aka":y.goods_name,
				"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","number":y.buy_num,"goods_specifition_name_value":"棕色",
				"retail_price":y.pay_amount/100,"goods_id":y.goods_sku_id,"goods_sn":y.goods_no, 'remark':y.remark]
		goodsList.add(g_obj);
	}
}

//查找订单对应的用户信息
SimpleAjax user_info = JC.internal.call(SimpleAjax, 'crm','/h5/p/info_by_apid', [apid:x.buyerid]);
def userInfo = ["nickname":"","name":"","mobile":"","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5zIcg1WdC0mUvIqhIHKqxXT8tlMKm75rJOJ0aZTa03UB7BaAoTCxFyAfn2BF3icZANRbzFyJKkeGw/132"];
if(user_info.available && user_info.data) {
	userInfo['nickname'] = user_info.data['nickname'];
	userInfo['name'] = user_info.data['realname'];
	userInfo['mobile'] = user_info.data['mobile'];
	userInfo['avatar'] = user_info.data['head'];
}

def total_obj = [:];

def order_obj = [id:x.id, order_sn:x.order_no, user_id:x.buyerid, order_status:x.oss,"offline_pay":offline_pay,"shipping_status":0,"print_status":0, 
	"pay_status":2, "consignee":x.buyername,"country":0,"province":x.buyerprovincecode,"city":x.buyercitycode,"district":x.buyerzonecode,"address":x.buyeraddr,"mobile":x.buyerphone,"postscript":x.remark,
	"print_info":print_info,"order_type":order_type,"is_delete":0,
	"add_time":a_time,"pay_time":pay_time,
	"admin_memo":x.admin_memo, ,"remark":exp_tips,
	"full_region":full_region,"order_status_text":order_status_text,
	"change_price":x.total_amount/100,"actual_price":x.pay_amount/100,"order_price":x.total_amount/100,"goods_price":x.total_amount/100,
	"pay_name":pay_name,
	"goodsCount":goodsCount, "goodsList":goodsList,
	"userInfo": userInfo,
	"shipping_fee":shipping_fee,"pay_id":"4200000422201911290938060299",
	"shipping_time":0,"confirm_time":0,"dealdone_time":0,"freight_price":0,"express_value":exp_value
];
order_obj['goodsCount'] = goodsCount;
order_obj['goodsList'] = goodsList;
order_obj['user_name'] = user_info.data['nickname'];
order_obj['avatar'] = user_info.data['head'];

total_obj['orderInfo'] = order_obj;
total_obj['receiver'] = ["name":x.buyername,"mobile":x.buyerphone,"province":x.buyerprovincename,"province_id":x.buyerprovincecode,"city":x.buyercityname,"city_id":x.buyercitycode,"district":x.buyerzonename,"district_id":x.buyerzonecode,"address":x.buyeraddr];
total_obj['sender'] = ["name":"海鸥实验室","mobile":"13599888877","province":"浙江省","city":"杭州市","district":"上城区","province_id":'330000',"city_id":'330100',"district_id":'330102',"address":"嘉绿景苑"];

return ProtObj.success(total_obj);

/*
return 
["errno":0,"errmsg":"",
	"data":
	[
		"orderInfo":
		[
			"id":1330,"order_sn":"20191005170626623520","user_id":1048,"order_status":300,"offline_pay":1,"shipping_status":0,"print_status":0,
			"pay_status":2,"consignee":"测试","country":0,"province":3,"city":38,"district":422,"address":"测试地址","print_info":"1、懒人椅【1】 ",
			"mobile":"13333232323","postscript":"","admin_memo":null,"shipping_fee":0,"pay_name":"","pay_id":"4200000422201911290938060299",
			"change_price":0.01,"actual_price":0.01,"order_price":0.01,"goods_price":0.01,"add_time":"2019-11-29 17:06:26","pay_time":"2019-11-29 17:06:31",
			"shipping_time":0,"confirm_time":0,"dealdone_time":0,"freight_price":0,"express_value":480,"remark":"需电联客户请优先派送勿放快递柜","order_type":0,
			"is_delete":0,
			"goodsList":[
				["id":1478,"product_id":246,"goods_name":"日式和风懒人沙发","goods_aka":"懒人椅",
				"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","number":1,"goods_specifition_name_value":"棕色",
				"retail_price":0.01,"goods_id":1009024,"goods_sn":"12313"]
			],
			"goodsCount":1,"user_name":"哄哄","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5zIcg1WdC0mUvIqhIHKqxXT8tlMKm75rJOJ0aZTa03UB7BaAoTCxFyAfn2BF3icZANRbzFyJKkeGw/132",
			"full_region":"天津市天津市和平区","order_status_text":"待发货","allAddress":"天津市天津市和平区测试地址"
		],
		"receiver":["name":"测试","mobile":"13333232323","province":"天津市","province_id":3,"city":"天津市","city_id":38,"district":"和平区","district_id":422,"address":"测试地址"],
		"sender":["name":"海鸥实验室","mobile":"13599888877","province":"浙江省","city":"舟山市","district":"普陀区","province_id":12,"city_id":123,"district_id":1362,"address":"嘉绿景苑"]
	]
];
*/
		
		
		
		
