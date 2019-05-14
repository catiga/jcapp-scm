package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_goods_package_item_vert')
class GoodsPackItemVert {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger gpid;
	
	@JCForeign
	BigInteger item_id;
	
	@JCForeign
	BigInteger fid;
	
	String fno;
	
	String fname;
	
	@JCForeign
	BigInteger fkid;
	
	String fkno;
	
	String fkname;
	
	String unit;
	
	BigDecimal cost_price;
	
	BigDecimal rec_price;
	
	BigDecimal sale_price;
	
	BigDecimal fdd_price;
	
	BigDecimal num;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	/**
	 * 1** : 普通商品
	 * 5** : 合成品
	 */
	String it_code = '100';
}
