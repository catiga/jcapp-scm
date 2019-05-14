package com.jeancoder.scm.entry.gdpack.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.service.CmpGoodsService

CmpGoodsService cmp_g_s = CmpGoodsService.INSTANCE();

def item_id = JC.request.param('item_id');

def sql = 'select * from GoodsPackItem where flag!=? and id=?';
GoodsPackItem exist_item = JcTemplate.INSTANCE().get(GoodsPackItem, sql, -1, item_id);
if(exist_item) {
	exist_item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	exist_item.flag = -1;
	JcTemplate.INSTANCE().update(exist_item);
}

sql = 'update GoodsPackItemVert set flag=-1 where item_id=?';
JcTemplate.INSTANCE().batchExecute(sql, item_id);

return SimpleAjax.available();


