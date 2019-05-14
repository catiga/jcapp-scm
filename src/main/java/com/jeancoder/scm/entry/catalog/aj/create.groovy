package com.jeancoder.scm.entry.catalog.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

CatalogService service = CatalogService.INSTANCE();

def parent = JC.request.param('parent');
Catalog node_parent = service.get(parent);
if(node_parent==null) {
	return SimpleAjax.notAvailable('obj_not_found');
}

Catalog c = new Catalog();
c.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
c.cat_name_cn = '未命名';
c.flag = 0;
c.parent_id = node_parent.id;
c.proj_id = GlobalHolder.getProj().id;
c.id = service.save(c);
return SimpleAjax.available('', c);
