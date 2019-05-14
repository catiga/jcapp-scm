package com.jeancoder.scm.entry.supplier

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.service.SupplierService
import com.jeancoder.scm.ready.util.GlobalHolder

/**
 * @mod_name 商品管理
 * @mod_pos  1000
 * @mod_limpro 1
 * @mod_icon fontawesome
 * @mod_grant_by 0
 * @mod_detail 这里是一个模块的描述信息等等
 */
def pn = JC.request.param('pn');
if(!pn) {
	pn = 1;
}
SupplierService ss_service = SupplierService.INSTANCE();

JcPage<Provider> page = new JcPage<Provider>();
page.pn = pn;
page.ps = 20;
page = ss_service.find(page);

Result view = new Result();
view.setView("supplier/list");
view.addObject("page", page);

return view;