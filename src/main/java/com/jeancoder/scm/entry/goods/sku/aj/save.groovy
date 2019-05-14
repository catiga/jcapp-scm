package com.jeancoder.scm.entry.goods.sku.aj

import java.util.Random

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.MoneyUtil

def g = JC.request.param('g');
def m = JC.request.param('m');
def sku_id = JC.request.param('sku_id');
def data = JC.request.param('data');

def price = JC.request.param('price')?.trim();

def remark = JC.request.param('remark');
def num = JC.request.param('num');

if(!num) {
	num = nextInt(100, 999) + '' + nextInt(1, 9);
}

GoodsService g_ser = GoodsService.INSTANCE();
GoodsInfo goods = g_ser.get(g);
GoodsSku goods_sku = g_ser.get_sku(sku_id);
GoodsModel goods_model = g_ser.get_model(m);

if(goods==null||goods_model==null) {
	return SimpleAjax.notAvailable('obj_not_found');
}

if(goods_sku==null) {
	goods_sku = new GoodsSku();
	goods_sku.goods_id = goods.id;
	goods_sku.flag = 0;
	goods_sku.sku_no = num;
	goods_sku.skus = data;
	goods_sku.remark = remark;
	goods_sku.sku_price = MoneyUtil.get_cent_from_yuan(price);
	g_ser.save_sku(goods_sku);
} else {
	goods_sku.sku_no = num;
	goods_sku.skus = data;
	goods_sku.remark = remark;
	goods_sku.sku_price = MoneyUtil.get_cent_from_yuan(price);
	g_ser.update_sku(goods_sku);
}

return SimpleAjax.available();








int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}


