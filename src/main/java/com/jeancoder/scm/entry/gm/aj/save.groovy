package com.jeancoder.scm.entry.gm.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.form.GM
import com.jeancoder.scm.ready.service.GoodsService

GoodsService goods_service = GoodsService.INSTANCE();

GM gm = JC.extract.fromRequest(GM.class);

def id = gm.id;

GoodsModel entity = null;
if(id) {
	entity = goods_service.get_model(id);
	if(entity==null) {
		//return AjaxUtil.fail('obj_not_found', null);
		return SimpleAjax.notAvailable('obj_not_found');
	}
} else {
	entity = new GoodsModel();
}

entity.m_name_cn = gm.namecn;
entity.m_name_en = gm.nameen;
if(id) {
	id = goods_service.update_model(entity);
} else {
	goods_service.save_model(entity);
}

//return AjaxUtil.successs('', id);
return SimpleAjax.available('', RetObj.build(id));


