package com.jeancoder.scm.entry.gm.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsModelProp
import com.jeancoder.scm.ready.form.GMFull
import com.jeancoder.scm.ready.service.GoodsService

GoodsService goods_service = GoodsService.INSTANCE();

def id = JC.request.param('id');
if(!id) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}
GoodsModel model = goods_service.get_model(id);
if(model==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}

List<GoodsModelProp> props = goods_service.find_gm_props(model.id);

GMFull full = new GMFull();
full.model = model;
full.props = props;
//return AjaxUtil.successs('', full);

return SimpleAjax.available('', RetObj.build(full));