package com.jeancoder.scm.entry.stockout.upub.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.WareHouseService


GoodsService ss_service = GoodsService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

def gid = JC.request.param('gid');
GoodsInfo g = ss_service.get(gid);

def wh = JC.request.param('wh');
WareHouse ware_house = wh_service.get(wh);

List<GoodsStock> ret = null;
def data = null;
if(g!=null) {
	if(ware_house!=null) {
		ret = ss_service.find_goos_stock(g, ware_house);
	}
}

if(ret!=null) {
	data = [];
	def keys = [] as BigInteger[];
	
	for(GoodsStock gs : ret) {
		//def aim_obj = keys.find({item->item=gs.goods_sku_id});
		def aim_obj = null; for(x in keys) {if(x.equals(gs.goods_sku_id)) aim_obj = gs}
		if(!aim_obj) {
			def num = gs.stock;
			for(GoodsStock gs_2 : ret) {
				if(!gs.id.equals(gs_2.id)) {
					if(gs.goods_sku_id.equals(gs_2.goods_sku_id)) {
						num += gs_2.stock;
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
			keys+=gs.goods_sku_id;
		}
	}
}


//return AjaxUtil.successs('', data);
return SimpleAjax.available('', RetObj.build(data));




