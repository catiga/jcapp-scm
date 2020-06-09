package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

def goods_id = JC.request.param('goods_id');
def img_id = JC.request.param('img_id');

GoodsInfo goods = GoodsService.INSTANCE().get(goods_id);

if(goods==null) {
	return ProtObj.fail(110001, '商品未找到');
}

GoodsImg img = JcTemplate.INSTANCE().get(GoodsImg, 'select * from GoodsImg where flag!=? and goods_id=? and id=?', -1, goods.id, img_id);

if(img!=null) {
	img.flag = -1;
	JcTemplate.INSTANCE().update(img);
}

return ProtObj.success(1);
