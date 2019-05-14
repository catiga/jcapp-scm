package com.jeancoder.scm.entry.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.RemoteUtil

CatalogService cat_service = CatalogService.INSTANCE();

def pn = JC.request.param('pn')?.trim();
def ps = JC.request.param('ps');
try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
if(!ps) {
	ps = 50;
}

def cat_id = JC.request.param('cat_id');
Catalog node = null;
if(cat_id) {
	node = cat_service.get(cat_id);
}

def page = new JcPage<>();
page.pn = pn;
page.ps = ps;

def typecode = JC.request.param('tyc');
if(typecode!='100'&&typecode!='200'&&typecode!='300') {
	typecode = '100';
}

def result = cat_service.find_sale_goods(page, node, typecode);

if(result!=null&&result.result!=null&&!result.result.empty) {
	for(it in result.result) {
		if(typecode=='100') {
			it[0].sku_img = 'http://pe1s.static.pdr365.com/' + it[1].goods_picturelink;
		} else {
			it.pic_url = 'http://pe1s.static.pdr365.com/' + it.pic_url;
		}
	}
}


//检查用户选择的门店
def sid = JC.request.param('sid')?.trim();

WareHouse default_wh = WareHouseService.INSTANCE().get_default_warehouse_by_store(sid);
if(default_wh==null) {
	return SimpleAjax.notAvailable('sys_set_error,请绑定门店仓库');
}
//开始处理 typecode==100 即普通商品情况下的库存
if(typecode=='100') {
	//查询库存
	result.result.each({
		BigDecimal stock = GoodsService.INSTANCE().get_goods_sku_stock_by_id(it[0].goods_id, it[0].id, default_wh);
		it[0].stock = stock;
	});
}

return result;


