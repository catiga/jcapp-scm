package com.jeancoder.scm.interceptor.project

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result

JCRequest jcRequest = RequestSource.getRequest();
JCResponse jcResponse = ResponseSource.getResponse();
Result result = ResultSource.getResult();

if(result!=null) {
	result.addObject("name", "黄杰");
	ResultSource.setResult(result);
}



 
