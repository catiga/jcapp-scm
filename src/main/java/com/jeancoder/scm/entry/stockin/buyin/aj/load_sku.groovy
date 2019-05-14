package com.jeancoder.scm.entry.stockin.buyin.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.WareHouseService


GoodsService ss_service = GoodsService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

def gid = JC.request.param('gid');
GoodsInfo g = ss_service.get(gid);


List<GoodsSku> ret = null;
if(g!=null) {
	ret = ss_service.find_goods_skus(g);
}
if(ret==null||ret.empty) {
	if(ret==null) ret = [];
	//构造一款临时SKU数据
	GoodsSku sku = new GoodsSku();
	sku.flag = 0;
	sku.goods_id = g.id;
	sku.id = 0;
	sku.sku_no = g.goods_id;
	sku.skus = '{"商品名称":"' + g.goods_name + '"}';
	ret.add(sku);
}
return SimpleAjax.available('', RetObj.build(ret));

