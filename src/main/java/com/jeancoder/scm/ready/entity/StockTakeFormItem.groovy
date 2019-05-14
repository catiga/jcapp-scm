package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_inventory_check_item')
class StockTakeFormItem {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	@JCForeign
	BigInteger icid;
	
	@JCForeign
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	@JCForeign
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	String spec;
	
	String unit;
	
	BigDecimal recnum;
	
	BigDecimal realnum;
	
	String remark;
	
	@JCForeign
	BigInteger cuid;
	
	String cuname;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
