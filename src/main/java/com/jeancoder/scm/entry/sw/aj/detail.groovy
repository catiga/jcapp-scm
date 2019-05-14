package com.jeancoder.scm.entry.sw.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.SWDetail
import com.jeancoder.scm.ready.entity.SWSetting
import com.jeancoder.scm.ready.service.GoodsService

def total_id = JC.request.param('id');

SWSetting setting = JcTemplate.INSTANCE().get(SWSetting, 'select * from SWSetting where flag!=? and id=?', -1, total_id);

if(setting==null) {
	return SimpleAjax.notAvailable('obj_not_found,预警设置未找到');
}

def sql = 'select * from SWDetail where flag!=? and total_id=?';
List<SWDetail> result = JcTemplate.INSTANCE().find(SWDetail, sql, -1, setting.id);
return SimpleAjax.available('', result);

