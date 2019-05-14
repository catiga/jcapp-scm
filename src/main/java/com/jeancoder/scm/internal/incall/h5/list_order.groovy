package com.jeancoder.scm.internal.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.entity.OrderInfo

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
def apid = JC.internal.param('apid');

def ds = JC.internal.param('ds');

OrderService order_service= OrderService.INSTANCE();
def orders = order_service.find_user_order(apid, null, null);

if(orders==null||orders.empty) {
	return new ArrayList<OrderInfo>();
}

orders.each({
	it.items = order_service.find_order_items(it);
});

return orders;








