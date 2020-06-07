package com.jeancoder.scm.entry.incall.api.goods

import java.util.List

import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

//获取商品模型
List<GoodsModel> goods_models = GoodsService.INSTANCE().find_goods_models();

def data = [];
if(goods_models) {
	for(x in goods_models) {
		data.add([value:x.id, label:x.m_name_cn]);
	}
}

return ProtObj.success(data);

//return ["errno":0,"errmsg":"","data":[["value":1,"label":"规格"],["value":2,"label":"包装"],["value":3,"label":"重量"]]];
