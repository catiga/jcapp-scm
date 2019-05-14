package com.jeancoder.scm.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_goods_content")
class GoodsContent {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger goods_id;
	
	String content;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(BigInteger goods_id) {
		this.goods_id = goods_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
