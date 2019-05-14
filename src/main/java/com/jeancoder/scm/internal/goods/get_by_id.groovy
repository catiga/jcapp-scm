package com.jeancoder.scm.internal.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService

def id = JC.internal.param('id');
def pid = JC.internal.param('pid');

GoodsInfo goods = GoodsService.INSTANCE().get(id, pid);

if(goods==null) {
	return SimpleAjax.notAvailable('obj_not_found,商品未找到');
}

List<GoodsImg> goods_imgs = GoodsService.INSTANCE().find_goods_imgs(goods.id, '100');

//查询商品详细信息
GoodsContent goods_content = GoodsService.INSTANCE().get_content(goods.id);

return SimpleAjax.available('', [goods, goods_imgs, goods_content]);