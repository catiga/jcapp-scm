package com.jeancoder.scm.entry.gdpack.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.service.CmpGoodsService

CmpGoodsService cmp_g_s = CmpGoodsService.INSTANCE();

def item_id = JC.request.param('item_id');
def g_id = JC.request.param('g_id');
def gs_id = JC.request.param('gs_id');

def sql = 'select * from GoodsPackItemVert where flag!=? and item_id=? and fid=?';
GoodsPackItemVert exist_item = JcTemplate.INSTANCE().get(GoodsPackItemVert, sql, -1, item_id, g_id);
if(exist_item) {
	exist_item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	exist_item.flag = -1;
	JcTemplate.INSTANCE().update(exist_item);
}

return SimpleAjax.available();


