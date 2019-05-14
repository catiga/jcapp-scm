package com.jeancoder.scm.entry.gdpack.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.service.CmpGoodsService

CmpGoodsService cmp_g_s = CmpGoodsService.INSTANCE();

def pack_id = JC.request.param('pack_id');
List<GoodsPackItem> items = cmp_g_s.get_pack_items(pack_id);

return SimpleAjax.available('', RetObj.build(items));




