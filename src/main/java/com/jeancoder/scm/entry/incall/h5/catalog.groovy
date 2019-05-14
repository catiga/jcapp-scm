package com.jeancoder.scm.entry.incall.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService

CatalogService cat_service = CatalogService.INSTANCE();

def pid = JC.request.param('pid');
Catalog parent = null;
if(pid) {
	parent = cat_service.get(pid);
}

List<Catalog> result = cat_service.find_by_parent(parent);

//增加二级返回
for(x in result) {
	x.sons = cat_service.find_by_parent(x);
}

return result;
