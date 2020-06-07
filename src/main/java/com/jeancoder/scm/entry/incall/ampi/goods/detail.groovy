package com.jeancoder.scm.entry.incall.ampi.goods

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsModelProp
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GlobalHolder

def g_id = JC.request.param('id');	//商品id
def sku_id = JC.request.param('sku_id');	//sku id
def typecode = JC.request.param('tyc');

if(!typecode) {
	typecode = '100';
}
def pid = GlobalHolder.proj.id;

GoodsService goods_service = GoodsService.INSTANCE();
CmpGoodsService cmp_service = CmpGoodsService.INSTANCE();
MergeGoodsService merge_service = MergeGoodsService.INSTANCE();

WareHouseService wh_service = WareHouseService.INSTANCE();

def goods_info = [:];

if(typecode=='100') {
	GoodsInfo g = goods_service.get(g_id);
	def stock = 9999;	//库存
	def goods_content = null;
	if(g!=null) {
		GoodsContent g_cont = goods_service.get_content(g.id);
		if(g_cont!=null) {
			goods_content = g_cont.content;
		}
		def base_info = ["id":g.id,"category_id":1005000,"is_on_sale":1,"name":g.goods_name,"goods_number":stock,"sell_volume":2923,
		"keywords":"","retail_price":g.goods_price/100,"min_retail_price":g.goods_price/100,"cost_price":g.cost_price/100,"min_cost_price":g.cost_price/100,
		"goods_brief":g.goods_remark,
		"goods_desc":goods_content,
		"sort_order":1,"is_index":1,"is_new":0,"goods_unit":g.unit,
		"https_pic_url":g.goods_picturelink,
		"list_pic_url":g.goods_picturelink,
		"freight_template_id":g.ftpl,"freight_type":g.freepost,"is_delete":0,"has_gallery":0,"has_done":1];
		
		//查找图片库
		List<GoodsImg> goods_img = goods_service.find_goods_imgs(g.id, typecode);
		def goods_gallery = [];
		if(goods_img && !goods_img.empty) {
			goods_info['has_gallery'] = 1;
			for(x in goods_img) {
				def g_i_obj = ["id":x.id,"goods_id":x.goods_id,"img_url":x.img_url,"img_desc":"","sort_order":1,"is_delete":0];
				goods_gallery.add(g_i_obj);
			}
		}
		goods_info['info'] = base_info;
		goods_info['gallery'] = goods_gallery;
		
		def specificationList = [:];
		//查找model
		GoodsModel model = null;
		if(g.model_id) {
			model = goods_service.get_model(g.model_id);
			if(model) {
				specificationList['specification_id'] = model.id;
				specificationList['name'] = model.m_name_cn;
				List<GoodsModelProp> model_props = goods_service.find_gm_props(g.model_id);
				def valueList = [];
				if(model_props) {
					for(x in model_props) {
						valueList.add([id:x.id, specification_id:x.gm_id, value:x.attr_k, is_delete:0, goods_id:g.id, pic_url:'', goods_number:0]);
					}
				}
				specificationList['valueList'] = valueList;
			}
		}
		goods_info['specificationList'] = specificationList;
		
		//查找商品SKU
		List<GoodsSku> g_skus = goods_service.find_goods_skus(g);
		def productList = [];
		if(g_skus) {
			for(x in g_skus) {
				def tmp_obj = [id:x.id, goods_id:g.id, goods_specification_ids:x.sku_no, goods_sn:g.goods_id, goods_number:0, retail_price:x.sku_price,, cost:1, has_change:0, is_on_sale:1, is_delete:0];
				tmp_obj['sku'] = x.skus;
				productList.add(tmp_obj);
			}
		}
		goods_info['productList'] = productList;
	}
} else if(typecode=='200') {
	GoodsPack pack = cmp_service.get_pack(g_id);
	if(pack==null) {
		return SimpleAjax.notAvailable('obj_not_found,商品未找到');
	}
} else if(typecode=='300') {
	GdMerge merge = merge_service.get(g_id);
	if(merge==null) {
		return SimpleAjax.notAvailable('obj_not_found,商品未找到');
	}
}

return ProtObj.success(goods_info)




