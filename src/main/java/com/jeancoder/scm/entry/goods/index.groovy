package com.jeancoder.scm.entry.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.UnitUtil

def g_no_name =JC.request.param('g_no_name')?.trim()==null?"":URLDecoder.decode(JC.request.param('g_no_name')?.trim());

def pn = Integer.valueOf(JC.request.param('pn')?:1);
GoodsService ss_service = GoodsService.INSTANCE();

JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = pn;
page.ps = 20;
page = ss_service.find_by_num(page, g_no_name);


Result view = new Result();
view.setView("goods/index");
view.addObject("page", page);

view.addObject('units', UnitUtil.toList());
view.addObject('g_no_name', g_no_name);

view.addObject('now_proj', GlobalHolder.proj);
return view;