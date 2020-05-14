package com.jeancoder.scm.entry.incall.ampi.region

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj

def parentId = JC.request.param('parentId');

if(parentId==null || parentId=='0') {
	parentId = null;
}

def params = null;
if(parentId!=null) {
	params = [p:parentId];
}
SimpleAjax province_list = JC.internal.call(SimpleAjax, 'project', '/incall/data/city', params);

def p_result = [];
if(province_list && province_list.available) {
	for(x in province_list.data) {
		def type = x['m_level'] + 1;
		def p_obj = [id:x['id'], value:x['city_no'], name:x['city_name'], parent_id:0, type:type];
		p_result.add(p_obj);
	}
}

return ProtObj.success(p_result);

