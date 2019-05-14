package com.jeancoder.scm.entry.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.ConvertToGoods

CatalogService cat_service = CatalogService.INSTANCE();

def pn = Integer.valueOf(JC.request.param('pn')?.trim()?:1);
def ps = JC.request.param('ps');

if(!ps) {
	ps = 200;
}

def cat_id = JC.request.param('cat_id');
Catalog node = null;
if(cat_id) {
	node = cat_service.get(cat_id);
}

def typecode = JC.request.param('tyc');
if(typecode!=null) {
	//只有在不传typecode的情况下，才取所有商品
	if(typecode!='100'&&typecode!='200'&&typecode!='300') {
		typecode = '100';
	}
}
//typecode='100';

//分别设置需要返回的三类
def page_goods = new JcPage<>(); page_goods.pn = pn; page_goods.ps = ps;
def page_pack = new JcPage<>(); page_pack.pn = pn; page_pack.ps = ps;
def page_merge = new JcPage<>(); page_merge.pn = pn; page_merge.ps = ps;

if(typecode==null) {
	page_goods = cat_service.find_sale_goods(page_goods, node, '100');
	page_pack = cat_service.find_sale_goods(page_pack, node, '200');
	page_merge = cat_service.find_sale_goods(page_merge, node, '300');
} else if(typecode=='100') {
	//取商品
	page_goods = cat_service.find_sale_goods(page_goods, node, '100');
} else if(typecode=='200') {
	//取套餐
	page_pack = cat_service.find_sale_goods(page_pack, node, '200');
} else if(typecode=='300') {
	//取合成品
	page_merge = cat_service.find_sale_goods(page_merge, node, '300');
}

//需要统一所有的page数据返回为goods的格式
def ret_data = [];
if(page_goods.result) {
	ret_data.addAll(page_goods.result);
	ret_data.each({
		it[1].gt = '100';
		def g_sku = it[0];
		if(!g_sku.id) {
			//it[0].id = new BigInteger('-' + it[1].id + '' + nextInt(1000, 9999));
			it[0].id = new BigInteger('-' + it[1].id);
			def sku_name = ['商品名称':it[1].goods_name];
			it[0].skus = JackSonBeanMapper.mapToJson(sku_name);
		}
	});
}
if(page_pack.result) {
	ret_data.addAll(ConvertToGoods.convert_pack(page_pack.result));
}
if(page_merge.result) {
	ret_data.addAll(ConvertToGoods.convert_merge(page_merge.result));
}

//page_goods.result.each({it-> it[0].sku_img = 'http://pe1s.static.pdr365.com/' + it[1].goods_picturelink});
//page_pack.result.each({it-> it.pic_url = 'http://pe1s.static.pdr365.com/' + it.pic_url});
//page_merge.result.each({it-> it.pic_url = 'http://pe1s.static.pdr365.com/' + it.pic_url});

ret_data.each({it-> it[0].sku_img = 'http://pe1s.static.pdr365.com/' + it[1].goods_picturelink});
def result = ret_data;


//检查用户选择的门店
def sid = JC.request.param('sid')?.trim();

WareHouse default_wh = WareHouseService.INSTANCE().get_default_warehouse_by_store(sid);
if(default_wh==null) {
	return SimpleAjax.notAvailable('sys_set_error,请绑定门店仓库');
}
//开始处理 typecode==100 即普通商品情况下的库存
if(typecode=='100') {
	//查询库存
	//result.result.each({
	result.each({
		if(it[1].gt=='100') {
			BigDecimal stock = GoodsService.INSTANCE().get_goods_sku_stock_by_id(it[0].goods_id, it[0].id, default_wh);
			it[0].stock = stock;
		}
	});
}


return result;

def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
