package com.jeancoder.scm.entry.goods.sku.aj

import java.util.Random

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.GoodsService

GoodsService g_ser = GoodsService.INSTANCE();

def sku_id = JC.request.param('sku_id');

GoodsSku sku = g_ser.get_sku(sku_id);

return SimpleAjax.available('', RetObj.build(sku));

