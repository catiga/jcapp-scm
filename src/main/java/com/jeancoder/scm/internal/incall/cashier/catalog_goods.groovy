package com.jeancoder.scm.internal.incall.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.ConvertToGoods

CatalogService cat_service = CatalogService.INSTANCE();

def pn = Integer.valueOf(JC.internal.param('pn')?.toString()?.trim()?:1);
def ps = 200;

def pid = JC.internal.param('pid');
def token = JC.internal.param('token');

def cat_id = JC.internal.param('cat_id');
Catalog node = null;
if(cat_id) {
	node = cat_service.get(pid, cat_id);
}

def project = JC.internal.call('project', '/incall/project_by_id', [pid:pid]);
def img_path = '//';
try {
	project = JackSonBeanMapper.jsonToMap(project);
	img_path = img_path + '/' + project['domain'] + '/img_server';
} catch(any) {
}

def typecode = JC.internal.param('tyc');
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
	page_goods = cat_service.find_sale_goods(page_goods, node, '100', pid);
	page_pack = cat_service.find_sale_goods(page_pack, node, '200', pid);
	page_merge = cat_service.find_sale_goods(page_merge, node, '300', pid);
} else if(typecode=='100') {
	//取商品
	page_goods = cat_service.find_sale_goods(page_goods, node, '100', pid);
} else if(typecode=='200') {
	//取套餐
	page_pack = cat_service.find_sale_goods(page_pack, node, '200', pid);
} else if(typecode=='300') {
	//取合成品
	page_merge = cat_service.find_sale_goods(page_merge, node, '300', pid);
}

//需要统一所有的page数据返回为goods的格式
def ret_data = [];
if(page_goods.result) {
	ret_data.addAll(page_goods.result);
	ret_data.each({
		it[1].gt = '100';
		def g_sku = it[0];
		if(!g_sku.id) {
			it[0].id = new BigInteger('-' + it[1].id + '' + nextInt(1000, 9999));
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

//ret_data.each({it-> it[0].sku_img = 'http://pe1s.static.pdr365.com/' + it[1].goods_picturelink});
ret_data.each({it-> it[0].sku_img = img_path + '/' + it[1].goods_picturelink});
def result = ret_data;


//检查登录用户
def token_key = token;
if(token_key==null) {
	return SimpleAjax.notAvailable('need_login,请先登陆');
}
//SimpleAjax ret_result = RemoteUtil.connect(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key]);
SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key]);
if(ret_result==null) {
	return SimpleAjax.notAvailable('trade_server_error,请检查交易服务');
}
if(!ret_result.available) {
	return ret_result;
}
def counter = ret_result.data[0];
if(counter['sid']==null) {
	return SimpleAjax.notAvailable('counter_set_error,请绑定收银台的门店信息');
}
def sid = counter['sid'];
def sname = counter['sname'];

WareHouseService wh_service = WareHouseService.INSTANCE();
OrderService order_service = OrderService.INSTANCE();
StockService stock_service = StockService.INSTANCE();

WareHouse default_wh = wh_service.get_default_warehouse_by_store(sid, pid);
if(default_wh==null) {
	return SimpleAjax.notAvailable('sys_set_error,请绑定门店仓库');
}
//开始处理 typecode==100 即普通商品情况下的库存
//查询库存
//result.result.each({
result.each({
	if(it[1].gt=='100') {
		BigDecimal stock = GoodsService.INSTANCE().get_goods_sku_stock_by_id(it[0].goods_id, it[0].id, default_wh);
		it[0].stock = stock;
	}
});


return result;


def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}



