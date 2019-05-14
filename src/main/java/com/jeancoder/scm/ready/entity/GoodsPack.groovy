package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_goods_package')
class GoodsPack {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	String name;
	
	String info;
	
	String unit;
	
	String sn;
	
	BigDecimal cost_price;
	
	BigDecimal rec_price;
	
	BigDecimal sale_price;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	String pic_url;
}
