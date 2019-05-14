package com.jeancoder.scm.entry.invbatch

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService


def pn = Integer.valueOf(JC.request.param('pn')?.trim()?:1);
def id = JC.request.param('id')?.trim();

StockOpBatch batch = StockService.INSTANCE().get(id);

def sql = 'select id from GsForm where flag!=? and batch_id=?';
sql = 'select * from GoodsStock where form_id in (' + sql + ')';

JcPage<GoodsStock> page = new JcPage<GoodsStock>();
page.pn = pn;
page.ps = 20;
page = JcTemplate.INSTANCE().find(GoodsStock, page, sql, -1, batch.id);

Result view = new Result();
view.setView("invbatch/items");
view.addObject('page', page);
view.addObject('batch', batch);

return view;