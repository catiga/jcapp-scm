package com.jeancoder.scm.entry.sw

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.dto.GoodsStockDataWithSetting
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.SWSetting
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService

GoodsService goods_service = GoodsService.INSTANCE();
StockService stock_service = StockService.INSTANCE();
WareHouseService ware_service = WareHouseService.INSTANCE();

//查询条件
def wh_id = JC.request.param('wh_id');
def g_num = JC.request.param('g_num');
def sku_num = JC.request.param('sku_num');
if(sku_num!=null) {
	sku_num = URLDecoder.decode(sku_num, "UTF-8");
}

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

def pn = Integer.valueOf(JC.request.param('pn')?.trim()?:1);
JcPage<SWSetting> page = new JcPage<SWSetting>();
page.pn = pn;
page.ps = 100;
//查找预警设置信息
def sql = 'select * from SWSetting where flag!=? order by a_time desc ';
page = JcTemplate.INSTANCE().find(SWSetting, page, sql, -1);

def data = null;
if(ware!=null||g!=null||sku!=null) {
	List<GoodsStock> ret = goods_service.find_goos_sku_stock(g, sku, ware, null);
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
				GoodsStockDataWithSetting gsd = new GoodsStockDataWithSetting();
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
}

Result view = new Result();
view.setView("sw/index");
view.addObject('page', page);
view.addObject('all_whs', all_whs);
view.addObject('g_num', g_num);
view.addObject('sku_num', sku_num);
view.addObject('wh_id', wh_id);



view.addObject('data', data);
if(data!=null&&!data.empty) {
	for(GoodsStockDataWithSetting gsd in data) {
		def goods_id = gsd.goods_id;
		def goods_sku_id = gsd.goods_sku_id;
		//开始查找合计库存
		sql = 'select * from SWSetting where flag!=? and goods_id=?';
		def params = []; params.add(-1); params.add(goods_id);
		if(goods_sku_id!=null) {
			sql += ' and goods_sku_id=?'; params.add(goods_sku_id);
		}
		SWSetting setting = JcTemplate.INSTANCE().get(SWSetting, sql, params.toArray());
		gsd.setting = setting;
	}
	
	//重置
	view.setView("sw/query");
}

return view;

/*
List<GoodsStock> ret = goods_service.find_goos_sku_stock(g, sku, ware, null);
//println "entry.stocksst________" + JackSonBeanMapper.toJson(ret);
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
			data.add(gsd);
			keys+= gs.goods_id + "_"  + gs.goods_sku_id;
		}
	}
}

Result view = new Result();
view.setView("sw/index");
view.addObject('data', data);
view.addObject('all_whs', all_whs);
view.addObject('g_num', g_num);
view.addObject('sku_num', sku_num);
view.addObject('wh_id', wh_id);
return view;
*/



