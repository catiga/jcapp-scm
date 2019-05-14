package com.jeancoder.scm.entry.incall.catalog

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.CatalogService

CatalogService cat_service = CatalogService.INSTANCE();

def pn = JC.request.param('pn');
def ps = JC.request.param('ps');
if(!pn) {
	pn = 1;
}
if(!ps) {
	ps = 50;
}

def cat_id = JC.request.param('cat_id');
Catalog node = null;
if(cat_id) {
	node = cat_service.get(cat_id);
}

def page = new JcPage<>();
page.pn = pn;
page.ps = ps;

def typecode = JC.request.param('tyc');
if(typecode!='100'&&typecode!='200'&&typecode!='300') {
	typecode = '100';
}

def result = cat_service.find_sale_goods_without_sku(page, node, typecode);


return result;
