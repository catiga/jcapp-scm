package com.jeancoder.scm.entry.common.org

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.CatalogAttr
import com.jeancoder.scm.ready.dto.CatalogDto
import com.jeancoder.scm.ready.dto.SysDept
import com.jeancoder.scm.ready.dto.SysOrgination
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.RemoteUtil

def users = [];
def id = JC.request.param('id');
if(id) {
	def ret_json = JC.internal.call('project', '/org/dept_person', [pid:GlobalHolder.proj.id, id:id]);
	users = JackSonBeanMapper.jsonToList(ret_json, AuthUser);
}

if(users!=null&&!users.empty) {
	return SimpleAjax.available('', users);
}
return SimpleAjax.notAvailable('empty');



