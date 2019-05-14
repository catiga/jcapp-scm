package com.jeancoder.scm.entry.forsale.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService


GoodsService ss_service = GoodsService.INSTANCE();
CmpGoodsService cmp_service = CmpGoodsService.INSTANCE();
MergeGoodsService mg_service = MergeGoodsService.INSTANCE();

CatalogService cat_service = CatalogService.INSTANCE();

def cat_id = JC.request.param('c_id');
Catalog cat = cat_service.get(cat_id);
if(!cat) {
	return SimpleAjax.notAvailable('cat_not_found');
}

def typecode = JC.request.param('tyc');
if(typecode!='100'&&typecode!='200'&&typecode!='300') {
	return SimpleAjax.notAvailable('param_error');
}

def result = [];
if(typecode=='100') {
	result = ss_service.get_cat_goods(cat);
} else if(typecode=='200') {
	result = cmp_service.get_cat_goods(cat);
} else if(typecode=='300') {
	result = mg_service.get_cat_goods(cat);
}

return SimpleAjax.available('', result);







