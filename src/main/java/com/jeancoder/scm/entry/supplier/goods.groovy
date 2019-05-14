package com.jeancoder.scm.entry.supplier

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.SupplierService

/**
 * @mod_name 商品管理
 * @mod_pos  1000
 * @mod_limpro 1
 * @mod_icon fontawesome
 * @mod_grant_by 0
 * @mod_detail 这里是一个模块的描述信息等等
 */
def pn = Integer.valueOf(JC.request.param('pn')?.trim()?:1);
def id = JC.request.param('id')?.trim();

SupplierService ss_service = SupplierService.INSTANCE();
Provider provider = ss_service.get(id);

def sql = 'select id from GsForm where flag!=? and providerid=?';
sql = 'select * from GoodsStock where form_id in (' + sql + ')';

JcPage<GoodsStock> page = new JcPage<GoodsStock>();
page.pn = pn;
page.ps = 20;
page = JcTemplate.INSTANCE().find(GoodsStock, page, sql, -1, provider.id);

Result view = new Result();
view.setView("supplier/goods");
view.addObject("page", page);
view.addObject('provider', provider);

return view;