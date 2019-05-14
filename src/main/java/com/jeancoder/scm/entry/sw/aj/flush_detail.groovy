package com.jeancoder.scm.entry.sw.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.SWDetail
import com.jeancoder.scm.ready.entity.SWSetting
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService

// total_id:total_id,op:op,wh_id:wh_id
def total_id = JC.request.param('total_id')?.trim();
def wh_id = JC.request.param('wh_id')?.trim();
def stock = new BigDecimal(JC.request.param('value'));

WareHouse ware = WareHouseService.INSTANCE().get(wh_id);
if(ware==null) {
	return SimpleAjax.notAvailable('obj_not_found,仓库未找到');
}

def op = JC.request.param('op')?.trim();
if(op!='0'&&op!='1') {
	return SimpleAjax.notAvailable('param_error,参数错误');
}

SWSetting setting = JcTemplate.INSTANCE().get(SWSetting, 'select * from SWSetting where flag!=? and id=?', -1, total_id);

if(setting==null) {
	return SimpleAjax.notAvailable('obj_not_found,预警设置未找到');
}

def sql = 'select * from SWDetail where flag!=? and total_id=? and wh_id=?';
SWDetail detail_obj = JcTemplate.INSTANCE().get(SWDetail, sql, -1, setting.id, wh_id);
if(detail_obj==null) {
	detail_obj = new SWDetail();
	detail_obj.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	detail_obj.c_time = detail_obj.a_time;
	detail_obj.flag = 0;
	detail_obj.total_id = setting.id;
	detail_obj.wh_id = ware.id;
	if(op=='1') {
		detail_obj.up_limit = stock;
	} else {
		detail_obj.low_limit = stock;
	}
	JcTemplate.INSTANCE().save(detail_obj);
} else {
	if(op=='1') {
		detail_obj.up_limit = stock;
	} else {
		detail_obj.low_limit = stock;
	}
	JcTemplate.INSTANCE().update(detail_obj);
}

return SimpleAjax.available('', stock);



