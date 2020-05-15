package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_shopping_cart")
class ShoppingCart {

	@JCID
	BigInteger id;
	
	BigInteger pid;
	
	BigInteger ap_id;
	
	BigInteger basic_id;
	
	String user_name;
	
	BigInteger goods_id;
	
	String goods_name;
	
	String goods_pic;
	
	BigInteger goods_sku_id;
	
	String goods_sku_name;
	
	String goods_sku_rich;
	
	Integer number;
	
	BigDecimal add_price;
	
	Date a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
