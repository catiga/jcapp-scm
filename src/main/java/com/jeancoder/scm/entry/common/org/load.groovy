package com.jeancoder.scm.entry.common.org

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.dto.CatalogAttr
import com.jeancoder.scm.ready.dto.CatalogDto
import com.jeancoder.scm.ready.dto.SysDept
import com.jeancoder.scm.ready.dto.SysOrgination
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.RemoteUtil

def root_depts = [];
def id = JC.request.param('id');
if(id) {
	def ret_json = JC.internal.call('project', '/org/list', [pid:GlobalHolder.proj.id, id:id]);
	root_depts = JackSonBeanMapper.jsonToList(ret_json, SysDept);
}

List<CatalogDto> catalog = [];
for(SysDept e : root_depts) {
	CatalogDto cd = new CatalogDto();
	cd.text = e.name;
	cd.id = e.id.toString();
	cd.parent = e.parent.toString();
	if(e.parent==0||e.parent==null) {
		cd.parent = "#";
	}
	
	cd.attr = new CatalogAttr();
	cd.attr.id = e.id.toString();
	cd.data = e.name;
	
	catalog.add(cd);
}
return catalog;



