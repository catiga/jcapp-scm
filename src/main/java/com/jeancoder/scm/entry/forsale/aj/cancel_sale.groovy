package com.jeancoder.scm.entry.forsale.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.service.GoodsService


GoodsService ss_service = GoodsService.INSTANCE();

def gs = JC.request.param('gs');
def typecode = JC.request.param('tyc');
if(!typecode) {
	typecode = '100';
}

gs = gs.split(',');
if(!gs) {
	return SimpleAjax.notAvailable('empty');
}

def ret = [];
//开始下架
for(x in gs) {
	if(ss_service.cancel_sale(x, typecode)) {
		ret.add(x);
	}
}

return new SimpleAjax(true, '', RetObj.build(ret));







