package com.jeancoder.scm.entry.incall.api.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.OrderShip
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.order.OrderConstants
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.OrderUtil

def pn = JC.request.param('page');
def logistic_code = JC.request.param('logistic_code');	//快递单号

def status = JC.request.param('status');

def ps = JC.request.param('size');

try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
try {
	ps = Integer.valueOf(ps);
	if(ps<1) {
		ps = 10;
	}
}catch(any){
	ps = 10;
}
JcPage<OrderInfo> page = new JcPage<OrderInfo>();
page.pn = pn;
page.ps = ps;

def o_num = null;
def g_num = null;

//查找订单列表
def pid = GlobalHolder.proj.id;
def params = [];
String sql = 'select * from OrderInfo where flag!=?';
params.add(-1);
sql += ' and pid=?';
params.add(pid);

if(o_num!=null&&!o_num.toString().trim().equals('')) {
	sql = sql + ' and order_no=?'
	params.add(o_num);
}
if(g_num!=null&&!g_num.toString().trim().equals('')) {
	sql = sql + ' and id in (select order_id from OrderItem where goods_no=?)';
	params.add(g_num);
}

if(status) {
	status = status.split(',');
	if(status) {
		sql += ' and oss in (';
		for(x in status) {
			sql = sql + '?,';
			params.add(x);
		}
		sql = sql.substring(0, sql.length() - 1) + ')';
	}
}
sql = sql + ' order by a_time desc';

page = JcTemplate.INSTANCE().find(OrderInfo.class, page, sql, params.toArray());

SimpleDateFormat _sdf_yyyyMMdd_hh = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
def data = [];
if(page.result) {
	for(x in page.result) {
		def offline_pay = 0;	//是否为线下支付订单，默认为否
		if(x.oss==OrderConstants._order_status_payed_cod_) {
			offline_pay = 1;
		}
		def order_type = x.dss;	//订单类型，派送；自取；送餐到座等
		def pay_name = x.pay_type;
		def full_region = x.buyerprovincename?x.buyerprovincename:'' + x.buyercityname?x.buyercityname:'' + x.buyerzonename?x.buyerzonename:'';
		def order_status_text = OrderUtil.order_text(x.oss);
		def a_time = x.a_time?_sdf_yyyyMMdd_hh.format(new Date(x.a_time.getTime())):null;
		def pay_time = x.pay_time?_sdf_yyyyMMdd_hh.format(new Date(x.pay_time.getTime())):null;
		def print_info = '';
		
		def expressInfo = "";
		//查找订单的快递信息
		OrderShip order_ship = JcTemplate.INSTANCE().get(OrderShip, 'select * from OrderShip where flag!=? and order_id=?', -1, x.id);
		if(order_ship) {
			expressInfo = order_ship.exp_name + ':' + order_ship.exp_odd;
		}
		
		//查找当前订单对应的商品信息
		List<OrderItem> order_items = OrderService.INSTANCE().get_goods_Item_order(x.id);
		def goodsCount = 0;
		def goodsList = [];
		if(order_items) {
			goodsCount = order_items.size();
			for(y in order_items) {
				def g_obj = ["goods_name":y.goods_sku_name,"goods_aka":y.goods_name,
					"list_pic_url":y.pic_url,"number":y.buy_num, 
					"goods_specifition_name_value":"棕色","retail_price":y.pay_amount/100];
				goodsList.add(g_obj);
				print_info = y.goods_name + '<br/>';
			}
		}
		
		//查找订单对应的用户信息
		def userInfo = ["nickname":"","name":"","mobile":"","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5zIcg1WdC0mUvIqhIHKqxXT8tlMKm75rJOJ0aZTa03UB7BaAoTCxFyAfn2BF3icZANRbzFyJKkeGw/132"];
		if(x.buyerid) {
			SimpleAjax user_info = JC.internal.call(SimpleAjax, 'crm','/h5/p/info_by_apid', [apid:x.buyerid]);
			if(user_info.available && user_info.data) {
				userInfo['nickname'] = user_info.data['nickname'];
				userInfo['name'] = user_info.data['realname'];
				userInfo['mobile'] = user_info.data['mobile'];
				userInfo['avatar'] = user_info.data['head'];
			}
		}
		
		def order = [id:x.id, order_sn:x.order_no, user_id:x.buyerid, order_status:x.oss,"offline_pay":offline_pay,"shipping_status":0,"print_status":1, "pay_status":2,
			"consignee":x.buyername,"country":0,"province":x.buyerprovincecode,"city":x.buyercitycode,"district":x.buyerzonecode,"address":x.buyeraddr,"mobile":x.buyerphone,"postscript":"",
			"print_info":print_info,"order_type":order_type,"is_delete":0,
			"add_time":a_time,"pay_time":pay_time, 
			"admin_memo":x.admin_memo, ,"remark":x.remark,
			"full_region":full_region,"order_status_text":order_status_text,
			"change_price":x.total_amount/100,"actual_price":x.pay_amount/100,"order_price":x.total_amount/100,"goods_price":x.total_amount/100,
			"pay_name":pay_name,
			"goodsCount":goodsCount, "goodsList":goodsList,
			"userInfo": userInfo,
			"shipping_fee":0,"pay_id":"4200000422201911290938060299",
			"shipping_time":0,"confirm_time":0,"dealdone_time":0,"freight_price":0,"express_value":480,
			"expressInfo":expressInfo
		];
		data.add(order);
	}
}

