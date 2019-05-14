package com.jeancoder.scm.entry.incall.order

/**
 * 统一优惠计算服务，接收参数如下
 *
 * @param o_id :  order_id;		//传入订单id
 * @param unicode : 统一代码		//会员／优惠券码／活动编码
 * @param pref : 计算类型;		// 100:会员；200:优惠券
 */

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.prefer.PreferFactory
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.service.OrderService

JCLogger LOGGER = JCLoggerFactory.getLogger('GOODS_ORDER_NOTIFY');

println '进入接口时间：' + new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());

def unicode = JC.request.param('unicode');
def oid = JC.request.param('o_id');
def pref = JC.request.param('pref');
def op = JC.request.param('op')?.trim();
if(pref!='100'&&pref!='200') {
	return SimpleAjax.notAvailable('unsupport_type,不支持的优惠类型');
}

GoodsService goods_service = GoodsService.INSTANCE();
MergeGoodsService merge_goods_service = MergeGoodsService.INSTANCE();
CmpGoodsService pack_goods_service = CmpGoodsService.INSTANCE();

OrderService order_service = OrderService.INSTANCE();

OrderInfo order = order_service.get(oid);
if(order==null) {
	return SimpleAjax.notAvailable('order_not_found,商品订单未找到');
}
if(!order.oss.startsWith('0')) {
	return SimpleAjax.notAvailable('order_status_invalid,商品订单状态不合法');
}

List<OrderItem> items = order_service.find_order_items(order);

// [goods_id, [cat_ids, cat_ids], price, num]
def param = [];

for(x in items) {
	def tycode = x.tycode;
	if(tycode=='100') {
		GoodsInfo g = goods_service.get(x.goods_id);
		if(!g) {
			//return SimpleAjax.notAvailable('goods_not_found,商品不存在或已下架');
			continue;
		}
		
	} else if(tycode=='200') {
		GoodsPack g = pack_goods_service.get_pack(x.goods_id);
		if(!g) {
			continue;
		}
	} else if(tycode=='300') {
		GdMerge g = merge_goods_service.get(x.goods_id);
		if(!g) {
			continue;
		}
	}
	def cats = goods_service.get_goods_cats(x.goods_id, x.tycode);
	param.add([x.goods_id, x.tycode, cats, x.unit_amount, x.buy_num]);
}

def computer = PreferFactory.generate(pref);
def order_param = [:];
order_param['on'] = order.order_no;
order_param['oc'] = '1000';
order_param['pid'] = order.pid;
order_param['id'] = order.id;
order_param['sid'] = order.store_id;
SimpleAjax ret_obj = computer.compute(order_param, unicode, op, param);


return ret_obj;




