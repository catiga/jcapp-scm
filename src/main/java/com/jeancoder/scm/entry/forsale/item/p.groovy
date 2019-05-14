package com.jeancoder.scm.entry.forsale.item

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.util.UnitUtil

CmpGoodsService cmp_service = CmpGoodsService.INSTANCE();

Result view = new Result();
view.setView("forsale/pack/items");

def g_id = JC.request.param('g_id');
GoodsPack pack = cmp_service.get_pack(g_id);
view.addObject('pack', pack);

if(pack!=null) {
	List<GoodsPackItem> pack_items = cmp_service.get_pack_items(pack.id);
	view.addObject('result', pack_items);
}
view.addObject('units', UnitUtil.toList());
return view;