return ProtObj.success(["count":page.totalCount,"totalPages":page.totalPages,"pageSize":page.ps,"currentPage":page.pn, data:data]);

/*
return 
["errno":0,"errmsg":"",
	"data":["count":3,"totalPages":1,"pageSize":10,"currentPage":1,
		"data":[
			["id":1330,"order_sn":"20191005170626623520","user_id":1048,"order_status":300,"offline_pay":1,"shipping_status":0,"print_status":0,
				"pay_status":2,"consignee":"测试","country":0,"province":3,"city":38,"district":422,"address":"测试地址","print_info":"1、懒人椅【1】 ",
				"mobile":"13333232323","postscript":"","admin_memo":null,"shipping_fee":0,"pay_name":"","pay_id":"4200000422201911290938060299",
				"change_price":0.01,"actual_price":0.01,"order_price":0.01,"goods_price":0.01,"add_time":"2019-11-29 17:06:26","pay_time":"2019-11-29 17:06:31",
				"shipping_time":0,"confirm_time":0,"dealdone_time":0,"freight_price":0,"express_value":480,"remark":"需电联客户请优先派送勿放快递柜","order_type":0,"is_delete":0,
				"goodsList":[["goods_name":"日式和风懒人沙发","goods_aka":"懒人椅","list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","number":1,"goods_specifition_name_value":"棕色","retail_price":0.01]],
				"goodsCount":1,"userInfo":["nickname":"哄哄","name":"","mobile":"","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5zIcg1WdC0mUvIqhIHKqxXT8tlMKm75rJOJ0aZTa03UB7BaAoTCxFyAfn2BF3icZANRbzFyJKkeGw/132"],"full_region":"天津市天津市和平区","order_status_text":"待发货","expressInfo":"圆通速递YT2880409397161"],
			
			["id":1328,"order_sn":"20191005133123894325","user_id":1048,"order_status":300,"offline_pay":0,"shipping_status":0,"print_status":0,
				"pay_status":2,"consignee":"测试","country":0,"province":3,"city":38,"district":422,"address":"测试地址","print_info":"1、懒人椅【1】 ",
				"mobile":"13333232323","postscript":"","admin_memo":null,"shipping_fee":0,"pay_name":"","pay_id":"4200000432201911295554286798",
				"change_price":0.01,"actual_price":0.01,"order_price":0.01,"goods_price":0.01,"add_time":"2019-11-29 13:31:23","pay_time":"2019-11-29 13:31:26",
				"shipping_time":0,"confirm_time":0,"dealdone_time":0,"freight_price":0,"express_value":480,"remark":"需电联客户请优先派送勿放快递柜","order_type":0,"is_delete":0,
				"goodsList":[["goods_name":"日式和风懒人沙发","goods_aka":"懒人椅","list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","number":1,"goods_specifition_name_value":"棕色","retail_price":0.01]],
				"goodsCount":1,"userInfo":["nickname":"哄哄","name":"","mobile":"","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5zIcg1WdC0mUvIqhIHKqxXT8tlMKm75rJOJ0aZTa03UB7BaAoTCxFyAfn2BF3icZANRbzFyJKkeGw/132"],"full_region":"天津市天津市和平区","order_status_text":"待发货","expressInfo":""],
			
			["id":1325,"order_sn":"20191005104128963425","user_id":1048,"order_status":300,"offline_pay":0,"shipping_status":0,"print_status":0,
				"pay_status":2,"consignee":"测试","country":0,"province":3,"city":38,"district":422,"address":"测试地址","print_info":"1、懒人椅【1】 ",
				"mobile":"13333232323","postscript":"","admin_memo":null,"shipping_fee":0,"pay_name":"","pay_id":"4200000443201911295307326072",
				"change_price":0.1,"actual_price":0.1,"order_price":0.1,"goods_price":0.1,"add_time":"2019-11-29 10:41:28","pay_time":"2019-11-29 10:42:42",
				"shipping_time":0,"confirm_time":0,"dealdone_time":0,"freight_price":0,"express_value":480,"remark":"需电联客户请优先派送勿放快递柜","order_type":0,"is_delete":0,
				"goodsList":[["goods_name":"日式和风懒人沙发","goods_aka":"懒人椅","list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","number":1,"goods_specifition_name_value":"棕色","retail_price":0.1]],
				"goodsCount":1,"userInfo":["nickname":"哄哄","name":"","mobile":"","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5zIcg1WdC0mUvIqhIHKqxXT8tlMKm75rJOJ0aZTa03UB7BaAoTCxFyAfn2BF3icZANRbzFyJKkeGw/132"],"full_region":"天津市天津市和平区","order_status_text":"待发货","expressInfo":""]
		]
	]
];
*/

