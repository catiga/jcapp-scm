package com.jeancoder.scm.entry.stockin.alloc.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.DateUtil
import com.jeancoder.scm.ready.util.MoneyUtil
import com.jeancoder.scm.ready.util.RemoteUtil

GoodsService ss_service = GoodsService.INSTANCE();
StockService s_s = StockService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

def bt = JC.request.param('bt');
def pub_gs = JC.request.param('pub_gs');
def remark = JC.request.param('remark');
def data = JC.request.param('data');
def in_house_date = JC.request.param('in_house_date');

StockOpBatch batch = s_s.get(bt);
if(batch==null) {
	return SimpleAjax.notAvailable('batch_not_found');
}

GsForm form = s_s.get_gs_form_simple(pub_gs);
if(form==null) {
	return SimpleAjax.notAvailable('form_not_found');
}
def wh_id = form.aim_wh_id;
WareHouse warehouse = wh_service.get(wh_id);
//检查data
data = data.split(",");
if(!data) {
	return SimpleAjax.notAvailable('empty');
}

// row_49_1_1_0,row_49_2_1_1

def gs = [] ;
for(x in data) {
	def xs = x.split("_");
	GoodsInfo g = ss_service.get(xs[1]);
	GoodsSku sku = null;
	if(xs[2]) {
		sku = ss_service.get_sku(xs[2]);
	}
	def nums = new BigDecimal(xs[3]);
	def product_date = xs[4];
	def quality_date = xs[5];
	def cost_price = MoneyUtil.get_cent_from_yuan(xs[6]);
	def confirm_status = Integer.valueOf(xs[7]);
	if(g==null) {
		return SimpleAjax.notAvailable('not_found');
	}
	if(nums<0) {
		return SimpleAjax.notAvailable('param_error');
	}
	
	gs.add([g, sku, nums, product_date, quality_date, cost_price, confirm_status]);
}

Timestamp ts = DateUtil.generate(in_house_date);
if(ts==null) {
	ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
}

s_s.in_gs_form(batch, warehouse, gs, ts, remark, null, form);
return SimpleAjax.available();




