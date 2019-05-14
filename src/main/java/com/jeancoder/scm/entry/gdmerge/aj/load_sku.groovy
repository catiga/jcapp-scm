package com.jeancoder.scm.entry.gdmerge.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.GoodsService


GoodsService ss_service = GoodsService.INSTANCE();

def gid = JC.request.param('gid')?.trim();

GoodsInfo g = ss_service.get(gid);
if(g==null) {
	return SimpleAjax.notAvailable('obj_not_found');
}
List<GoodsSku> skus = ss_service.find_goods_skus(g);
if(skus==null||skus.empty) {
	if(skus==null) skus = [];
	GoodsSku sku = new GoodsSku();
	sku.flag = 0;
	sku.goods_id = g.id;
	sku.id = 0;
	sku.sku_no = g.goods_id;
	sku.skus = '{"商品名称":"' + g.goods_name + '"}';
	skus.add(sku);
}
return SimpleAjax.available('', RetObj.build(skus));




