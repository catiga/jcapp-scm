package com.jeancoder.scm.entry.incall.ampi.settings

import com.jeancoder.app.sdk.JC;
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj;
import com.jeancoder.scm.ready.util.GlobalHolder

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

if(request_data==null) {
	return ProtObj.fail(110001, '请检查参数');
}
try {
	request_data = JackSonBeanMapper.jsonToMap(request_data);
} catch(any) {
	return ProtObj.fail(110001, '信息参数错误');
}

def front_user_case = JC.request.attr('front_user_case');

if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}

def realname = request_data['name'];
def mobile = request_data['mobile'];

def ap_id = front_user_case['ap_id'];
def pid = front_user_case['pid'];

SimpleAjax ret = JC.internal.call(SimpleAjax, 'crm', '/h5/p/save_info', [name:realname, mobile:mobile, pid:pid, ap_id:ap_id]);

if(ret.available)
	return ProtObj.success(1);
else
	return ProtObj.fail(110002, '信息保存失败');
