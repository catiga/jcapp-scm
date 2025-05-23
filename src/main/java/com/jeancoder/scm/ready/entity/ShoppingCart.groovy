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
	
	String nick_name;
	
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	String goods_pic;
	
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	BigDecimal weight;
	
	Integer number;
	
	BigDecimal add_price;
	
	BigDecimal retail_price;
	
	Integer is_on_sale = 1;
	
	Date a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
