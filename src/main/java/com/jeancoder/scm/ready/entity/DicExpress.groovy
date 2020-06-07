package com.jeancoder.scm.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_dic_express')
class DicExpress {

	@JCID
	BigInteger id;
	
	String name;
	
	String code;
	
	Integer sort_order;
	
	String MonthCode;
	
	String CustomerName;
	
	Integer enabled;
	
	Integer flag = 0;
}
