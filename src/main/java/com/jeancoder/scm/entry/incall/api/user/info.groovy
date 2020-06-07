package com.jeancoder.scm.entry.incall.api.user

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');

def ap_id = JC.request.param('id');
def pid = GlobalHolder.proj.id;

SimpleAjax ajax = JC.internal.call(SimpleAjax, 'crm', '/api/admin/user_info', [pid:pid, ap_id:ap_id]);

def data = [:];
if(ajax && ajax.available && ajax.data) {
	def a_time = '';
	def c_time = '';
	if(ajax.data['a_time']) {
		a_time = _sdf_.format(new Date(ajax.data['a_time']));
	}
	if(ajax.data['a_time']) {
		c_time = _sdf_.format(new Date(ajax.data['a_time']));
	}
	
	data['id'] = ajax.data['id'];
	data['nickname'] = ajax.data['nickname'];
	data['name'] = ajax.data['realname'];
	data['username'] = ajax.data['realname'];
	data['gender'] = ajax.data['sex'];
	data['birthday'] = ajax.data['birthday'];
	data['register_time'] = a_time;
	data['last_login_time'] = c_time;
	data['avatar'] = ajax.data['head'];
	
	data['name_mobile'] = '';
	data['country'] = ajax.data['countryname'];
	data['province'] = '';
	data['city'] = '';
}

return ProtObj.success(data);

/*
return 
["errno":0,"errmsg":"",
	"data":
	["id":1528,"nickname":"腾腾@JC","name":"","username":"微信用户d82d8d28-5d1c-46e6-b6af-d59b489113ad","password":"oLMM44zSU_tHtH8q4hsKDiqd7NJ0",
		"gender":1,"birthday":0,"register_time":"2020-04-22 17:40:00","last_login_time":"2020-04-22 17:40:00","last_login_ip":"","mobile":"",
		"register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqHRLzz1QhlPOGgKUrAUZGc7mhricKUfb0bKb9wY11x7oWOGm19XTwRXLBykwo15p91ic7W21Qdf74w/132",
		"weixin_openid":"oLMM44zSU_tHtH8q4hsKDiqd7NJ0","name_mobile":0,"country":"China","province":"Beijing","city":"Chaoyang"]
];
*/

