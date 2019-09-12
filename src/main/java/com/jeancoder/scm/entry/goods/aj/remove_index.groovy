package com.jeancoder.scm.entry.goods.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder


GoodsService ss_service = GoodsService.INSTANCE();

def id = JC.request.param('id');
def img_id = JC.request.param('img_id');

GoodsInfo g = ss_service.get(id);
if(g==null) {
	return SimpleAjax.notAvailable('goods_not_found');
}

GoodsImg img = JcTemplate.INSTANCE().get(GoodsImg, 'select * from GoodsImg where flag!=? and id=?', -1, img_id);
if(img!=null) {
	img.flag = -1;
	JcTemplate.INSTANCE().update(img);
}

return SimpleAjax.available();






