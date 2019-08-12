package com.jeancoder.scm.internal.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService

def gn = JC.internal.param('gn');
def pid = JC.internal.param('pid');

List<GoodsInfo> goods = GoodsService.INSTANCE().get_by_num(gn, pid);

if(goods==null||goods.empty) {
	return SimpleAjax.notAvailable('obj_not_found,商品未找到');
}

return SimpleAjax.available('', goods);

/*
GoodsInfo g = null;
if(goods.size()==1) {
	g = goods.get(0);
}
if(g==null) {
	return SimpleAjax.available('', goods);	//直接返回商品列表
}

List<GoodsImg> goods_imgs = GoodsService.INSTANCE().find_goods_imgs(g.id, '100');

//查询商品详细信息
GoodsContent goods_content = GoodsService.INSTANCE().get_content(g.id);

return SimpleAjax.available('', [g, goods_imgs, goods_content]);
*/