package com.jeancoder.scm.entry.incall.api.category

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService

def cat_id = JC.request.param('id');
def status = JC.request.param('status');

Catalog cat = CatalogService.INSTANCE().get(cat_id);
if(cat==null) {
	return ProtObj.fail(110001, '分类未找到');
}

if(status=='true') {
	cat.cat_show = 1;
} else {
	cat.cat_show = 0;
}
JcTemplate.INSTANCE().update(cat);

return ProtObj.success(1);
