package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = 'data_goods_stock')
class GoodsStock {

	@JCID
	BigInteger id;
	
	String stunicode;
	
	BigDecimal stock;
	
	Integer in_house = 1;
	
	String op_type;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	@JCForeign
	BigInteger store_id;
	
	String store_name;
	
	@JCForeign
	BigInteger wh_id;
	
	String wh_name
	
	String remark;
	
	@JCForeign
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	//商品的进货单位
	String unit;
	
	//规格
	Float weight;
	//规格单位
	String spec_unit;
	
	@JCForeign
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	String parainfo;
	
	BigDecimal cost_price;
	
	BigDecimal tax_fee;
	
	String product_date;
	
	//单位天
	Integer quality_date;
	
	@JCForeign
	BigInteger auid;
	
	String auname;
	
	String batchno;
	
	@JCForeign
	BigInteger opbatchid;
	
	String opbatchsn;
	
	@JCForeign
	BigInteger pid;
	
	@JCForeign
	BigInteger form_id;
	
	//对入库没有意义，对出库记录对应扣减的原始入库记录
	@JCForeign
	BigInteger origdata;
	
	//盘点表id
	@JCForeign
	BigInteger take_id;
	
	//盘点表明细id
	@JCForeign
	BigInteger take_item_id;
	
	@JCNotColumn
	String pic_url;
	
	@JCNotColumn
	BigDecimal sale_price;
	
}
