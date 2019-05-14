package com.jeancoder.scm.ready.dto

class SysDept {

	BigInteger id;
	
	BigInteger org;
	
	String name;
	
	String code;
	
	String info;
	
	BigInteger parent;
	
	Integer flag = 0;
	
	Integer level;
	
	Boolean hasson;
	
	/**
	 * 1:部门
	 * 2:岗位
	 */
	Integer type = 1;

	BigInteger pid;
	
}
