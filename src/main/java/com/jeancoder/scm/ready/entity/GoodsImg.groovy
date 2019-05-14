package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_goodsinfo_img")
class GoodsImg {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger goods_id;
	
	String img_url;
	
	String img_type;
	
	String content_type;
	
	String ct_code;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	String tycode = '100';

	String predomain;
}
