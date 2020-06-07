package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = 'order_info')
class OrderInfo {

	@JCID
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
	
	String remark;	//整单备注
	
	String admin_memo;	//商家备注
	
	@JCNotColumn
	List<OrderItem> items;
	
}
