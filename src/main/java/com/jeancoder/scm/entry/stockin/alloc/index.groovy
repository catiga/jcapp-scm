package com.jeancoder.scm.entry.stockin.alloc

import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.SysOrgination
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.RemoteUtil

StockService stock_service = StockService.INSTANCE();

WareHouseService wh_service = WareHouseService.INSTANCE();

//获取仓库
List<WareHouse> all_whs = wh_service.find();

//获取调拨入库类型的批次
List<StockOpBatch> result = stock_service.find_by_code(1, '1001');

Result view = new Result();
view.setView("stockin/alloc/index");
view.addObject('batches', result);
view.addObject("whs", all_whs);

SysOrgination org = RemoteUtil.connect(SysOrgination.class, 'project', '/incall/org', null);
view.addObject("org", org);

//获取人员
def retobj = RemoteUtil.connectAsArray(AuthUser.class, 'project', '/incall/user', [pn:1]);
view.addObject('user', retobj)
return view;







