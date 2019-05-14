package com.jeancoder.scm.ready.dto

import java.sql.Timestamp

class GoodsStockData {

	BigInteger id;
	
	BigDecimal stock;
	
	Integer in_house = 1;
	
	String op_type;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	BigInteger store_id;
	
	String store_name;
	
	BigInteger wh_id;
	
	String wh_name
	
	String remark;
	
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	String parainfo;
	
	BigDecimal cost_price;
	
	String product_date;
	
	String quality_date;
	
	BigInteger auid;
	
	String auname;
	
	String batchno;
	
	BigInteger opbatchid;
	
	String opbatchsn;
	
	BigInteger pid;
	
	BigInteger form_id;
	
	//商品的进货单位
	String unit;
	
	//规格
	Float weight;
	//规格单位
	String spec_unit;
	
	String pic_url;
	
	String sale_price;
	
	public BigInteger getGoods_sku_id() {
		if(goods_sku_id==null) {
			return 0;
		}
		return goods_sku_id;
	}
	
	public String getGoods_sku_no() {
		if(goods_sku_no==null) {
			return goods_no;
		}
		return goods_sku_no;
	}
	public String getGoods_sku_name() {
		if(goods_sku_name==null) {
			goods_sku_name = '{"商品名称":"' + goods_name + '"}';
		}
		return goods_sku_name;
	}
	
}
