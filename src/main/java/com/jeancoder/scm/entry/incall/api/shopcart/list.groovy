package com.jeancoder.scm.entry.incall.api.shopcart

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.ShoppingCart
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def p = JC.request.param('page');
def name = JC.request.param('name');

def pn = 1;
try {
	pn = Integer.valueOf(p);
} catch(any) {
}

JcPage<ShoppingCart> page = new JcPage<ShoppingCart>();
page.pn = pn;
page.ps = 10;

def pid = GlobalHolder.proj.id;
//查找购物车列表
def sql = 'select * from ShoppingCart where flag!=? and pid=?';
if(name) {
	sql = sql + ' and goods_name like "%' + name + '%"';
}
sql += ' order by a_time desc';

page = JcTemplate.INSTANCE().find(ShoppingCart, page, sql, -1, pid);

def count = page.totalCount;
def totalPages = page.totalPages;
def pageSize = page.ps;
def currentPage = page.pn;

def data = [];
SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
for(x in page.result) {
	def d = ["id":x.id,"user_id":x.ap_id,"goods_id":x.goods_id,"goods_sn":x.goods_no,"product_id":x.goods_sku_id,"goods_name":x.goods_name,"goods_aka":x.goods_sku_name,"goods_weight":x.weight,"add_price":x.add_price,"retail_price":x.retail_price,"number":x.number,"goods_specifition_name_value":"1.5m*1m","goods_specifition_ids":"45","checked":1,"list_pic_url":x.goods_pic,"freight_template_id":15,"is_on_sale":1,"add_time":_sdf_.format(x.a_time),"is_fast":0,"is_delete":x.flag,"nickname":x.user_name];
	data.add(d);
}

return ProtObj.success([count:count, totalPages:totalPages, pageSize:pageSize, currentPage:currentPage, data:data]);

/*
return ["errno":0,"errmsg":"",
	"data":["count":1796,"totalPages":180,"pageSize":10,"currentPage":1,
		"data":[
			["id":10701,"user_id":1691,"goods_id":1097004,"goods_sn":"1097004","product_id":120,"goods_name":"原素系列实木餐桌","goods_aka":"原素系列实木餐桌","goods_weight":1,"add_price":1699,"retail_price":1699,"number":1,"goods_specifition_name_value":"1.5m*1m","goods_specifition_ids":"45","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/54f822e9c542d20566c7f70f90d52ae6.png","freight_template_id":15,"is_on_sale":1,"add_time":"2020-05-10 21:32:31","is_fast":0,"is_delete":1,"nickname":"MY"],
			["id":10700,"user_id":1665,"goods_id":1086015,"goods_sn":"1086015","product_id":103,"goods_name":"支付测试老板向","goods_aka":"11","goods_weight":1,"add_price":49.9,"retail_price":49.9,"number":1,"goods_specifition_name_value":"黑色","goods_specifition_ids":"47","checked":0,"list_pic_url":"http://yanxuan.nosdn.127.net/d5c2ecfe0fb00cdd8b829975bab21a31.png","freight_template_id":15,"is_on_sale":1,"add_time":"2020-05-10 19:40:30","is_fast":0,"is_delete":0,"nickname":"无题*_*"],
			["id":10699,"user_id":1665,"goods_id":1135055,"goods_sn":"1135055","product_id":210,"goods_name":"北欧印象几何条纹混色窗帘","goods_aka":"北欧印象几何条纹混色窗帘","goods_weight":1,"add_price":399,"retail_price":399,"number":1,"goods_specifition_name_value":"1套","goods_specifition_ids":"71","checked":0,"list_pic_url":"http://yanxuan.nosdn.127.net/87b6a608b99279ebf1764682e9e5fcec.png","freight_template_id":15,"is_on_sale":1,"add_time":"2020-05-10 19:39:55","is_fast":0,"is_delete":0,"nickname":"无题*_*"],
			["id":10698,"user_id":1665,"goods_id":1135055,"goods_sn":"1135055","product_id":210,"goods_name":"北欧印象几何条纹混色窗帘","goods_aka":"北欧印象几何条纹混色窗帘","goods_weight":1,"add_price":399,"retail_price":399,"number":1,"goods_specifition_name_value":"1套","goods_specifition_ids":"71","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/87b6a608b99279ebf1764682e9e5fcec.png","freight_template_id":15,"is_on_sale":1,"add_time":"2020-05-10 19:39:50","is_fast":1,"is_delete":1,"nickname":"无题*_*"],
			["id":10697,"user_id":1666,"goods_id":1083009,"goods_sn":"1083008","product_id":249,"goods_name":"海洋之心永生花","goods_aka":"海洋之心","goods_weight":1,"add_price":90,"retail_price":90,"number":1,"goods_specifition_name_value":"一盒","goods_specifition_ids":"10","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/76e5c820f6bb71a26517ffa01f499871.png","freight_template_id":14,"is_on_sale":1,"add_time":"2020-05-10 18:15:11","is_fast":1,"is_delete":1,"nickname":"张起灵丢失的外套。"],
			["id":10696,"user_id":1665,"goods_id":1009024,"goods_sn":"12313","product_id":246,"goods_name":"支付测试兼打赏","goods_aka":"懒人椅","goods_weight":2,"add_price":0.5,"retail_price":0.5,"number":1,"goods_specifition_name_value":"一根葱","goods_specifition_ids":"7","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-05-10 16:17:24","is_fast":1,"is_delete":1,"nickname":"无题*_*"],
			["id":10695,"user_id":1665,"goods_id":1009024,"goods_sn":"1234","product_id":251,"goods_name":"支付测试兼打赏","goods_aka":"懒人椅","goods_weight":2,"add_price":5,"retail_price":5,"number":1,"goods_specifition_name_value":"一个面包","goods_specifition_ids":"77","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-05-10 16:09:15","is_fast":1,"is_delete":1,"nickname":"无题*_*"],
			["id":10694,"user_id":1665,"goods_id":1009024,"goods_sn":"12313","product_id":246,"goods_name":"支付测试兼打赏","goods_aka":"懒人椅","goods_weight":2,"add_price":0.5,"retail_price":0.5,"number":1,"goods_specifition_name_value":"一根葱","goods_specifition_ids":"7","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-05-10 16:02:26","is_fast":1,"is_delete":1,"nickname":"无题*_*"],
			["id":10693,"user_id":1371,"goods_id":1009024,"goods_sn":"12313","product_id":246,"goods_name":"支付测试兼打赏","goods_aka":"懒人椅","goods_weight":2,"add_price":0.5,"retail_price":0.5,"number":1,"goods_specifition_name_value":"一根葱","goods_specifition_ids":"7","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-05-10 15:30:14","is_fast":1,"is_delete":1,"nickname":"林邵"],
			["id":10692,"user_id":1687,"goods_id":1009024,"goods_sn":"12313","product_id":246,"goods_name":"支付测试兼打赏","goods_aka":"懒人椅","goods_weight":2,"add_price":0.01,"retail_price":0.01,"number":1,"goods_specifition_name_value":"一根葱","goods_specifition_ids":"7","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-05-10 14:51:43","is_fast":1,"is_delete":1,"nickname":"得意！"]
		]
	]
];
*/

