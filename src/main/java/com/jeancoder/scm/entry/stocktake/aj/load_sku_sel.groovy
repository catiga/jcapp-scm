package com.jeancoder.scm.entry.stocktake.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockTakeService
import com.jeancoder.scm.ready.service.WareHouseService

/**
 * 统计某个指定仓库的商品数量
 */
GoodsService ss_service = GoodsService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();
StockTakeService stock_take_service = StockTakeService.INSTANCE();

def wh = JC.request.param('wh');
WareHouse ware_house = wh_service.get(wh);
if(ware_house==null) {
	return SimpleAjax.notAvailable('warehouse_not_found');
}

def form_id = JC.request.param('form_id');
StockTakeForm form = stock_take_service.get(form_id);
if(form==null) {
	return SimpleAjax.notAvailable('form_not_found');
}

def gid = JC.request.param('gid');
GoodsInfo g = ss_service.get(gid);

List<GoodsStock> ret = ss_service.find_goos_stock(g, ware_house);
def data = null;

if(ret!=null) {
	data = [];
	def keys = [] as String[];
	
	for(GoodsStock gs : ret) {
		def aim_obj = null; 
		for(x in keys) {
			if(x.equals(gs.goods_id + "_" + gs.goods_sku_id)) 
				aim_obj = gs
		}
		if(!aim_obj) {
			BigDecimal num = gs.stock;
			for(GoodsStock gs_2 : ret) {
				if(!gs.id.equals(gs_2.id)) {
					def gs_key =  gs.goods_id + "_"  + gs.goods_sku_id;
					def gs_2_key = gs_2.goods_id + "_"  + gs_2.goods_sku_id;
					if(gs_key.equals(gs_2_key)) {
						//num += gs_2.stock;
						num = num.add(gs_2.stock);
					}
				}
			}
			GoodsStockData gsd = new GoodsStockData();
			gsd.goods_id = gs.goods_id;
			gsd.goods_name = gs.goods_name;
			gsd.goods_sku_id = gs.goods_sku_id;
			gsd.goods_sku_name = gs.goods_sku_name;
			gsd.stock = num;
			gsd.goods_no = gs.goods_no;
			gsd.goods_sku_no = gs.goods_sku_no;
			gsd.unit = gs.unit;
			gsd.spec_unit = gs.spec_unit;
			data.add(gsd);
			keys+= gs.goods_id + "_"  + gs.goods_sku_id;
		}
	}
}

//if(data) {
//	stock_take_service.init_form_items(form, data);
//}

return SimpleAjax.available('', data);




