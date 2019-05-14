package com.jeancoder.scm.entry.dsc.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Dsc
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id')?.trim();
def name = JC.request.param('name')?.trim();
def code = JC.request.param('code')?.trim();
def remark = JC.request.param('remark')?.trim();

Dsc data = null;
def update = true;
if(id&&id!='0') {
	data = JcTemplate.INSTANCE().get(Dsc, 'select * from Dsc where pid=? and flag!=? and id=?', GlobalHolder.proj.id, -1, id);
} else {
	data = new Dsc();
	data.pid = GlobalHolder.proj.id;
	data.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	data.flag = 0;
	update = false;
}
if(!data) {
	return SimpleAjax.notAvailable('obj_not_found,数据未找到');
}

data.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
data.name = name;
data.codec = code;
data.remark = remark;

if(update) {
	JcTemplate.INSTANCE().update(data);
} else {
	data.id = JcTemplate.INSTANCE().save(data);
}

return SimpleAjax.available('', data);
