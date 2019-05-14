package com.jeancoder.scm.entry.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder

def g_id = JC.request.param('g_id');
GoodsService ss_service = GoodsService.INSTANCE();

Result view = new Result();
view.setView("goods/imgs");

if(g_id) {
	GoodsInfo goods = ss_service.get(g_id);
	view.addObject('goods', goods);
	if(goods!=null) {
		List<GoodsImg> imgs = ss_service.find_goods_imgs(g_id, '100');
		view.addObject("imgs", imgs);
	}
}

//def img_root = 'https://cdn.iplaysky.com'
def img_root = GlobalHolder.INSTANCE.img_path();
view.addObject('root_path', img_root);

return view;