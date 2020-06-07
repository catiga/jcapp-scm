package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'order_ship')
class OrderShip {

	@JCID
	BigInteger id;
	
	BigInteger order_id;
	
	BigInteger pid;
	
	String sender_name;
	
	String sender_phone;
	
	String sender_prov_code;
	
	String sender_prov_name;
	
	String sender_city_code;
	
	String sender_city_name;
	
	String sender_zone_code;
	
	String sender_zone_name;
	
	String receiver_name;
	
	String receiver_phone;
	
	String receiver_prov_code;
	
	String receiver_prov_name;
	
	String receiver_city_code;
	
	String receiver_city_name;
	
	String receiver_zone_code;
	
	String receiver_zone_name;
	
	String receiver_address;
	
	String ship_type;	//ship类型
	
	BigInteger exp_id;
	
	String exp_code;
	
	String exp_name;
	
	String exp_odd;
	
	String exp_str;		//快递单上打印内容
	
	String exp_tips;	//快递单提醒
	
	BigDecimal exp_price = 0;	//快递价格
	
	BigDecimal exp_value = 0;	//报价金额
	
	Date a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
