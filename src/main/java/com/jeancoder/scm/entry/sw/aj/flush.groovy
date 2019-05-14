package com.jeancoder.scm.entry.sw.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.SWSetting
import com.jeancoder.scm.ready.service.GoodsService

def g_id = JC.request.param('g_id')?.trim();
def sku_id = JC.request.param('sku_id')?.trim();
def stock = new BigDecimal(JC.request.param('value'));

def op = JC.request.param('op')?.trim();
if(op!='0'&&op!='1') {
	return SimpleAjax.notAvailable('param_error,参数错误');
}

GoodsInfo goods = GoodsService.INSTANCE().get(g_id);
GoodsSku sku = null;
if(sku_id) {
	sku = GoodsService.INSTANCE().get_sku(sku_id);
}
if(goods==null) {
	return SimpleAjax.notAvailable('obj_not_found,商品未找到');
}

//根据 g_id 和 sku_id 取得预警数据

def sql = 'select * from SWSetting where flag!=? and goods_id=? and goods_sku_id=?';
SWSetting setting = JcTemplate.INSTANCE().get(SWSetting, sql, -1, g_id, sku_id);
def update = true;
if(setting==null) {
	setting = new SWSetting();
	setting.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	setting.flag = 0;
	update = false;
}
setting.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
setting.goods_id = goods.id;
setting.goods_no = goods.goods_id;
setting.goods_name = goods.goods_name;
if(sku!=null) {
	setting.goods_sku_id = sku.id;
	setting.goods_sku_no = sku.sku_no;
	setting.goods_sku_name = sku.skus;
}
if(op=='0') {
	//下限
	setting.totlowlimit = stock;
} else {
	setting.totuplimit = stock;
}
if(update)
	JcTemplate.INSTANCE().update(setting);
else
	JcTemplate.INSTANCE().save(setting);

return SimpleAjax.available('', stock);



