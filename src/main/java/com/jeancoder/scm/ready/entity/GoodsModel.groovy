package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_goods_model")
class GoodsModel {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger proj_id;
	
	String m_name_cn;
	
	String m_name_en;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getProj_id() {
		return proj_id;
	}

	public void setProj_id(BigInteger proj_id) {
		this.proj_id = proj_id;
	}

	public String getM_name_cn() {
		return m_name_cn;
	}

	public void setM_name_cn(String m_name_cn) {
		this.m_name_cn = m_name_cn;
	}

	public String getM_name_en() {
		return m_name_en;
	}

	public void setM_name_en(String m_name_en) {
		this.m_name_en = m_name_en;
	}

	public Timestamp getA_time() {
		return a_time;
	}

	public void setA_time(Timestamp a_time) {
		this.a_time = a_time;
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
