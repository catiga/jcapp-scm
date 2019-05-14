package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = 'data_category_info')
class Catalog {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger proj_id;
	
	@JCForeign
	BigInteger parent_id;
	
	String cat_icon;
	
	String cat_name_cn;
	
	String cat_name_en;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
	
	Integer seq = 0;
	
	Integer cat_show = 1;
	
	String click_type = '0000';
	
	String click_content;
	
	String cat_info;
	
	String cat_content;
	
	String hierars;
	//有默认值，有默认处理逻辑，为空或-1则都不显示，为0则全部渠道显示，其他为显示渠道的缓存值
	String show_dsc = "0";
	
	@JCNotColumn
	List<Catalog> sons;
}
