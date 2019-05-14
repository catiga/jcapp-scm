package com.jeancoder.scm.ready.util

class OrderDss {

	static List<OrderDss> _ss_ = [];
	
	static {
		_ss_.add(new OrderDss('100', '到店'));
	}
	
	public OrderDss(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	def code;
	
	def name;
	
}
