package com.jeancoder.scm.ready.util

class StringUtil {
	public static  boolean isEmpty(String str){
		if (str == null) {
			return  true;
		}
		return str.isEmpty();
	}
	
	public static String trim(String str){
		if (str == null) {
			return  null;
		}
		return str.trim();
	}
}
