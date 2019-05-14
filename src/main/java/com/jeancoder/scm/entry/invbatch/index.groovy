package com.jeancoder.scm.entry.invbatch

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GoodsOpCode

StockService stock_service = StockService.INSTANCE();
def pn = JC.request.param('pn');
JcPage<StockOpBatch> page = new JcPage<StockOpBatch>();
if(!pn) {
	pn = 1;
}
page.pn = pn;
page.ps = 20;

page = stock_service.find_batches(page)

List<GoodsOpCode> op_code_list = GoodsOpCode.to_list();
Map<Integer, String> op_code_cat = GoodsOpCode.op_code_cat();

Result view = new Result();
view.setView("invbatch/index");
view.addObject('page', page);
view.addObject("op_code_list", op_code_list);
view.addObject("op_code_cat", op_code_cat);

//获得发生仓库列表
WareHouseService ware_service = WareHouseService.INSTANCE();
List<WareHouse> source_warehouse = ware_service.find();
view.addObject('source_warehouse', source_warehouse);

return view;