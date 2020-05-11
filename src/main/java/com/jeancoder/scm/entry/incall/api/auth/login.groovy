package com.jeancoder.scm.entry.incall.api.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def username = JC.request.param('username');
def password = JC.request.param('password');

JCLogger logger = JCLoggerFactory.getLogger('');

def ip = JC.request.get().getRemoteAddr();
def pid = GlobalHolder.proj.id;


JCRequest request = JC.request.get();

SimpleAjax auth_result = JC.internal.call(SimpleAjax, 'project', '/auth/check_login_with_pid', [jc_name:username, jc_pass:password, ip:ip, pid:pid]);
if(auth_result.available) {
	def token_data = auth_result.data[0];
	def token_user = auth_result.data[1];
	
	def token_str = token_data['token'];
	def user_info = [id:token_user['id'], username:token_user['username'], name:token_user['name'], mobile:token_user['mobile']];
	
	def ret_data = [token:token_str, userInfo:user_info];
	
	//return '{"errno":0,"errmsg":"","data":{"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTQsImlhdCI6MTU4ODgyNDI5MX0.--kvKAHYQwXtT631mTB_PYaZJdJC_qAx22PDgO3MaKY","userInfo":{"id":14,"username":"hiolabs"}}}';
	
	return ProtObj.success(ret_data);
} else {
	//return '{"errno":401,"errmsg":"用户名或密码不正确!"}';
	
	return ProtObj.fail(401, "用户名或密码不正确!");
}


