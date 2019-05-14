package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'inventory_warning_setting_detail')
class SWDetail {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger total_id;
	
	@JCForeign
	BigInteger wh_id;
	
	BigDecimal up_limit;
	
	BigDecimal low_limit;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
