package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_goods_model_kv')
class GoodsModelProp {
	
	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger gm_id;
	
	String attr_k;
	
	Timestamp c_time;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getGm_id() {
		return gm_id;
	}

	public void setGm_id(BigInteger gm_id) {
		this.gm_id = gm_id;
	}

	public String getAttr_k() {
		return attr_k;
	}

	public void setAttr_k(String attr_k) {
		this.attr_k = attr_k;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
