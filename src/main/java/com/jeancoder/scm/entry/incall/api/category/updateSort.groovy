package com.jeancoder.scm.entry.incall.api.category

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService

def cat_id = JC.request.param('id');
def sort = JC.request.param('sort');

try {
	sort = Integer.valueOf(sort);
} catch(any) {
	return ProtObj.fail(220001, '排序参数应该为整数');
}

Catalog cat = CatalogService.INSTANCE().get(cat_id);
if(cat==null) {
	return ProtObj.fail(110001, '分类未找到');
}

cat.seq = sort;
JcTemplate.INSTANCE().update(cat);

return ProtObj.success(1);
