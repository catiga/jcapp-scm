package com.jeancoder.scm.entry.goods.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder


GoodsService ss_service = GoodsService.INSTANCE();

def id = JC.request.param('id');
def f = JC.request.param('f');

GoodsInfo g = ss_service.get(id);
if(g==null) {
	return SimpleAjax.notAvailable('goods_not_found');
}

g.goods_picturelink = f;
g.goods_picturelink_big = f;
ss_service.update(g);

return SimpleAjax.available();






