package com.jeancoder.scm.entry.forsale.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder


GoodsService ss_service = GoodsService.INSTANCE();

CatalogService cat_service = CatalogService.INSTANCE();

def g_id = JC.request.param('g_id')?.trim();
def cat_id = JC.request.param('cat_id')?.trim();
def tyc = JC.request.param('tyc')?.trim();

def cats = cat_service.build_catalog_hierars(cat_id, '');

def sql = 'update GfCatalog set flag=-1 where flag!=? and c_id like "' + cats + '%" and g_id=? and tycode=? and pid=?';
JcTemplate.INSTANCE().batchExecute(sql, -1, g_id, tyc, GlobalHolder.proj.id);
return SimpleAjax.available();


