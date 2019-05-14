package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_goods_sku')
class GoodsSku {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger goods_id;
	
	String sku_no;
	
	BigDecimal sku_price;
	
	String sku_img;
	
	String remark;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Integer stock;
	
	String skus;
	
}
