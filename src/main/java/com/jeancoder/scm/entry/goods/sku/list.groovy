package com.jeancoder.scm.entry.goods.sku

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsModelProp
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.GoodsService

def g_id = JC.request.param('g_id');
GoodsService goods_service = GoodsService.INSTANCE();
GoodsInfo goods = goods_service.get(g_id);

Result view = new Result();

if(goods!=null) {
	view.addObject('goods', goods);
	def model_id = goods.model_id;
	if(model_id) {
		GoodsModel model = goods_service.get_model(model_id);
		List<GoodsModelProp> model_props = goods_service.find_gm_props(model_id);
		view.addObject('model', model);
		view.addObject('model_props', model_props);
	}
}

view.setView("goods/sku/list");
List<GoodsSku> skus = goods_service.find_goods_skus(goods);
view.addObject("skus", skus);

GoodsService ss_service = GoodsService.INSTANCE();

List<GoodsModel> all_models = ss_service.find_goods_models();
view.addObject('all_models', all_models);
view.addObject('am_len', all_models==null?0:all_models.size());

return view;

