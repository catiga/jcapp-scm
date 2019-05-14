package com.jeancoder.scm.entry.goods.sku.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.GoodsService

GoodsService g_ser = GoodsService.INSTANCE();

def sku_id = JC.request.param('id');

GoodsSku sku = g_ser.get_sku(sku_id);

if(sku==null) {
	//return AjaxUtil.success();
	return SimpleAjax.available();
}

sku.flag = -1;
g_ser.update_sku(sku);
//return AjaxUtil.success();
return SimpleAjax.available();
