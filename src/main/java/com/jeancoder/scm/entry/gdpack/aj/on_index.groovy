package com.jeancoder.scm.entry.gdpack.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder


CmpGoodsService ss_service = CmpGoodsService.INSTANCE();

def id = JC.request.param('id');
def f = JC.request.param('f');

GoodsPack g = ss_service.get_pack(id);
if(g==null) {
	return SimpleAjax.notAvailable('goods_not_found');
}

g.pic_url = f;
ss_service.update_pack(g);

return SimpleAjax.available();






