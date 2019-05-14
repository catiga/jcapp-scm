package com.jeancoder.scm.internal.inventory

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.StockTakeFormItem
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.StockTakeService

def icid = JC.internal.param('icid');
def pid = JC.internal.param('pid');
def goods_id = JC.internal.param('goods_id');
def goods_sku_id = JC.internal.param('goods_sku_id');

def real_stock = JC.internal.param('real_stock');
def rec_stock = JC.internal.param('rec_stock');

def remark = JC.internal.param('remark');

try {
	real_stock = new BigDecimal(real_stock + '');
	rec_stock = new BigDecimal(rec_stock + '');
} catch(any) {
	return SimpleAjax.notAvailable('param_error,参数错误');
}

GoodsInfo g = GoodsService.INSTANCE().get(goods_id, pid);
if(!g) {
	return SimpleAjax.notAvailable('obj_not_found,商品未找到');
}
GoodsSku sku = null;
if(goods_sku_id&&goods_sku_id!='0') {
	sku = GoodsService.INSTANCE().get_sku(goods_sku_id);
	if(!sku) {
		return SimpleAjax.notAvailable('obj_not_found,sku未找到');
	}
}

StockTakeForm take_form = StockTakeService.INSTANCE().get(icid, pid);
if(!take_form) {
	return SimpleAjax.notAvailable('obj_not_found,盘点表未找到');
}

//获取进行中的盘点表
def sql = 'select * from StockTakeFormItem where flag!=? and icid=? and pid=? and goods_id=?';
def params = [];
params.add(-1);
params.add(icid);
params.add(pid);
params.add(goods_id);
if(goods_sku_id&&goods_sku_id!='0') {
	sql = sql + ' and goods_sku_id=?';
	params.add(goods_sku_id);
}

StockTakeFormItem result = JcTemplate.INSTANCE().get(StockTakeFormItem, sql, params.toArray());
if(!result) {
	result = new StockTakeFormItem();
	result.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	result.c_time = result.a_time;
	result.flag = 0;
	result.goods_id = g.id;
	result.goods_no = g.goods_id;
	result.goods_name = g.goods_name;
	if(sku) {
		result.goods_sku_id = sku.id;
		result.goods_sku_no = sku.sku_no;
		result.goods_sku_name = sku.skus;
	} else {
		result.goods_sku_id = 0;
		result.goods_sku_no = g.goods_id;
		def map = ['商品名称':g.goods_name];
		result.goods_sku_name = JackSonBeanMapper.mapToJson(map);
	}
	result.icid = take_form.id;
	result.pid = take_form.pid;
	result.realnum = real_stock;
	result.recnum = rec_stock;
	result.spec = g.spec_unit;
	result.unit = g.unit;
	result.remark = remark;
	
	JcTemplate.INSTANCE().save(result);
} else {
	result.realnum = real_stock;
	result.remark = remark;
	
	JcTemplate.INSTANCE().update(result);
}

return SimpleAjax.available();

