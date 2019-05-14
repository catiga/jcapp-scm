package com.jeancoder.scm.entry.goods.sku.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.service.GoodsService

def g_id = JC.request.param('id')?.trim();
def model_id = JC.request.param('m_id')?.trim();

GoodsService goods_service = GoodsService.INSTANCE();

GoodsInfo goods = goods_service.get(g_id);
GoodsModel model = goods_service.get_model(model_id);

if(goods==null) {
	return SimpleAjax.notAvailable('obj_not_found,商品未找到');
}
if(model==null) {
	return SimpleAjax.notAvailable('obj_not_found,模型未找到');
}

if(goods.model_id!=null) {
	GoodsModel ex_model = goods_service.get_model(goods.model_id);
	if(ex_model!=null) {
		return SimpleAjax.notAvailable('op_repeat,商品模型不可重复设置');
	}
}

goods.model_id = model.id;
goods.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
goods_service.update(goods);

return SimpleAjax.available();
