package com.jeancoder.scm.entry.gdpack

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder

def g_id = JC.request.param('g_id');
CmpGoodsService cmp_goods_service = CmpGoodsService.INSTANCE();
GoodsService ss_service = GoodsService.INSTANCE();

Result view = new Result();
view.setView("gdpack/imgs");

if(g_id) {
	GoodsPack goods = cmp_goods_service.get_pack(g_id);
	view.addObject('goods', goods);
	if(goods!=null) {
		List<GoodsImg> imgs = ss_service.find_goods_imgs(g_id, '200');
		view.addObject("imgs", imgs);
	}
}

//def img_root = 'https://cdn.iplaysky.com'
def img_root = GlobalHolder.INSTANCE.img_path();
view.addObject('root_path', img_root);

return view;

