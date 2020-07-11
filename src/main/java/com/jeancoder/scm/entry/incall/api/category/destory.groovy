package com.jeancoder.scm.entry.incall.api.category

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService

def cat_id = JC.request.param('id');

Catalog cat = CatalogService.INSTANCE().get(cat_id);
if(cat!=null) {
	cat.flag = -1;
	JcTemplate.INSTANCE().update(cat);
}

return ProtObj.success(1);

//return ["errno":0,"errmsg":"","data":""];
