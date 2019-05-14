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

def gs = JC.request.param('gs').split(',');
def cat_id = JC.request.param('cat_id');
Catalog cat = cat_service.get(cat_id);

if(!gs) {
	return SimpleAjax.notAvailable('empty');
}

def typecode = JC.request.param('tyc');
if(!typecode) {
	typecode = '100';
}

List<BigInteger> ids = [];
for(x in gs) {
	ids.add(BigInteger.valueOf(Long.valueOf(x)));
}

if(typecode.equals('100')) {
	def goods_list = ss_service.find_goods_by_ids(ids);
	ss_service.sale_catalog_for_goods(goods_list, cat);
} else if(typecode.equals('200')) {
	def goods_list = cmp_service.find_goods_by_ids(ids);
	cmp_service.sale_catalog_for_goods(goods_list, cat);
} else if(typecode.equals('300')) {
	def goods_list = mg_service.find_goods_by_ids(ids);
	mg_service.sale_catalog_for_goods(goods_list, cat);
}

return SimpleAjax.available();







