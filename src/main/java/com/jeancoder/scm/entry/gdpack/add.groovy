package com.jeancoder.scm.entry.gdpack

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.util.UnitUtil

Result view = new Result();
view.setView("gdpack/add");

def id = JC.request.param('id');
if(!id) {
	id = 0;
}

CmpGoodsService ss_service = CmpGoodsService.INSTANCE();
GoodsPack pack = ss_service.get_pack(id);
if(pack==null) {
	pack = new GoodsPack();
}
view.addObject('pack', pack);

List<GoodsPackItem> items = ss_service.get_pack_items(id);
view.addObject('items', items);
view.addObject('units', UnitUtil.toList());
return view;