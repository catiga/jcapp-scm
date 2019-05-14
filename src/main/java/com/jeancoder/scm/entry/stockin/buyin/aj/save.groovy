package com.jeancoder.scm.entry.stockin.buyin.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.SupplierService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.DateUtil
import com.jeancoder.scm.ready.util.MoneyUtil
import com.jeancoder.scm.ready.util.RemoteUtil

GoodsService ss_service = GoodsService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();
StockService s_s = StockService.INSTANCE();

def bt = JC.request.param('bt');
def wh = JC.request.param('wh');
def remark = JC.request.param('remark');
def data = JC.request.param('data');
def in_house_date = JC.request.param('in_house_date');

def select_provider = JC.request.param('select_provider');
SupplierService provider_service = SupplierService.INSTANCE();
Provider provider = null;
if(select_provider) {
	provider = provider_service.get(select_provider);
}

StockOpBatch batch = s_s.get(bt);
WareHouse warehouse = wh_service.get(wh);
if(batch==null||warehouse==null) {
	return SimpleAjax.notAvailable('not_found');
}

//检查data
data = data.split(",");
if(!data) {
	return SimpleAjax.notAvailable('empty');
}

def gs = [] ;
for(x in data) {
	def xs = x.split("_");
	GoodsInfo g = ss_service.get(xs[1]);
	GoodsSku sku = ss_service.get_sku(xs[2]);
	try {
		def nums = new BigDecimal(xs[3]);
		def product_date = xs[4];
		def quality_date = xs[5];
		def cost_price = MoneyUtil.get_cent_from_yuan(xs[6]);
		def tax_fee = MoneyUtil.get_cent_from_yuan(xs[7]);
		if(g==null) {
			return SimpleAjax.notAvailable('not_found');
		}
		if(nums<0) {
			return SimpleAjax.notAvailable('param_error');
		}
		
		gs.add([g, sku, nums, product_date, quality_date, cost_price, 0, tax_fee]);
	}catch(any) {
		return SimpleAjax.notAvailable('number_format_error');
	}
}

Timestamp ts = DateUtil.generate(in_house_date);
if(ts==null) {
	ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
}

GsForm original_form = null;
GsForm gs_form = s_s.in_gs_form(batch, warehouse, gs, ts, remark, null, original_form);
if(gs_form!=null&&gs_form.id!=null&&provider!=null) {
	gs_form.providerid = provider.id;
	gs_form.providername = provider.name;
	s_s.update_gs_form(gs_form);
}
return SimpleAjax.available();




