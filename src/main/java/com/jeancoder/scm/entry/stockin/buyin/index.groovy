package com.jeancoder.scm.entry.stockin.buyin

import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.SysOrgination
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.SupplierService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.RemoteUtil

StockService stock_service = StockService.INSTANCE();

WareHouseService wh_service = WareHouseService.INSTANCE();

//获取仓库
List<WareHouse> all_whs = wh_service.find();

//获取领用类型的出库批次
List<StockOpBatch> result = stock_service.find_by_code(1, '1000');

Result view = new Result();
view.setView("stockin/buyin/index");
view.addObject('batches', result);
view.addObject("whs", all_whs);

SysOrgination org = RemoteUtil.connect(SysOrgination.class, 'project', '/incall/org', null);
view.addObject("org", org);

//获取人员
def retobj = RemoteUtil.connectAsArray(AuthUser.class, 'project', '/incall/user', [pn:1]);
view.addObject('user', retobj)

//供应商
SupplierService provider_service = SupplierService.INSTANCE();

JcPage<Provider> page = new JcPage<Provider>();
page.pn = 1;
page.ps = 200;
page = provider_service.find(page);
view.addObject('providers', page);

return view;




