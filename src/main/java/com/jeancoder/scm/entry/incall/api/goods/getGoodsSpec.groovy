package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsModelProp
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

def goods_id = JC.request.param('id');

GoodsInfo goods = GoodsService.INSTANCE().get(goods_id);
if(goods==null) {
	return ProtObj.fail(110001, '商品未找到');
}

//查找商品下的sku
List<GoodsSku> goods_skus = GoodsService.INSTANCE().find_goods_skus(goods);
def specData = [];

if(goods_skus) {
	for(x in goods_skus) {
		def cost_price = x.cost_price==null? goods.cost_price: x.cost_price;
		def sale_price = x.sku_price==null? goods.goods_price: x.sku_price;
		def weight = x.weight==null? 0 : x.weight;
		def stock = x.stock==null? 0 : x.stock;
		def sku_name = x.remark==null? goods.goods_name: x.remark;
		
		if(cost_price==null) {
			cost_price = new BigDecimal(0);
		}
		if(sale_price==null) {
			sale_price = new BigDecimal(0);
		}
		
		def value = [:];
		try {
			value = JackSonBeanMapper.jsonToMap(x.skus);
		} catch(any) {}
		def d = ["id":x.id,"goods_id":goods.id,"goods_specification_ids":"7","goods_sn":x.sku_no,"goods_number":stock,"retail_price":sale_price/100,"cost":cost_price/100,"goods_weight":weight/1000,"has_change":0,"goods_name":sku_name,"is_on_sale":1,"is_delete":0,"value":value];
		specData.add(d);
	}
}

def specModel = [:];
GoodsModel model = null;
if(goods.model_id) {
	model = GoodsService.INSTANCE().get_model(goods.model_id);
}
if(model!=null) {
	List<GoodsModelProp> model_props = GoodsService.INSTANCE().find_gm_props(model.id);
	if(model_props) {
		for(x in model_props) {
			specModel[x.attr_k] = '';
		}
	}
}

return ProtObj.success([specData:specData, specValue:goods.model_id, specModel:specModel]);

/*
return ["errno":0,"errmsg":"",
	"data":[
		"specData":[
			["id":246,"goods_id":1009024,"goods_specification_ids":"7","goods_sn":"12313","goods_number":200,"retail_price":0.5,"cost":1,"goods_weight":2,"has_change":0,"goods_name":"懒人椅","is_on_sale":1,"is_delete":0,"value":"一根葱"],
			["id":251,"goods_id":1009024,"goods_specification_ids":"77","goods_sn":"1234","goods_number":100,"retail_price":5,"cost":1,"goods_weight":2,"has_change":0,"goods_name":"懒人椅","is_on_sale":1,"is_delete":0,"value":"一个面包"]
		],
		"specValue":1
	]
]
*/

