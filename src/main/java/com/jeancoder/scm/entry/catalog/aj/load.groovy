package com.jeancoder.scm.entry.catalog.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.dto.CatalogDto
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService

CatalogService service = CatalogService.INSTANCE();

Catalog parent = null;
def id = JC.request.param('id');
if(id) {
	parent = service.get(id);
}

List<Catalog> result = service.find_by_parent(parent);


List<CatalogDto> catalog = [];
for(Catalog c : result) {
	CatalogDto cd = new CatalogDto(c);
	catalog.add(cd);
}

return catalog;