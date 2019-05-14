package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_goods_category')
class GoodsCatalog {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger g_id;
	
	@JCForeign
	BigInteger pid;
	
	String c_id;
	
	/*
	 * 100:一般商品
	 * 200:合成商品
	 * 300:套餐商品
	 */
	String tycode;
	
	Integer flag = 0;
	
	Timestamp c_time;
	
}
