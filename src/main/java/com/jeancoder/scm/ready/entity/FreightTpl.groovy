package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_freight_tpl_info")
class FreightTpl {

	@JCID
	BigInteger id;
	
	String ft_name;
	
	BigDecimal package_price;
	
	/**
	 * 00: 按重量
	 * 10: 按件
	 */
	String freight_type = '00';
	
	@JCForeign
	BigInteger proj_id;
	
	Date a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Integer is_def_sys = 0;

}
