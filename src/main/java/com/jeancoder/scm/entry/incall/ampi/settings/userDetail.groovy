package com.jeancoder.scm.entry.incall.ampi.settings

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj;

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def ap_id = front_user_case['ap_id'];

SimpleAjax ret = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info_by_apid', [apid:ap_id]);

def result = [:];

if(ret.available) {
	result['name'] = ret.data['realname'];
	result['mobile'] = ret.data['mobile'];
}

return ProtObj.success(result);
