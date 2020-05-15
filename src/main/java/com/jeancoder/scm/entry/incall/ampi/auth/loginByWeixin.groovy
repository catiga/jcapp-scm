package com.jeancoder.scm.entry.incall.ampi.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def hp_wxapp_app_id = 'wx7a8d97a1f1a98596';
def hp_wxapp_app_key = '34fcc7920dd4622756a93e2a55c5411d';

JCLogger logger = JCLoggerFactory.getLogger('');

def request_data = null;
try {
	request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');
	request_data = JackSonBeanMapper.jsonToMap(request_data);
}catch(any) {
	return ProtObj.fail(110001, '微信授权错误，请重试');
}
if(request_data==null) {
	return ProtObj.fail(110002, '微信授权失败，请重试');
}

def code = request_data['code'];
if(code==null) {
	return ProtObj.fail(110003, '微信授权参数错误，请重试');
}

def user_info = request_data['userInfo'];

SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'crm', '/wechat/app/check_code', [pid:GlobalHolder.proj.id,code:code, fg_user_info:user_info]);

logger.info(JackSonBeanMapper.toJson(ret_result));
if(!ret_result.available) {
	return ProtObj.fail(220001, '微信授权操作失败，请重试');
}

def userInfo = [:];
userInfo['avatar'] = ret_result.data['head'];
userInfo['nickname'] = ret_result.data['nickname'];
def token = ret_result.data['token'];

return ProtObj.success([token:token, userInfo:userInfo, is_new:0]);


