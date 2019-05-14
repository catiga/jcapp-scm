package com.jeancoder.scm.ready.ajax

class RetObj {

	Object data;
	
	public RetObj(Object e) {
		data = e;
	}
	
	static def build(Object e) {
		//return new RetObj(e);
		return e;
	}
}
