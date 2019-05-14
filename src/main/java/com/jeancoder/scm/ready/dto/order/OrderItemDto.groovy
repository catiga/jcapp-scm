package com.jeancoder.scm.ready.dto.order

import java.sql.Timestamp
import java.util.List

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

class OrderItemDto {

	BigInteger id;
	
	BigInteger order_id;
	
	String pic_url;
	
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	/**
	 * 100:商品
	 * 200:套餐
	 * 300:合成品
	 */
	String tycode;
	
	BigDecimal buy_num;
	
	BigDecimal unit_amount;
	
	BigDecimal pay_amount;
	
	Integer flag = 0;
	
	String fss;
	
	String remark;
	
	List<OrderItemPackDto> verts;
}
