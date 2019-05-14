package com.jeancoder.scm.entry.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AccountSession
import com.jeancoder.scm.ready.dto.FrontOrderAddr
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GlobalHolder

/**
 * '/incall/cashier/create_order?gs=******'
 * 创建商品订单
 * 商品协议串
 * 参数名称：gs, s_id
 * gs参数格式：tyc,商品id,SKU_ID,数量,remark;商品id,SKU_ID,数量,remark
 *           普通商品：tyc=100
 *           套餐商品：tyc=200
 * s_id参数：门店id
 * @author jackielee
 */

//首先校验当前用户

def apid = BigInteger.valueOf(Long.valueOf(JC.request.param('apid')));
AccountSession session = new AccountSession(ap_id:apid);

/**
 * 150:扫码点餐
 * 190:电商下单
 */
def dss = JC.request.param('dss')?.trim();
if(!dss) {
	dss = '190';
}

def gs = JC.request.param('gs')?.trim()?.split(";");
if(gs==null||gs.length==0) {
	return SimpleAjax.notAvailable('param_error,请选择下单商品');
}

def sid = JC.request.param('sid')?.trim();		//上传门店信息
StoreInfo store = null;
if(sid) {
	store = JC.internal.call(StoreInfo, 'project', '/incall/store_by_id', ['id':sid]);	//h5获取门店不限制pid，所以不传pid
}
if(store==null) {
	return SimpleAjax.notAvailable('param_error,请选择下单门店');
}

GoodsService goods_service = GoodsService.INSTANCE();
CmpGoodsService cmp_service = CmpGoodsService.INSTANCE();
MergeGoodsService merge_service = MergeGoodsService.INSTANCE();

OrderService order_service = OrderService.INSTANCE();
StockService stock_service = StockService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

def goods_list = []; //GoodsInfo, GoodsSku, num
for(x in gs) {
	x = x.split(',');
	if(x==null||x.length<4) {
		return SimpleAjax.notAvailable('param_error,请选择下单商品');
	}
	String typecode = x[0];
	def g_id = x[1];		//当tyc=100 时候为商品， 200时候为套餐    300时候为合成品
	def sku_id = x[2];
	BigDecimal num = new BigDecimal(x[3]);
	def remark = null;
	if(x.length>4) {
		remark = x[4];
	}
	
	if(typecode=='100') {
		GoodsInfo g = goods_service.get(g_id);
		GoodsSku sku = goods_service.get_sku(sku_id);
		if(g==null||sku==null) {
			return SimpleAjax.notAvailable('obj_not_found,商品未找到');
		}
		//开始校验库存
		WareHouse default_wh = wh_service.get_default_warehouse_by_store(sid);
		if(default_wh==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置出库仓库');
		}
		if(g.goods_price==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置商品价格');
		}
		GoodsService.INSTANCE().get_goods_sku_stock(g, sku, default_wh);
		goods_list.add([g, sku, num, remark]);
	} else if(typecode=='200') {
		GoodsPack pack = cmp_service.get_pack(g_id);
		if(pack==null) {
			return SimpleAjax.notAvailable('obj_not_found,商品未找到');
		}
		if(pack.sale_price==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置套餐组合商品价格');
		}
		goods_list.add([pack, null, num, remark]);
	} else if(typecode=='300') {
		GdMerge merge = merge_service.get(g_id);
		if(merge==null) {
			return SimpleAjax.notAvailable('obj_not_found,商品未找到');
		}
		if(merge.sale_price==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置商品价格');
		}
		goods_list.add([merge, null, num, remark]);
	}
}

if(goods_list.empty) {
	return SimpleAjax.notAvailable('empty,请选择下单商品');
}

FrontOrderAddr order_addr = new FrontOrderAddr();
order_addr.session = session;
order_addr.provincecode = store.province_no;
order_addr.provincename = store.province;
order_addr.citycode = store.city_no;
order_addr.cityname = store.city;
order_addr.zonecode = store.zone_no;
order_addr.zonename = store.zone;
order_addr.address = store.address;

order_addr.point = JC.request.param('tbn')?.trim();
def order_result = order_service.create_order_front(goods_list, store, dss, order_addr)
OrderInfo order = order_result[0];
List<OrderItem> order_items = order_result[1];
order.items = order_items;

def order_data = JackSonBeanMapper.toJson(order);

def tnum = JC.request.param('tnum')?.trim();
if(tnum==null) {
	tnum = '';
}
//开始去交易中心注册订单
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'1000','od':order_data, tnum:tnum, pid:GlobalHolder.proj.id]);

trade.data = [trade.data, order_result];

return trade;








