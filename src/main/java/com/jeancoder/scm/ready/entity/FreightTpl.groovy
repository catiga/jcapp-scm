package com.jeancoder.scm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_freight_tpl_info")
class FreightTpl {

	@JCID
	BigInteger id;
	
	String ft_name;
	
	@JCForeign
	BigInteger proj_id;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Boolean is_def_sys = false;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFt_name() {
		return ft_name;
	}

	public void setFt_name(String ft_name) {
		this.ft_name = ft_name;
	}

	public BigInteger getProj_id() {
		return proj_id;
	}

	public void setProj_id(BigInteger proj_id) {
		this.proj_id = proj_id;
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

	public Boolean getIs_def_sys() {
		return is_def_sys;
	}

	public void setIs_def_sys(Boolean is_def_sys) {
		this.is_def_sys = is_def_sys;
	}
	
}
