package com.jeancoder.scm.entry.dsc.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Dsc
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id')?.trim();

Dsc data = JcTemplate.INSTANCE().get(Dsc, 'select * from Dsc where pid=? and flag!=? and id=?', GlobalHolder.proj.id, -1, id);

if(!data) {
	return SimpleAjax.notAvailable('obj_not_found,数据未找到');
}
return SimpleAjax.available('', data);
