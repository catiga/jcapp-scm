package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_gs_form")
class GsForm {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger batch_id;
	
	String batch_name;
	
	@JCForeign
	BigInteger wh_id;
	
	String wh_name;
	
	@JCForeign
	BigInteger aim_wh_id;
	
	String aim_wh_name;
	
	String remark;
	
	Timestamp happen_time;
	
	@JCForeign
	BigInteger happen_uid;
	
	String happen_uname;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	@JCForeign
	BigInteger pid;
	
	@JCForeign
	BigInteger op_uid;
	
	String op_uname;
	
	//目前只针对调拨入库的表单有意义，关联相应的出库单
	@JCForeign
	BigInteger origdata;
	
	@JCForeign
	BigInteger providerid;
	
	String providername;
}
