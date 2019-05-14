package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'order_item_pack')
class OrderItemPack {

	public OrderItemPack() {}
	
	public OrderItemPack(GoodsPackItem gpi) {
		this.buy_num = gpi.num;
		this.goods_id = gpi.fid;
		this.goods_no = gpi.fno;
		this.goods_name = gpi.fname;
		this.goods_sku_id = gpi.fkid;
		this.goods_sku_no = gpi.fkno;
		this.goods_sku_name = gpi.fkname;
		this.tycode = gpi.it_code;
	}
	
	public OrderItemPack(GoodsPackItemVert gpi) {
		this.buy_num = gpi.num;
		this.goods_id = gpi.fid;
		this.goods_no = gpi.fno;
		this.goods_name = gpi.fname;
		this.goods_sku_id = gpi.fkid;
		this.goods_sku_no = gpi.fkno;
		this.goods_sku_name = gpi.fkname;
		this.tycode = gpi.it_code;
	}
	
	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger order_id;
	
	@JCForeign
	BigInteger order_item_id;
	
	String pic_url;
	
	@JCForeign
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
	@JCForeign
	BigInteger goods_sku_id;
	
	String goods_sku_no;
	
	String goods_sku_name;
	
	/**
	 * 100:商品
	 * 300:合成品
	 */
	String tycode;
	
	BigDecimal buy_num;
	
	Integer flag = 0;
	
	String remark;
}
