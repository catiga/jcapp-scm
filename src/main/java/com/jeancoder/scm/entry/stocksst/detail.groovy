package com.jeancoder.scm.entry.stocksst

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GoodsOpCode

GoodsService goods_service = GoodsService.INSTANCE();
StockService stock_service = StockService.INSTANCE();
WareHouseService ware_service = WareHouseService.INSTANCE();

def g_id = JC.request.param('g_id');
def s_id = JC.request.param('s_id');
def wh_id = JC.request.param('wh_id');

GoodsInfo g = goods_service.get(g_id);
GoodsSku sku = goods_service.get_sku(s_id);
WareHouse ware = ware_service.get(wh_id);

def all_whs = ware_service.find();

List<GoodsStock> ret = goods_service.find_goos_sku_stock(g, sku, ware, 'wh_id asc, a_time desc');

Result view = new Result();
view.setView("stocksst/detail");
view.addObject('data', ret);
view.addObject('all_whs', all_whs);
view.addObject('wh_id', wh_id);
view.addObject('opts', GoodsOpCode.to_list());
return view;




