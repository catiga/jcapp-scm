package com.jeancoder.scm.entry.stocktake.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockTakeService
import com.jeancoder.scm.ready.service.WareHouseService

GoodsService ss_service = GoodsService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

StockTakeService stock_service = StockTakeService.INSTANCE();

def stock = new BigDecimal(JC.request.param('value'));
if(!stock||stock<0) {
	return -1;
}

def form_id = JC.request.param('form_id');
StockTakeForm form = stock_service.get(form_id);
if(form==null) {
	return -3;
}

def id = JC.request.param('id');
def arr_ids = id.split("_");
if(!arr_ids||arr_ids.length!=4) {
	return -1;
}


GoodsInfo goods = ss_service.get(arr_ids[1]);
GoodsSku  sku = ss_service.get_sku(arr_ids[2]);
WareHouse ware_house = wh_service.get(arr_ids[0]);
if(goods==null||ware_house==null) {
	return -2;
}

List<GoodsStock> ret = ss_service.find_goos_sku_stock(goods, sku, ware_house, null);
def data = 0;

if(ret!=null) {
	for(GoodsStock gs : ret) {
		data+=gs.stock;
	}
}
//开始生成盘点明细数据
stock_service.update_form_item(form, goods, sku, stock, data);

return stock;



