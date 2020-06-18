package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_freight_tpl_rule")
class FreightRule {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger ftpl;
	
	// country,province,city,zone
	String ac;
	
	//首重 公斤 | 首件 个数
	BigDecimal ff;
	
	//首费 分
	BigDecimal fm;
	
	//续重单位 公斤 | 续件 个数
	BigDecimal cff;
	
	//续费 分
	BigDecimal cfm;
	
	//按金额包邮
	Integer freemoney_flag = 0;
	BigDecimal freemoney_value;
	
	//按重量或数量包邮
	Integer freewn_flag = 0;
	BigDecimal freewn_value;
	
	//免邮类型
	String fpt;
	
	//免邮金额
	String fpv;
	
	Date a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;

}
