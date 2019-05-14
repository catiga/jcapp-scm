package com.jeancoder.scm.ready.dto.order

import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.entity.OrderItemPack

class OrderItemPackDto {

	public OrderItemPackDto() {}
	
	public OrderItemPackDto(OrderItemPack gpi) {
		this.buy_num = gpi.buy_num;
		this.goods_id = gpi.goods_id;
		this.goods_no = gpi.goods_no;
		this.goods_name = gpi.goods_name;
		this.goods_sku_id = gpi.goods_sku_id;
		this.goods_sku_no = gpi.goods_sku_no;
		this.goods_sku_name = gpi.goods_sku_name;
		this.tycode = gpi.tycode;
	}
	
	BigInteger id;
	
	BigInteger order_id;
	
	BigInteger order_item_id;
	
	String pic_url;
	
	BigInteger goods_id;
	
	String goods_no;
	
	String goods_name;
	
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
