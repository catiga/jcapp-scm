package com.jeancoder.scm.entry.stocktake.aj

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.StockTakeFormItem
import com.jeancoder.scm.ready.service.StockTakeService

def id = JC.request.param('id');
StockTakeForm form = StockTakeService.INSTANCE().get(id);
if(form==null) {
	return SimpleAjax.notAvailable('form_not_found,盘点表未找到');
}

if(!form.icss.equals('1000')) {
	//只有进行中的盘点可以关闭
	return SimpleAjax.notAvailable('form_status_error,盘点表状态错误');
}

StockTakeService.INSTANCE().finish_form(form);
//开始进行相应的损溢库存操作
List<StockTakeFormItem> items = StockTakeService.INSTANCE().get_items(form);
if(items==null||items.empty) {
	//直接返回
	return SimpleAjax.available();
}
for(StockTakeFormItem i : items) {
	try {
		BigDecimal diff = i.realnum.subtract(i.recnum);
		if(diff==0) {
			continue;
		}
		//开始构建出入库记录
		Integer in_house = 1; //默认为入库
		String op_type = '3001';
		if(diff<0) {
			in_house = 2;	//设置为出库
			op_type = '4000';
		}
		GoodsStock gs = new GoodsStock(stock:diff,in_house:in_house,op_type:op_type,flag:0);
		gs.goods_id = i.goods_id; gs.goods_no = i.goods_no; gs.goods_name = i.goods_name;
		gs.goods_sku_id = i.goods_sku_id; gs.goods_sku_no = i.goods_sku_no; gs.goods_sku_name = i.goods_sku_name;
		gs.wh_id = form.wh_id; gs.wh_name = form.wh_name;
		gs.store_id = form.storeid; gs.store_name = form.storename;
		gs.spec_unit = i.spec; gs.unit = i.unit;
		gs.a_time = form.a_time; gs.c_time = form.c_time;
		gs.take_id = form.id; gs.take_item_id = i.id;
		gs.pid = form.pid;
		JcTemplate.INSTANCE().save(gs);
	} catch(any) {}
}
return SimpleAjax.available();




