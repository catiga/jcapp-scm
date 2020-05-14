package com.jeancoder.scm.interceptor.incall

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.util.GlobalHolder

@urlmapped(['/incall/api', '/incall/ampi'])

JCLogger logger = JCLoggerFactory.getLogger('');

JCResponse response = ResponseSource.getResponse();
response.setHeader("Access-Control-Allow-Origin", "*");
response.setHeader("Access-Control-Allow-Method", "*");
response.setHeader("Access-Control-Allow-Headers","x-nideshop-token");

JCRequest request = JC.request.get();

def method = request.getMethod();

if(method=='OPTIONS') {
	response.setStatus(204);
	return false;
}

//获取token
def user_token = request.getHeader('X-Nideshop-Token');
if(user_token!=null) {
	//去校验一下用户token
	def pid = GlobalHolder.proj.id;
	
	SimpleAjax simpleAjax = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info', [token:user_token, pid:pid]);
	if(simpleAjax && simpleAjax.available) {
		def user_info = simpleAjax.data;
		request.setAttribute('front_user_case', user_info);
	}
}

return true;
