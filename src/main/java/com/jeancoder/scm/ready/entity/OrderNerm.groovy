package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = 'order_nerm')
class OrderNerm {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger order_id;
	
	String caller_num;
	
	String table_num;
	
	String room_num;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
