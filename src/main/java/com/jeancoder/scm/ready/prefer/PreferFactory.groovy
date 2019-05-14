package com.jeancoder.scm.ready.prefer

class PreferFactory {

	static def generate(def ct) {
		if(ct=='100') {
			return new McPrefer();
		} else if(ct=='200') {
			return new CouponPrefer();
		}
		return null;
	}
	
	static def main(def argc) {
		Prefer p = generate('100');
		println p;
	}
}
