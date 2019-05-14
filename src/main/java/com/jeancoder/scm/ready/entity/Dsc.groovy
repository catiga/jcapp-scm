package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'dsc_setting')
class Dsc {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	String codec;
	
	String name;
	
	String remark;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
