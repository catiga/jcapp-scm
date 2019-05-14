package com.jeancoder.scm.internal.inventory

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.StockTakeFormItem

def icid = JC.internal.param('icid');
def pid = JC.internal.param('pid');

//获取进行中的盘点表
def sql = 'select * from StockTakeFormItem where flag!=? and icid=? and pid=? order by goods_name asc';

def result = JcTemplate.INSTANCE().find(StockTakeFormItem, sql, -1, icid, pid);

return SimpleAjax.available('', result);