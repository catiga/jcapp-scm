package com.jeancoder.scm.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

class TradeInfo {

	BigInteger id;
	
	BigInteger pid;
	
	String tnum;
	
	String tss;
	
	Integer flag = 0;
	
	BigDecimal total_amount;
	
	BigDecimal pay_amount;
	
	String tname;
	
	String tbody;
	
	BigInteger storeid;
	
	String storename;
	
	BigInteger buyerid;
	
	String buyerphone;
	
	String buyername;
}
