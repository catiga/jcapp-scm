package com.jeancoder.scm.entry.gm.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.service.GoodsService

GoodsService goods_service = GoodsService.INSTANCE();


def id = JC.request.param('id');
def props = JC.request.param('props');

if(!props||!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}

GoodsModel model = goods_service.get_model(id);
if(model==null) {
	//return AjaxUtil.fail('model_not_found', null);
	return SimpleAjax.notAvailable('model_not_found');
}

props = props.split(',');
if(!props) {
	//return AjaxUtil.fail('empty', null);
	return SimpleAjax.notAvailable('empty');
}
goods_service.save_gm_props(model, props);

//return AjaxUtil.success();
return SimpleAjax.available();

