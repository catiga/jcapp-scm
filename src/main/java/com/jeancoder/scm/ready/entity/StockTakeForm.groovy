package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_inventory_check')
class StockTakeForm {
	
	@JCID
	BigInteger id;

	@JCForeign
	BigInteger pid;
	
	String icname;
	
	String icinfo;
	
	String icno;
	
	//默认为全盘
	String ictype = '10';
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	@JCForeign
	BigInteger ouid;
	
	String ouname;
	
	Integer flag = 0;
	
	String icss;
	
	@JCForeign
	BigInteger storeid;
	
	String storename;
	
	@JCForeign
	BigInteger wh_id;
	
	String wh_name;
}
