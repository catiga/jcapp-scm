package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'sys_warehouse_info')
class WareHouse {

	@JCID
	BigInteger id;
	
	Integer level = 1;
	
	String name;
	
	String info;
	
	String code;
	
	String type;
	
	String remark;
	
	String contact_person;
	
	String contact_phone;
	
	String province;
	
	String province_no;
	
	String city;
	
	String city_no;
	
	String zone;
	
	String zone_no;
	
	String address;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Timestamp a_time;
	
	Integer flag = 0;
	
	//仓库优先级
	Integer sort = 1;
	
	@JCForeign
	BigInteger pid;
	
	@JCForeign
	BigInteger store_id;
	
	String store_name;
}
