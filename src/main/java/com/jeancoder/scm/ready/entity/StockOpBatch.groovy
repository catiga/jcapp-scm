package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_goods_op_stock_batch')
class StockOpBatch {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	Integer in_house = 1;
	
	String op_type;
	
	String bname;
	
	String binfo;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	@JCForeign
	BigInteger uid;
	
	String uname;
	
	@JCForeign
	BigInteger store_id;
	
	String store_name;
	
	@JCForeign
	BigInteger aim_store_id;
	
	String aim_store_name;
	
	String sn;
	
	Boolean is_def_sys = false;
	
	@JCForeign
	BigInteger s_wh_id;
	
	String s_wh_name;
	
	@JCForeign
	BigInteger a_wh_id;
	
	String a_wh_name;
}
