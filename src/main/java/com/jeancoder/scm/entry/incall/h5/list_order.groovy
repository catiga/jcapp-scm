package com.jeancoder.scm.entry.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AccountSession
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.FrontOrderAddr
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.dto.TradeInfo
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
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
def apid = JC.request.param('apid');

def ds = JC.request.param('ds');

OrderService order_service= OrderService.INSTANCE();
def orders = order_service.find_user_order(apid, null, null);

if(orders==null||orders.empty) {
	return null;
}

orders.each({
	it.items = order_service.find_order_items(it);
});

return orders;








