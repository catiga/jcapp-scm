package com.jeancoder.scm.entry.gm

import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.service.GoodsService

GoodsService goods_service = GoodsService.INSTANCE();

List<GoodsModel> sku_models = goods_service.find_goods_models();

Result view = new Result();
view.setView("gm/index");
view.addObject("sku_models", sku_models);

return view;