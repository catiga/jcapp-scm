package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');
def status = JC.request.param('status');

def pid = GlobalHolder.proj.id;

GoodsInfo goods = GoodsService.INSTANCE().get(id, pid);

if(goods==null) {
	return ProtObj.fail(110001, '商品未找到');
}

if(status=='false') {
	GoodsService.INSTANCE().cancel_sale(goods.id, '100');
} else {
	//上架
	GoodsService.INSTANCE().for_sale(goods, pid);
}

return ProtObj.success(1);
