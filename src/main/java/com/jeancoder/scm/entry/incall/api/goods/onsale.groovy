package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

/*
def goods_1 = ["id": 1009024,"category_id": 1005000,"is_on_sale": true,"name": "支付测试兼打赏","goods_number": 22,
	"sell_volume": 2923,"keywords": "","retail_price": "0.5-5","min_retail_price": 0.5,"cost_price": "1-1","min_cost_price": 1,
	"goods_brief": "此商品可以测试分享到朋友圈","goods_desc": "<p><br></p><p><img src=\"http://yanxuan.nosdn.127.net/34a6a0daa3f7a397a38aad14cb9e90fa.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/76637af0eec246b318cb129b768de637.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18fee22626e61fc1d1a01916914016ba.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91f57a9bb142e1c1e2ff0bbea6f9af96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/252d80fd75eb1254d746d0b57c267650.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4b07697992a2b14de6fd0a5811936d71.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c499439d6081bb4e836955b7514c1b96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bed437fdc091d020a8f805bcc8830bd8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0fc5febdb817abd7a1040bab03f048b7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a0417b3986c9dc082124fcc360390021.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a5c9d24c652d4dee7946ef925105f3f2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b10272c58f95dd6737ce1cd41452a21d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/510c6ef36760238b38ed59cd6e47a21f.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6371348b917c021c55dc393fc59d4d28.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/de4079b128e57c5c0fa8a8177e9bc6e7.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/160966fbc772787f824dc1dbd5afb16d.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bb3c8d3f10f2aca0908871c8e598aa0e.jpg\"></p>",
	"sort_order": 1,"is_index": true,"is_new": 0,"goods_unit": "件","https_pic_url": "https://githttps.hiolabs.com/5b7c1d0a-a12f-48e5-9487-efb1a81a6864","list_pic_url": "http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png",
	"freight_template_id": 17,"freight_type": 0,"is_delete": 0,"has_gallery": 1,"has_done": 1,"category_name": "居家"];

def goods_product_1 = ["id": 250,"goods_id": 1009024,"goods_specification_ids": "7","goods_sn": "12313",
	"goods_number": 200,"retail_price": 0.5,"cost": 1,"goods_weight": 2,"has_change": 0,"goods_name": "懒人椅","is_on_sale": "1","is_delete": 0,"value": "一根葱"];
def goods_product_2 = ["id": 251,"goods_id": 1009024,"goods_specification_ids": "77","goods_sn": "1234","goods_number": 100,"retail_price": 5,
	"cost": 1,"goods_weight": 2,"has_change": 0,"goods_name": "懒人椅","is_on_sale": "1","is_delete": 0,"value": "一个面包"];

def goods_product_list = [];
goods_product_list.add(goods_product_1);
goods_product_list.add(goods_product_2);

goods_1['product'] = goods_product_list;

return ProtObj.success([count:1, totalPages:1, pageSize:10, currentPage:1, data: [goods_1]]);
*/

def pn = JC.request.param('page');
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
JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = pn;
page.ps = ps;
//查找在售商品
def pid = GlobalHolder.proj.id;
def params = [];

def sql = 'select * from GoodsInfo where flag!=? and proj_id=?';
params.add(-1); params.add(pid);
sql += ' and id in (select g_id from GoodsForSale where flag!=?)';
params.add(-1);

page = JcTemplate.INSTANCE().find(GoodsInfo, page, sql, params.toArray());

def page_obj = [count:page.totalCount, totalPages:page.totalPages, pageSize:page.ps, currentPage:page.pn];

def g_data = [];
if(page.result) {
	for(x in page.result) {
		def goods_1 = ["id": x.id,"name": x.goods_name,"goods_brief": x.goods_material,"goods_desc": x.goods_remark,
			"retail_price": x.goods_price,"min_retail_price": x.goods_price,"cost_price": x.cost_price,"min_cost_price": x.cost_price,
			"is_on_sale": true,"goods_number": 22, "sell_volume": 2923,"keywords": "",
			"sort_order": 1,"is_index": true,"is_new": 1,"goods_unit": x.unit,"https_pic_url": x.goods_picturelink,
			"freight_template_id": x.ftpl,"freight_type": x.freepost,"is_delete": 0,"has_gallery": 1,"has_done": 1,
			"category_id": 1005000,"category_name": "居家"];
		
		g_data.add(goods_1);
	}
}
page_obj['data'] =g_data;

return ProtObj.success(page_obj);



