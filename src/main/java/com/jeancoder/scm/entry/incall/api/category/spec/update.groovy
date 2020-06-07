package com.jeancoder.scm.entry.incall.api.category.spec

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

def model_id = JC.request.param('id');
def name = JC.request.param('name');
def sort_order = JC.request.param('sort_order');

GoodsModel model = GoodsService.INSTANCE().get_model(model_id);

if(model==null) {
	return ProtObj.fail(110001, '型号数据未找到');
}

model.m_name_cn = name;
JcTemplate.INSTANCE().update(model);

return ProtObj.success(1);
