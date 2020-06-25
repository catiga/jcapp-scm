package com.jeancoder.scm.entry.incall.ampi.catalog

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogIndexService
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

if(request_data==null) {
	return ProtObj.fail(110001, '请检查参数');
}
try {
	request_data = JackSonBeanMapper.jsonToMap(request_data);
} catch(any) {
	return ProtObj.fail(110001, '信息参数错误');
}

def pid = GlobalHolder.proj.id;

def cat_id = request_data['id'];
def pn = request_data['page'];
def ps = request_data['size'];
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
	if(ps < 8) {
		ps = 8;
	}
} catch(any) {
	ps = 8;
}

Catalog cat = CatalogService.INSTANCE().get(pid, cat_id);
if(cat==null) {
	cat_id = null;
}

def result = CatalogIndexService.INSTANCE.find_catalog_merge_goods(pid, cat_id, '100', pn, ps);

//构建返回对象
def count = 50;
def totalPages = 2;
def pageSize = ps;
def currentPage = pn;
def data = [];
for(x in result) {
	GoodsSku sku = x[0];
	GoodsInfo goods = x[1];
	
	def price = goods.goods_price!=null?goods.goods_price/100:'-';
	
	def d = ["name":goods.goods_name,"id":goods.id,"goods_brief":goods.goods_remark,"min_retail_price":price,"list_pic_url":goods.goods_picturelink,"goods_number":sku.stock];
	data.add(d);
}

return ProtObj.success(["count":count,"totalPages":totalPages,"pageSize":pageSize,"currentPage":currentPage, data:data])

/*
return ["errno":0,"errmsg":"",
	"data":[
		"count":48,"totalPages":6,"pageSize":8,"currentPage":1,
		"data":[
			["name":"支付测试兼打赏","id":1009024,"goods_brief":"此商品可以测试分享到朋友圈","min_retail_price":0.5,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","goods_number":22],
			["name":"海洋之心永生花","id":1083009,"goods_brief":"厄瓜多尔玫瑰，精致美感","min_retail_price":90,"list_pic_url":"http://yanxuan.nosdn.127.net/76e5c820f6bb71a26517ffa01f499871.png","goods_number":1000],
			["name":"支付测试老板向","id":1086015,"goods_brief":"开源不易，请打赏","min_retail_price":49.9,"list_pic_url":"http://yanxuan.nosdn.127.net/d5c2ecfe0fb00cdd8b829975bab21a31.png","goods_number":100],
			["name":"怀抱休闲椅组合","id":1116032,"goods_brief":"敦厚包裹感 葛优躺神器","min_retail_price":3499,"list_pic_url":"http://yanxuan.nosdn.127.net/45176a783387751fc07a07f5031dd62c.png","goods_number":0],
			["name":"纯棉水洗色织格夏凉被","id":1127052,"goods_brief":"100%棉填充，透气排汗，双面可用","min_retail_price":169,"list_pic_url":"http://yanxuan.nosdn.127.net/4f483526cfe3b953f403ae02049df5b9.png","goods_number":100],
			["name":"贝壳型凉感蓬松宠物窝垫","id":1130038,"goods_brief":"日本面料，简约条纹","min_retail_price":89,"list_pic_url":"http://yanxuan.nosdn.127.net/4d77296e02896675558f1a8a83742132.png","goods_number":100],
			["name":"宫廷奢华真丝四件套","id":1135002,"goods_brief":"100%桑蚕丝，丝滑润肤","min_retail_price":2599,"list_pic_url":"http://yanxuan.nosdn.127.net/45548f26cfd0c7c41e0afc3709d48286.png","goods_number":100],
			["name":"母亲节礼物-舒适安睡组合","id":1181000,"goods_brief":"安心舒适是最好的礼物","min_retail_price":999,"list_pic_url":"http://yanxuan.nosdn.127.net/1f67b1970ee20fd572b7202da0ff705d.png","goods_number":100]
		]
	]
];
*/

