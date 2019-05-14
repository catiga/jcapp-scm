package com.jeancoder.scm.entry.gdmerge.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.service.MergeGoodsService


MergeGoodsService ss_service = MergeGoodsService.INSTANCE();

def id = JC.request.param('id');
def f = JC.request.param('f');

GdMerge g = ss_service.get(id);
if(g==null) {
	return SimpleAjax.notAvailable('goods_not_found');
}

g.pic_url = f;
ss_service.update_merge(g);

return SimpleAjax.available();






