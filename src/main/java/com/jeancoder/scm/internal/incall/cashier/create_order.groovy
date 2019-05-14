package com.jeancoder.scm.internal.incall.cashier

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.dto.TradeInfo
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
import com.jeancoder.scm.ready.util.JsonUtil
import com.jeancoder.scm.ready.util.RemoteUtil

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

def token = JC.internal.param('token')?.toString()?.trim();
def pid = JC.internal.param('pid')?.toString()?.trim();
pid = new BigInteger(pid);
def gs = JC.internal.param('gs')?.toString()?.trim();
def verts = JC.internal.param('verts')?.toString()?.trim();

//订单附加信息
def rn = JC.internal.param('rn')?.toString()?.trim();	//房间号
def cn = JC.internal.param('cn')?.toString()?.trim();	//叫号器
def tn = JC.internal.param('tn')?.toString()?.trim();	//桌号

gs = gs?.split(";");
if(gs==null||gs.length==0) {
	return SimpleAjax.notAvailable('param_error,请选择下单商品');
}

SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token,pid:pid]);

if(ret_result==null) {
	return SimpleAjax.notAvailable('trade_server_error,请检查交易服务');
}
if(!ret_result.available) {
	return ret_result;
}

JCLogger logg = LoggerSource.getLogger();
def counter_info = ret_result.data[0];
def user_info = ret_result.data[1];
def log_id = ret_result.data[2]['id'];

AuthUser user = user_info['user'];
if(counter_info['sid']==null) {
	return SimpleAjax.notAvailable('counter_set_error,请绑定收银台的门店信息');
}
def sid = counter_info['sid'];
def sname = counter_info['sname'];

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
		GoodsInfo g = goods_service.get(g_id, pid);
		GoodsSku sku = goods_service.get_sku(sku_id);
		if(g==null) {
			return SimpleAjax.notAvailable('obj_not_found,商品未找到');
		}
		//开始校验库存
		WareHouse default_wh = wh_service.get_default_warehouse_by_store(sid, pid);
		if(default_wh==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置出库仓库');
		}
		if(g.goods_price==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置商品价格');
		}
		GoodsService.INSTANCE().get_goods_sku_stock(g, sku, default_wh);
		goods_list.add([g, sku, num, remark]);
	} else if(typecode=='200') {
		GoodsPack pack = cmp_service.get_pack(g_id, pid);
		if(pack==null) {
			return SimpleAjax.notAvailable('obj_not_found,套餐未找到');
		}
		if(pack.sale_price==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置套餐组合价格');
		}
		def verts_array = [];
		if(verts) {
			//开始查找替代品
			for(vx in verts.split(',')) {
				try {
					verts_array.add(BigInteger.valueOf(Long.valueOf(vx)));
				} catch(any) {}
			}
		}
		goods_list.add([pack, verts_array, num, remark]);
	} else if(typecode=='300') {
		GdMerge merge = merge_service.get(g_id, pid);
		if(merge==null) {
			return SimpleAjax.notAvailable('obj_not_found,合成品未找到');
		}
		if(merge.sale_price==null) {
			return SimpleAjax.notAvailable('sys_set_error,下单失败:未设置合成品价格');
		}
		goods_list.add([merge, null, num, remark]);
	}
}

if(goods_list.empty) {
	return SimpleAjax.notAvailable('empty,请选择下单商品');
}

def order_result = order_service.create_order(goods_list, sid, sname, user, pid);
OrderInfo order = order_result[0];
List<OrderItem> order_items = order_result[1];
order.items = order_items;

if(rn||cn||tn) {
	order_service.update_order_nerm(order, cn, tn, rn);
}

def order_data = JackSonBeanMapper.toJson(order);
order_data = URLEncoder.encode(order_data, 'UTF-8');
order_data = URLEncoder.encode(order_data, 'UTF-8');
def tnum = JC.internal.param('tnum')?.toString()?.trim();
if(tnum==null) {
	tnum = '';
}
//开始去交易中心注册订单
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'1000','od':order_data, tnum:tnum, pid:pid,log_id:log_id]);

trade.data = [trade.data, order_result[0]];

return trade;








