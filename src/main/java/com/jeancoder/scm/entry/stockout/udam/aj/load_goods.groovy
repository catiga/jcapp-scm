package com.jeancoder.scm.entry.stockout.udam.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockTakeService
import com.jeancoder.scm.ready.service.WareHouseService


WareHouseService wh_service = WareHouseService.INSTANCE();
StockTakeService stock_take_service = StockTakeService.INSTANCE();

def wh = JC.request.param('wh');
WareHouse ware_house = wh_service.get(wh);
if(ware_house==null) {
	return SimpleAjax.notAvailable('warehouse_not_found');
}

GoodsService ss_service = GoodsService.INSTANCE();


List<GoodsStock> goods_list = ss_service.find_goos_stock(ware_house);

def data = [];
if(goods_list) {
	def ids = [];
	for(x in goods_list) {
		if(ids.contains(x.goods_id)) {
			continue;
		}
		data.add([id:x.goods_id,goods_id:x.goods_no,goods_name:x.goods_name]);
		ids.add(x.goods_id);
	}
}

return SimpleAjax.available('', data);


/*
GoodsService ss_service = GoodsService.INSTANCE();

JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = 1;
page.ps = 100;

page = ss_service.find(page);

//return AjaxUtil.successs('', page);
return SimpleAjax.available('', RetObj.build(page));

*/