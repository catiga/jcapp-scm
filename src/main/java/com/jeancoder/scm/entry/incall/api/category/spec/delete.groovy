package com.jeancoder.scm.entry.incall.api.category.spec

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder

def model_id = JC.request.param('id');

GoodsModel model = GoodsService.INSTANCE().get_model(model_id);

if(model==null) {
	return ProtObj.fail(110001, '型号数据未找到');
}

//判断有没有该模型对应的商品信息
String sql = 'select * from GoodsInfo where flag!=? and model_id=? and proj_id=?';

GoodsInfo goods = JcTemplate.INSTANCE().get(GoodsInfo, sql, -1, model.id, GlobalHolder.proj.id);
if(goods!=null) {
	return ProtObj.fail(210001, '型号下存在商品，禁止操作');
}

model.flag = -1;
JcTemplate.INSTANCE().update(model);

return ProtObj.success(1);
