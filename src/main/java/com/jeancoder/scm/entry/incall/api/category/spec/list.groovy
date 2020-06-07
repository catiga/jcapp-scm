package com.jeancoder.scm.entry.incall.api.category.spec

import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

//获取商品模型
List<GoodsModel> goods_models = GoodsService.INSTANCE().find_goods_models();

def data = [];
if(goods_models) {
	for(x in goods_models) {
		data.add([id:x.id, name:x.m_name_cn, sort_order:x.id, memo:x.m_name_en]);
	}
}

return ProtObj.success(data);

//return ["errno":0,"errmsg":"","data":[["id":1,"name":"规格","sort_order":1,"memo":"例如：5条装等"],["id":2,"name":"包装","sort_order":2,"memo":""],["id":3,"name":"重量","sort_order":3,"memo":""]]];
