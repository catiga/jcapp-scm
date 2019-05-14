package com.jeancoder.scm.interceptor.token

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.NativeUtil
import com.jeancoder.scm.ready.util.RemoteUtil

@urlmapped("/")
@urlpassed("/incall")

JCRequest req = JC.request.get();
JCResponse response = ResponseSource.getResponse();
String domain = req.getServerName();
String servletPath = req.getPathInfo().replaceFirst("/", "");
int port = req.getServerPort();
try {
	String _c_u_k_ = null;
	JCCookie[] cookies = req.getCookies()
	if(cookies!=null&&cookies.length>0) {
		for(JCCookie c : cookies) {
			if(c.getName().equals("_c_u_k_adm_")) {
				try {
					_c_u_k_ = URLDecoder.decode(c.getValue(), "utf-8");
				}catch(Exception e){
				}
				break;
			}
		}
	}
	if (_c_u_k_ == null || _c_u_k_.length() == 0) {
		response.sendRedirect("http://" + domain + ":" + port + "/project/index/index");
		return new Result().setRedirectResource("/project/index/index");
	}
	
	AuthToken token = NativeUtil.connect(AuthToken.class, 'project', '/incall/token', ["pid":GlobalHolder.proj.id,"token":_c_u_k_]);
	if (token == null) {
		response.sendRedirect("http://" + domain + ":" + port + "/project/index/index");
		return new Result().setRedirectResource("/project/index/index");
	}
	req.setAttribute('current_token', token);
	GlobalHolder.setToken(token);
}catch(Exception e) {
	response.sendRedirect("http://" + domain + ":" + port + "/project/index/index");
	return new Result().setRedirectResource("/project/index/index");
}

return true;
