package com.jeancoder.scm.entry.stocksst

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService

GoodsService goods_service = GoodsService.INSTANCE();
StockService stock_service = StockService.INSTANCE();
WareHouseService ware_service = WareHouseService.INSTANCE();

def wh_id = JC.request.param('wh_id');
def g_num = JC.request.param('g_num');
def sku_num = JC.request.param('sku_num');

WareHouse ware = ware_service.get(wh_id);
List<GoodsInfo> goods_result = goods_service.get_by_num(g_num);
GoodsInfo g = null;
if(goods_result!=null&&!goods_result.isEmpty()) {
	g = goods_result.get(0);
}
List<GoodsSku> sku_result = goods_service.get_sku_by_no(sku_num);
GoodsSku sku = null;
if(sku_result!=null&&!sku_result.isEmpty()) {
	sku = sku_result.get(0);
	if(g==null&&sku!=null) {
		g = goods_service.get(sku.goods_id);
	}
}

def all_whs = ware_service.find();

List<GoodsStock> ret = goods_service.find_goos_sku_stock(g, sku, ware, null);
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
						num = num.add(gs_2.stock).setScale(2);
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
			data.add(gsd);
			keys+= gs.goods_id + "_"  + gs.goods_sku_id;
		}
	}
}
Result view = new Result();
view.setView("stocksst/index");
view.addObject('data', data);
view.addObject('all_whs', all_whs);
view.addObject('g_num', g_num);
view.addObject('sku_num', sku_num);
view.addObject('wh_id', wh_id);
return view;




