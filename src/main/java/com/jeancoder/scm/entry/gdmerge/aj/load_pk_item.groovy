package com.jeancoder.scm.entry.gdmerge.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMergeItem
import com.jeancoder.scm.ready.service.MergeGoodsService

MergeGoodsService mg_ser = MergeGoodsService.INSTANCE();

def pack_id = JC.request.param('pack_id');
List<GdMergeItem> items = mg_ser.get_items(pack_id);

return SimpleAjax.available('', RetObj.build(items));



