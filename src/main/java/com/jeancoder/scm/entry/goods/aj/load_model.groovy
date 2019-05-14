package com.jeancoder.scm.entry.goods.aj

import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.service.GoodsService


GoodsService ss_service = GoodsService.INSTANCE();

List<GoodsModel> result = ss_service.find_goods_models();

return SimpleAjax.available('', result);

