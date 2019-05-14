package com.jeancoder.scm.entry.stockio

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GoodsOpCode

StockService stock_service = StockService.INSTANCE();
WareHouseService wh_service = WareHouseService.INSTANCE();

//获取仓库
List<WareHouse> all_whs = wh_service.find();
//获取出入库操作类型
def op_list = GoodsOpCode.to_list();
//获取所有操作批次
def all_bts = stock_service.find_all_batch();

def bt = JC.request.param('bt')?:null;
def wh = JC.request.param('wh')?:null;
def op = JC.request.param('op')?:null;
def gn = JC.request.param('gn')?:null;

Result view = new Result();
view.setView("stockio/index");
view.addObject("whs", all_whs);
view.addObject('op_list', op_list);
view.addObject('all_bts', all_bts);

view.addObject('bt', bt);
view.addObject('wh', wh);
view.addObject('op', op);
view.addObject('gn', gn);

def pn = JC.request.param('pn')?:1;
try {
	pn = Integer.valueOf(pn);
}catch(any) {}
if(pn<1) {
	pn = 1;
}
JcPage<GsForm> page = new JcPage<>();
page.pn = pn;
page.ps = 20;

//获取数据
page = stock_service.gs_forms(page, bt, wh, op, gn);
view.addObject('page', page);

return view;
