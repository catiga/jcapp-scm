package com.jeancoder.scm.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

class CashCounter {

	BigInteger id;
	
	String sn;
	
	String name;
	
	BigInteger pid;
	
	BigInteger sid;
	
	String sname;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	def ocs;
	
	//默认可以用
	Integer inuse = 0;
}
