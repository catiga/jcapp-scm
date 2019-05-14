package com.jeancoder.scm.entry.goods.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsInfo
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

def content = JC.request.param('content');

GoodsContent goods_content = ss_service.get_content(id);
if(goods_content==null) {
	goods_content = new GoodsContent();
	goods_content.goods_id = goods.id;
	goods_content.flag = 0;
	goods_content.content = content;
	ss_service.save_content(goods_content);
} else {
	goods_content.content = content;
	ss_service.update_content(goods_content);
}

return SimpleAjax.available('', goods.id);







