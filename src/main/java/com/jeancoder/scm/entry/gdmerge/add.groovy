package com.jeancoder.scm.entry.gdmerge

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GdMergeItem
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.UnitUtil

Result view = new Result();
view.setView("gdmerge/add");

def id = JC.request.param('g_id');
if(!id) {
	id = 0;
}

MergeGoodsService mg_ser = MergeGoodsService.INSTANCE();
GdMerge merge = mg_ser.get(id);
if(merge==null) {
	merge = new GdMerge();
}

view.addObject('merge', merge);

List<GdMergeItem> items = mg_ser.get_items(id);
view.addObject('items', items);
view.addObject('units', UnitUtil.toList());
return view;