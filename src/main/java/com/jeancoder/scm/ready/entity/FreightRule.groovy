package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_freight_tpl_rule")
class FreightRule {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger ftpl;
	
	String ac;
	
	String ff;
	
	String fm;
	
	String cfm;
	
	String fpt;
	
	String fpv;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getFtpl() {
		return ftpl;
	}

	public void setFtpl(BigInteger ftpl) {
		this.ftpl = ftpl;
	}

	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getFf() {
		return ff;
	}

	public void setFf(String ff) {
		this.ff = ff;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	public String getCfm() {
		return cfm;
	}

	public void setCfm(String cfm) {
		this.cfm = cfm;
	}

	public String getFpt() {
		return fpt;
	}

	public void setFpt(String fpt) {
		this.fpt = fpt;
	}

	public String getFpv() {
		return fpv;
	}

	public void setFpv(String fpv) {
		this.fpv = fpv;
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
