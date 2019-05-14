package com.jeancoder.scm.entry.gdmerge.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.GlobalHolder


MergeGoodsService ss_service = MergeGoodsService.INSTANCE();

def gs = JC.request.param('gs');

gs = gs.split(',');
if(!gs) {
	return SimpleAjax.notAvailable('empty');
}
List<BigInteger> ids = [];
for(x in gs) {
	ids.add(BigInteger.valueOf(Long.valueOf(x)));
}

def goods_list = ss_service.find_goods_by_ids(ids);

//开始上架
for(x in goods_list) {
	ss_service.for_sale(x, GlobalHolder.proj.id);
}

return SimpleAjax.available();







