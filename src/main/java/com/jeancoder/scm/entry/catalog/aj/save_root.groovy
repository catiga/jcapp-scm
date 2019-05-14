package com.jeancoder.scm.entry.catalog.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService

CatalogService service = CatalogService.INSTANCE();

def root_name = JC.request.param('root_name');
def root_code = JC.request.param('root_name');
def root_icon = JC.request.param('root_icon');
def root_info = JC.request.param('root_info');
def select_dsc = JC.request.param('select_dsc')?.trim();

Catalog catalog = new Catalog();
catalog.cat_name_cn = root_name;
catalog.cat_name_en = root_code;
catalog.cat_info = root_info;
catalog.cat_icon = root_icon;
if(select_dsc) {
	catalog.show_dsc = select_dsc;
}

BigInteger id = service.save(catalog);
catalog.id = id;

return SimpleAjax.available('', catalog);
