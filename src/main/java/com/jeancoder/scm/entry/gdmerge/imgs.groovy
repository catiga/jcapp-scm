package com.jeancoder.scm.entry.gdmerge

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.GlobalHolder

def g_id = JC.request.param('g_id');
MergeGoodsService cmp_goods_service = MergeGoodsService.INSTANCE();
GoodsService ss_service = GoodsService.INSTANCE();

Result view = new Result();
view.setView("gdmerge/imgs");

if(g_id) {
	GdMerge goods = cmp_goods_service.get(g_id);
	view.addObject('goods', goods);
	if(goods!=null) {
		List<GoodsImg> imgs = ss_service.find_goods_imgs(g_id, '300');
		view.addObject("imgs", imgs);
	}
}

//def img_root = 'https://cdn.iplaysky.com'
def img_root = GlobalHolder.INSTANCE.img_path();
view.addObject('root_path', img_root);

return view;