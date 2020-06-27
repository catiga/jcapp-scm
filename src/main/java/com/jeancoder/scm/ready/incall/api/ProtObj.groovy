package com.jeancoder.scm.ready.incall.api

class ProtObj {

	int errno;
	
	String errmsg;
	
	def data;
	
	public static success(def data) {
		return new ProtObj(errno:0, errmsg:'', data:data);
	}
	
	public static fail(int errno, String errmsg) {
		return new ProtObj(errno:errno, errmsg:errmsg);
	}
	
	public static fail(int errno, String errmsg, def data) {
		return new ProtObj(errno:errno, errmsg:errmsg, data:data);
	}
}
