package com.jeancoder.scm.internal.inventory

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockTakeForm

def s_id = JC.internal.param('s_id');
def pid = JC.internal.param('pid');

//获取进行中的盘点表
def sql = 'select * from StockTakeForm where flag!=? and storeid=? and icss=? and pid=? order by a_time desc';

def result = JcTemplate.INSTANCE().find(StockTakeForm, sql, -1, s_id, '1000', pid);

return SimpleAjax.available('', result);