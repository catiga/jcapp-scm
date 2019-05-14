package com.jeancoder.scm.internal.order

import java.util.List

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.dto.order.GoodsSaleDto
import com.jeancoder.scm.ready.dto.order.GoodsSalePage
import com.jeancoder.scm.ready.dto.order.OrderItemDto
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.service.OrderService

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
GoodsSalePage page = new GoodsSalePage();
page.available = false;
def pn = StringUtil.trim((String)CommunicationSource.getParameter("pn"));
def ps = StringUtil.trim((String)CommunicationSource.getParameter("ps"));
def pid = StringUtil.trim((String)CommunicationSource.getParameter("pid"));
def start_data = StringUtil.trim((String)CommunicationSource.getParameter("start_data"));
def end_data = StringUtil.trim((String)CommunicationSource.getParameter("end_data"));
def oss = StringUtil.trim((String)CommunicationSource.getParameter("oss"));
def flag = StringUtil.trim((String)CommunicationSource.getParameter("flag"));

try {
	if (StringUtil.isEmpty(pn)) {
		pn = "1";
	}
	if (StringUtil.isEmpty(ps)) {
		ps = "10";
	}
	pn = Integer.parseInt(pn);
	ps = Integer.parseInt(ps);
	if (pn < 1) {
		pn = 1;
	}
	if (ps < 1) {
		ps = 10;
	}
	if (StringUtil.isEmpty(pid)) {
		pid = null;
	}
	if (StringUtil.isEmpty(oss)) {
		oss = "";
	}
	if (StringUtil.isEmpty(start_data)) {
		start_data = "";
	}
	if (StringUtil.isEmpty(end_data)) {
		end_data = "";
	}
	JcPage<OrderInfo> orderPage  = new JcPage<OrderInfo>();
	orderPage.pn = pn;
	orderPage.ps = ps;
	orderPage = OrderService.__instance__.get_goods_order(orderPage, pid, start_data, end_data, oss,flag);
	page.pn = orderPage.pn;
	page.ps = orderPage.ps;
	page.totalCount = orderPage.totalCount;
	List<GoodsSaleDto> list = new ArrayList<GoodsSaleDto>();
	for (OrderInfo order: orderPage.result) {
		GoodsSaleDto  goodsSaleDto = new GoodsSaleDto();
		goodsSaleDto.order_no = order.order_no;
		if(!order.pay_time.equals("")&&order.pay_time!=null){
			goodsSaleDto.pay_time = order.pay_time.getTime();
		}else{
			goodsSaleDto.pay_time = order.pay_time;
		}
		goodsSaleDto.store_name = order.store_name;
		goodsSaleDto.pay_type = order.pay_type;
		goodsSaleDto.member_no = "";
		goodsSaleDto.original_cost = String.valueOf(order.total_amount);
		goodsSaleDto.pay_amount = String.valueOf(order.pay_amount);
		goodsSaleDto.preferential_amount = String.valueOf(order.total_amount - order.pay_amount);
		goodsSaleDto.ouid = order.ouid;
		List<OrderItemDto> ords = new ArrayList<OrderItemDto>();
		List<OrderItem> tsdtoList = OrderService.__instance__.get_goods_Item_order(order.id);
		for (dlt in tsdtoList) {
			OrderItemDto ord = new OrderItemDto();
			ord.goods_no = dlt.goods_no;
			ord.goods_name = dlt.goods_name;
			ord.buy_num = dlt.buy_num;
			ords.add(ord);
		}
		goodsSaleDto.items = ords;
		list.add(goodsSaleDto);
	}
	page.available = true;
	page.result = list;
} catch (e) {
	Logger.error("商品网售售卖报表列表查询失败",e);
	page.available = false;
	page.msg = "查询失败";
}
return page;