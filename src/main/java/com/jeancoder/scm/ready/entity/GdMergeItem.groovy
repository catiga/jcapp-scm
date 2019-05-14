package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_gd_merge_item')
class GdMergeItem {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger gpid;
	
	@JCForeign
	BigInteger fid;
	
	String fno;
	
	String fname;
	
	@JCForeign
	BigInteger fkid;
	
	String fkno;
	
	String fkname;
	
	BigDecimal num;
	
	String spec_unit;
	
	String unit;
	
	BigDecimal cost_price;
	
	BigDecimal rec_price;
	
	BigDecimal sale_price;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
