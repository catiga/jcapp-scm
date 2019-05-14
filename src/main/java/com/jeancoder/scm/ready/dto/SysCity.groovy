package com.jeancoder.scm.ready.dto

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

class SysCity {

	Integer id;
	
	String city_no;
	
	String city_name;
	
	Integer m_level;
	
	Integer pid;
	
	String s_name;
	
	Integer flag = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity_no() {
		return city_no;
	}

	public void setCity_no(String city_no) {
		this.city_no = city_no;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public Integer getM_level() {
		return m_level;
	}

	public void setM_level(Integer m_level) {
		this.m_level = m_level;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
