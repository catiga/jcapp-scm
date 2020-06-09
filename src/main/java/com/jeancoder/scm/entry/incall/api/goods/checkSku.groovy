package com.jeancoder.scm.entry.incall.api.goods

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.MoneyUtil

BigDecimal _100_ = new BigDecimal(100);
BigDecimal _1000_ = new BigDecimal(1000);

def id = JC.request.param('id');
def goods_id = JC.request.param('goods_id');
def goods_specification_ids = JC.request.param('goods_specification_ids');

def sku_no = JC.request.param('goods_sn');
def stock = JC.request.param('goods_number');	//显示库存，非真实
def sku_price = JC.request.param('retail_price');	//零售价，单位元
def cost_price = JC.request.param('cost');		//成本价，单位元

def goods_weight = JC.request.param('goods_weight');	//单位千克

def sku_name = JC.request.param('goods_name');	//显示名称

//首先根据goods_id 和 sku_no查找
if(sku_no==null) {
	sku_no = nextInt(100, 999) + '' + nextInt(100, 999);
}

GoodsInfo goods = GoodsService.INSTANCE().get(goods_id);

def sql = 'select * from GoodsSku where flag!=? and goods_id=? and sku_no=?';
GoodsSku goods_sku = JcTemplate.INSTANCE().get(GoodsSku, sql, -1, goods_id, sku_no);
if(id && id!='0') {
	//不是新sku，需要对比
	if(goods_sku!=null && goods_sku.id.toString()!=id) {
		return ProtObj.fail(100, '该SKU已存在，编号重复');
	}
}

if(stock) {
	try {
		stock = Integer.valueOf(stock);
		if(stock<0) {
			return ProtObj.fail(101, '库存参数设置错误');
		}
	} catch(any) {
		return ProtObj.fail(101, '库存参数设置错误');
	}
}

if(sku_price) {
	sku_price = MoneyUtil.get_cent_from_yuan(sku_price);
	if(sku_price==null || sku_price<0) {
		if(sku_price<0) {
			return ProtObj.fail(102, '商品售价设置错误');
		}
	}
} else {
	if(goods!=null && goods.goods_price)
		sku_price = goods.goods_price;
}

if(cost_price) {
	cost_price = MoneyUtil.get_cent_from_yuan(cost_price);
	if(cost_price==null || cost_price<0) {
		if(cost_price<0) {
			return ProtObj.fail(103, '商品成本价设置错误');
		}
	}
} else {
	if(goods!=null && goods.cost_price)
		cost_price = goods.cost_price;
}

if(goods_weight) {
	try {
		goods_weight = new BigDecimal(goods_weight).multiply(_1000_);		//转化为克
		if(goods_weight<0) {
			return ProtObj.fail(105, '重量参数设置错误');
		}
	} catch(any) {
		return ProtObj.fail(105, '重量参数设置错误');
	}
}


//开始更新或保存数据
def update = true;
GoodsSku data = null;
if(id&&id!='0') {
	data = GoodsService.INSTANCE().get_sku(id);
	if(data==null) {
		return ProtObj.fail(110001, 'SKU未找到');
	}
} else {
	update = false;
	data = new GoodsSku();
	data.flag = 0;
	data.goods_id = goods.id;
}

data.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
if(cost_price)
	data.cost_price = cost_price;
if(sku_price)
	data.sku_price = sku_price;
data.remark = sku_name;
data.sku_no = sku_no;
if(stock)
	data.stock = stock;
if(goods_weight)
	data.weight = goods_weight;

if(update) {
	JcTemplate.INSTANCE().update(data);
} else {
	JcTemplate.INSTANCE().save(data);
}

return ProtObj.success(1);


int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
