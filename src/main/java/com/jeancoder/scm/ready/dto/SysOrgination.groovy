package com.jeancoder.scm.ready.dto

class SysOrgination {

	BigInteger id;
	
	String code;
	
	String name;
	
	String info;
	
	String content;
	
	BigInteger pid;
	
	Integer flag = 0;
	
	List<SysDept> root_depts;

}