//return ["errno":0,"errmsg":"","data":["info":["id":1009024,"category_id":1005000,"is_on_sale":1,"name":"支付测试兼打赏","goods_number":303,"sell_volume":2923,"keywords":"","retail_price":"0.5~5","min_retail_price":0.5,"cost_price":"1~2","min_cost_price":1,"goods_brief":"此商品可以测试分享到朋友圈","goods_desc":"<p><br></p><p><img src=\"http://yanxuan.nosdn.127.net/34a6a0daa3f7a397a38aad14cb9e90fa.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/76637af0eec246b318cb129b768de637.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18fee22626e61fc1d1a01916914016ba.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91f57a9bb142e1c1e2ff0bbea6f9af96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/252d80fd75eb1254d746d0b57c267650.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4b07697992a2b14de6fd0a5811936d71.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c499439d6081bb4e836955b7514c1b96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bed437fdc091d020a8f805bcc8830bd8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0fc5febdb817abd7a1040bab03f048b7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a0417b3986c9dc082124fcc360390021.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a5c9d24c652d4dee7946ef925105f3f2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b10272c58f95dd6737ce1cd41452a21d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/510c6ef36760238b38ed59cd6e47a21f.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6371348b917c021c55dc393fc59d4d28.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/de4079b128e57c5c0fa8a8177e9bc6e7.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/160966fbc772787f824dc1dbd5afb16d.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bb3c8d3f10f2aca0908871c8e598aa0e.jpg\"></p>","sort_order":1,"is_index":1,"is_new":0,"goods_unit":"件","https_pic_url":"https://githttps.hiolabs.com/5b7c1d0a-a12f-48e5-9487-efb1a81a6864","list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"freight_type":0,"is_delete":0,"has_gallery":1,"has_done":1],"gallery":[["id":33,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/9460f6b30661548c4a864607bfcdf4ca.jpg","img_desc":"","sort_order":5,"is_delete":0],["id":34,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/acbdb480bcad193fad77ef6c4f52192e.jpg","img_desc":"","sort_order":5,"is_delete":0],["id":35,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/e6feb5f4a0989d212bce068d4907657d.jpg","img_desc":"","sort_order":5,"is_delete":0],["id":36,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/6059ab6e106d97c29d5723c1d6f1a11f.jpg","img_desc":"","sort_order":5,"is_delete":0]],"specificationList":["specification_id":2,"name":"包装","valueList":[["id":7,"goods_id":1009024,"specification_id":2,"value":"一根葱","pic_url":"","is_delete":0,"goods_number":200],["id":77,"goods_id":1009024,"specification_id":2,"value":"一个面包","pic_url":"","is_delete":0,"goods_number":100],["id":78,"goods_id":1009024,"specification_id":2,"value":"包装","pic_url":"","is_delete":0,"goods_number":3]]],"productList":[["id":246,"goods_id":1009024,"goods_specification_ids":"7","goods_sn":"12313","goods_number":200,"retail_price":0.5,"cost":1,"goods_weight":2,"has_change":0,"goods_name":"懒人椅","is_on_sale":1,"is_delete":0],["id":251,"goods_id":1009024,"goods_specification_ids":"77","goods_sn":"1234","goods_number":100,"retail_price":5,"cost":1,"goods_weight":2,"has_change":0,"goods_name":"懒人椅","is_on_sale":1,"is_delete":0],["id":252,"goods_id":1009024,"goods_specification_ids":"78","goods_sn":"66666","goods_number":3,"retail_price":3,"cost":2,"goods_weight":23,"has_change":0,"goods_name":"步骤","is_on_sale":1,"is_delete":0]]]];





/*
return ["errno":0,"errmsg":"",
	"data":
	["info":
		["id":1009024,"category_id":1005000,"is_on_sale":1,"name":"支付测试兼打赏","goods_number":300,"sell_volume":2923,
			"keywords":"","retail_price":"0.5-5","min_retail_price":0.5,"cost_price":"1-1","min_cost_price":1,
			"goods_brief":"此商品可以测试分享到朋友圈",
			"goods_desc":"<p><br></p><p><img src=\"http://yanxuan.nosdn.127.net/34a6a0daa3f7a397a38aad14cb9e90fa.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/76637af0eec246b318cb129b768de637.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18fee22626e61fc1d1a01916914016ba.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91f57a9bb142e1c1e2ff0bbea6f9af96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/252d80fd75eb1254d746d0b57c267650.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4b07697992a2b14de6fd0a5811936d71.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c499439d6081bb4e836955b7514c1b96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bed437fdc091d020a8f805bcc8830bd8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0fc5febdb817abd7a1040bab03f048b7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a0417b3986c9dc082124fcc360390021.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a5c9d24c652d4dee7946ef925105f3f2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b10272c58f95dd6737ce1cd41452a21d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/510c6ef36760238b38ed59cd6e47a21f.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6371348b917c021c55dc393fc59d4d28.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/de4079b128e57c5c0fa8a8177e9bc6e7.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/160966fbc772787f824dc1dbd5afb16d.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bb3c8d3f10f2aca0908871c8e598aa0e.jpg\"></p>",
			"sort_order":1,"is_index":1,"is_new":0,"goods_unit":"件",
			"https_pic_url":"https://githttps.hiolabs.com/5b7c1d0a-a12f-48e5-9487-efb1a81a6864",
			"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png",
			"freight_template_id":17,"freight_type":0,"is_delete":0,"has_gallery":1,"has_done":1],
			"gallery":
			[
				["id":33,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/9460f6b30661548c4a864607bfcdf4ca.jpg","img_desc":"","sort_order":5,"is_delete":0],
				["id":34,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/acbdb480bcad193fad77ef6c4f52192e.jpg","img_desc":"","sort_order":5,"is_delete":0],
				["id":35,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/e6feb5f4a0989d212bce068d4907657d.jpg","img_desc":"","sort_order":5,"is_delete":0],
				["id":36,"goods_id":1009024,"img_url":"http://yanxuan.nosdn.127.net/6059ab6e106d97c29d5723c1d6f1a11f.jpg","img_desc":"","sort_order":5,"is_delete":0]
			],
			"specificationList":
			["specification_id":1,"name":"规格",
				"valueList":[
					["id":7,"goods_id":1009024,"specification_id":1,"value":"一根葱","pic_url":"","is_delete":0,"goods_number":200],
					["id":77,"goods_id":1009024,"specification_id":1,"value":"一个面包","pic_url":"","is_delete":0,"goods_number":100]
				]
			],
			"productList":[
				["id":246,"goods_id":1009024,"goods_specification_ids":"7","goods_sn":"12313","goods_number":200,"retail_price":0.5,"cost":1,"goods_weight":2,"has_change":0,"goods_name":"懒人椅","is_on_sale":1,"is_delete":0],
				["id":251,"goods_id":1009024,"goods_specification_ids":"77","goods_sn":"1234","goods_number":100,"retail_price":5,"cost":1,"goods_weight":2,"has_change":0,"goods_name":"懒人椅","is_on_sale":1,"is_delete":0]
			]
		]
	];
*/