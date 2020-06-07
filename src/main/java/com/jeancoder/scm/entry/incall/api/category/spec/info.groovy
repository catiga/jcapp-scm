package com.jeancoder.scm.entry.incall.api.category.spec

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

def model_id = JC.request.param('id');

GoodsModel model = GoodsService.INSTANCE().get_model(model_id);

if(model==null) {
	return ProtObj.fail(110001, '型号数据未找到');
}

def data = [id:model.id, name:model.m_name_cn, sort_order:model.id, memo:model.m_name_en];

return ProtObj.success(data);

//return {"errno":0,"errmsg":"","data":{"id":1,"name":"规格","sort_order":1,"memo":"例如：5条装等"}};
