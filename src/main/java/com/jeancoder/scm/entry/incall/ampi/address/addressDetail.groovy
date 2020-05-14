package com.jeancoder.scm.entry.incall.ampi.address

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail('need_login', '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def pid = front_user_case['pid'];

SimpleAjax ajax = JC.internal.call(SimpleAjax, 'crm', '/h5/address/get', [id:id, pid:pid]);
if(!ajax.available) {
	return ProtObj.fail('obj_not_found', '地址信息未找到');
}

def x = ajax.data;

if(x['ap_id']!=ap_id) {
	return ProtObj.fail('obj_be_error', '地址信息未找到');
}

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
x['is_default'] = x['is_def'];

return ProtObj.success(x);
