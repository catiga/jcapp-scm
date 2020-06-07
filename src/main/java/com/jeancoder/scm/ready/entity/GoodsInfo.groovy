package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "data_goods_info")
class GoodsInfo {

	@JCID
	BigInteger id;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
	
	String goods_id;
	
	String goods_name;
	
	String goods_material;
	
	String goods_picturelink;
	
	String goods_picturelink_big;
	
	String goods_state;
	
	String goods_remark;
	
	String goods_picturelink_middle;
	
	@JCForeign
	BigInteger proj_id;
	
	@JCForeign
	BigInteger model_id;
	
	String unit;
	
	String config;
	
	//规格
	Float weight;
	
	String spec_unit;
	
	Integer freepost = 1;
	
	BigInteger ftpl;
	
	String local = '1';
	
	Integer limit_buy_num;
	
	Integer stock;
	
	BigDecimal goods_price;
	
	BigDecimal goods_original_price;
	
	BigDecimal cost_price;
	
	String gt;
	
	String goods_out_no;
	
	String goods_code;
	
	BigInteger other_id;
	
	Timestamp a_time;

	@JCNotColumn
	BigInteger on_sale;
}
