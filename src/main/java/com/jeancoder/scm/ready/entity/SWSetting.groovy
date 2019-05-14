package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'inventory_warning_setting_total')
class SWSetting {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	@JCForeign
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	BigDecimal totuplimit;
	
	BigDecimal totlowlimit;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	@JCForeign
	BigInteger pid;
}
