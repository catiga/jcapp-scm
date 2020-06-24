package com.jeancoder.scm.entry.incall.ampi.pay

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];
def mobile = front_user_case['mobile'];
def pid = GlobalHolder.proj.id;

def domain = JC.request.get().getServerName();

def orderId = JC.request.param('orderId');
def tnum = JC.request.param('tnum');

def ct = JC.request.param("ct");
def pwd = JC.request.param("pwd");
def o_rem = JC.request.param('o_rem');

def coupon_id = JC.request.param('coupon_id');
def market_id = JC.request.param('market_id');

def unicode = '';
if (ct.equals("101001")) {
	unicode = JC.request.param("unicode");
} else {
	unicode = front_user_case['part_id'];//获取用户信息
}

def aaaa = JC.internal.call("trade", "/incall/ro_code_pay", 
	[tnum:tnum,ct:ct,pwd:pwd,unicode:unicode,o_rem:o_rem,coupon_id:coupon_id,market_id:market_id,domain:domain,ap_id:ap_id, mobile:mobile]);
try {
	aaaa = JackSonBeanMapper.jsonToMap(aaaa);
} catch(any) {
	aaaa = null;
}

if(!aaaa) {
	return ProtObj.fail(210001, '微信支付配置错误');
}
if(aaaa['code']!='0') {
	return ProtObj.fail(210002, aaaa['text']);
}
return ProtObj.success(aaaa['other']);

