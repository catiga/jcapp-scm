package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_g_f_sm')
class GoodsForSale {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger g_id;
	
	String tycode;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	@JCForeign
	BigInteger pid;
	
	Integer flag = 0;
}
