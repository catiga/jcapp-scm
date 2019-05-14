package com.jeancoder.scm.entry.stockout.upub.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.DateUtil
import com.jeancoder.scm.ready.util.RemoteUtil

GoodsService ss_service = GoodsService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();
StockService s_s = StockService.INSTANCE();

def bt = JC.request.param('bt');
def wh = JC.request.param('wh');
def remark = JC.request.param('remark');
def data = JC.request.param('data');
def in_house_date = JC.request.param('in_house_date');
def uper = JC.request.param('uper');

upder = BigDecimal.valueOf(Long.valueOf(uper));

AuthUser user = RemoteUtil.connect(AuthUser.class, 'project', '/incall/user_by_id', ['id':uper]);
if(!user) {
	return SimpleAjax.notAvailable('user_not_found');
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
		if(g==null) {
			return SimpleAjax.notAvailable('not_found');
		}
		if(nums<0) {
			return SimpleAjax.notAvailable('param_error');
		}
		
		gs.add([g, sku, nums]);
	}catch(any) {
		return SimpleAjax.notAvailable('number_format_error');
	}
}

Timestamp ts = DateUtil.generate(in_house_date);
if(ts==null) {
	ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
}
WareHouse acc_warehouse = null;
def fail_ops = s_s.out_gs_form(batch, warehouse, acc_warehouse, gs, ts, remark, user);

if(!fail_ops) {
	fail_ops = null;
}
return SimpleAjax.available('', RetObj.build(fail_ops));





