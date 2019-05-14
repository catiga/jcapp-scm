package com.jeancoder.scm.entry.goods.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.form.GoodsBasic
import com.jeancoder.scm.ready.service.GoodsService


GoodsService ss_service = GoodsService.INSTANCE();

def id = JC.request.param('id');
if(!id) {
	return SimpleAjax.notAvailable('param_error,请先保存商品基本信息');
}
GoodsInfo goods = ss_service.get(id);
if(goods==null) {
	return SimpleAjax.notAvailable('obj_not_found,请先保存商品基本信息');
}

GoodsBasic base_info = JC.extract.fromRequest(GoodsBasic.class);

goods.goods_price = base_info.price?.multiply(new BigDecimal(100));
goods.goods_original_price = base_info.original_price?.multiply(new BigDecimal(100));
goods.cost_price = base_info.cost_price?.multiply(new BigDecimal(100));

if(goods.model_id==null) {
	//只有在未设置过model的情况允许进行一次更新
	def gmid = base_info.gmid;
	if(gmid) {
		GoodsModel model = ss_service.get_model(gmid);
		if(model!=null) {
			goods.model_id = gmid;
		}
	}
}

ss_service.update(goods);

return SimpleAjax.available('', goods.id);







