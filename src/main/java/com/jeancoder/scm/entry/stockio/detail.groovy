package com.jeancoder.scm.entry.stockio

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.dto.GoodsStockFullForm
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GoodsOpCode

GoodsService goods_service = GoodsService.INSTANCE();
StockService stock_service = StockService.INSTANCE();
WareHouseService ware_service = WareHouseService.INSTANCE();

def gsm_id = JC.request.param('gsm_id');

GoodsStockFullForm form = stock_service.get_gs_form(gsm_id);

Result view = new Result();
view.setView("stockio/detail");
view.addObject('form', form?.form);
view.addObject('stocks', form?.stocks);

view.addObject('opts', GoodsOpCode.to_list());

return view;




