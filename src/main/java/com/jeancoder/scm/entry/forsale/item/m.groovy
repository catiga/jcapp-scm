package com.jeancoder.scm.entry.forsale.item

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.UnitUtil

MergeGoodsService cmp_service = MergeGoodsService.INSTANCE();

Result view = new Result();
view.setView("forsale/merge/items");

def g_id = JC.request.param('g_id');
GdMerge pack = cmp_service.get(g_id);
view.addObject('pack', pack);

if(pack!=null) {
	List<GoodsPackItem> pack_items = cmp_service.get_items(pack.id);
	view.addObject('result', pack_items);
}
view.addObject('units', UnitUtil.toList());
return view;