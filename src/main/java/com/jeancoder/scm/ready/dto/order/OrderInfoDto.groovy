package com.jeancoder.scm.ready.dto.order

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.scm.ready.entity.OrderInfo

class OrderInfoDto {

	BigInteger id;
	
	String order_no;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Timestamp pay_time;
	
	Integer flag = 0;
	
	BigDecimal total_amount;
	
	BigDecimal pay_amount;
	
	String oss;
	
	/**
	 * 100:收银台订单  只有收银台订单 有备注
	 * 130:外送类订单，即一般电商类订单
	 * 150:店内送货订单，即扫码点餐 备注就是桌号
	 * 190:店内自取订单
	 */
	String dss;
	
	@JCForeign
	BigInteger buyerid;
	
	String buyername;
	
	String buyerphone;
	
	String buyerprovincecode;
	
	String buyerprovincename;
	
	String buyercitycode;
	
	String buyercityname;
	
	String buyerzonecode;
	
	String buyerzonename;
	
	String buyeraddr;
	
	String buyerpoint;
	
	BigInteger pid;
	
	BigInteger store_id;
	
	String store_name;
	
	BigInteger ouid;
	
	String ouname;
	String pay_type;
	List<OrderItemDto> items;
	OrderNermDto nerm;
	
	public OrderInfoDto() {}
	
	public OrderInfoDto(OrderInfo order) {
		this.id = order.id;
		this.order_no = order.order_no;
		this.a_time = order.a_time;
		this.c_time = order.c_time;
		this.pay_time = order.pay_time;
		this.total_amount = order.total_amount;
		this.pay_amount = order.pay_amount;
		this.oss = order.oss;
		this.oss = order.oss;
		this.buyerid = order.buyerid;
		this.buyername = order.buyername;
		this.buyerphone = order.buyerphone;
		this.buyerprovincecode = order.buyerprovincecode;
		this.buyerprovincename = order.buyerprovincename;
		this.buyercitycode = order.buyercitycode;
		this.buyerzonename = order.buyerzonename;
		this.buyeraddr = order.buyeraddr;
		this.buyerpoint = order.buyerpoint;
		this.pid = order.pid;
		this.store_id = order.store_id;
		this.store_name = order.store_name;
		this.ouid = order.ouid;
		this.ouname = order.ouname;
		this.pay_type = order.pay_type;
	}
	
	
}
