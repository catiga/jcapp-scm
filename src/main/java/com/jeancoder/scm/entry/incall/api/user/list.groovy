package com.jeancoder.scm.entry.incall.api.user

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');

def pn = JC.request.param('page');
try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
SimpleAjax ajax = JC.internal.call(SimpleAjax, 'crm', '/api/admin/user_list', [pn:pn, ps:5, pid:GlobalHolder.proj.id]);

def count = 0;
def totalPages = 0;
def pageSize = 0;
def currentPage = 0;

def data = [];
if(ajax && ajax.available) {
	def page = ajax.data;
	count = page.totalCount;
	totalPages = page.totalPages;
	pageSize = page.ps;
	currentPage = page.pn;
	for(x in ajax.data.result) {
		def a_time = '';
		def c_time = '';
		if(x.a_time) {
			a_time = _sdf_.format(new Date(x.a_time));
		}
		if(x.c_time) {
			c_time = _sdf_.format(new Date(x.c_time));
		}
		def u_obj = ["id":x.id, ap_id:x.ap_id, "nickname":x.nickname, "name":x.realname, "username":x.realname,"password":"",
			"gender":x.sex,"birthday":x.birthday,"register_time":a_time,"last_login_time":c_time,"last_login_ip":"",
			"mobile":"","register_ip":"","avatar":x.head,"weixin_openid":"","name_mobile":x.mobile,"country":x.countryname,"province":"","city":""];
		data.add(u_obj);
	}
}

return ProtObj.success(["userData":["count":count,"totalPages":totalPages,"pageSize":pageSize,"currentPage":currentPage, "data":data]]);

