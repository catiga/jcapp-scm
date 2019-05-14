package com.jeancoder.scm.entry.gdmerge

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.UnitUtil

def g_no = JC.request.param('g_no')?.trim();

def pn = JC.request.param('pn');
try {
	pn = Integer.valueOf(pn);
} catch(any) {
	pn = 1;
}
JcPage<GoodsPack> page = new JcPage<GoodsPack>();
page.pn = pn;
page.ps = 20;

MergeGoodsService ss_service = MergeGoodsService.INSTANCE();
page = ss_service.find(page);

Result view = new Result();
view.setView("gdmerge/index");
view.addObject('page', page);

view.addObject('units', UnitUtil.toList());
view.addObject('g_no', g_no);

view.addObject('now_proj', GlobalHolder.proj);

//def img_root = 'https://cdn.iplaysky.com'
def img_root = GlobalHolder.INSTANCE.img_path();
view.addObject('root_path', img_root);

return view;