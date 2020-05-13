package com.jeancoder.scm.interceptor.incall

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory

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

return true;
