package com.jeancoder.scm.entry.incall.ampi.address

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail('need_login', '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = front_user_case['pid'];

SimpleAjax result = JC.internal.call(SimpleAjax, 'crm', '/h5/address/list', [pid:pid, ap_id:ap_id]);

def result_data = null;
if(result.available) {
	if(result.data) {
		for(x in result.data) {
			def full_region = '';
			if(x['province_code']) {
				full_region = x['province'];
			}
			if(x['city_code']) {
				full_region += x['city'];
			}
			if(x['zone_code']) {
				full_region += x['zone'];
			}
			x['full_region'] = full_region;
		}
	}
	result_data = result.data;
}
return ProtObj.success(result_data);
