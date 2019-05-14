package com.jeancoder.scm.entry.stockin.spillover.aj

import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService


GoodsService ss_service = GoodsService.INSTANCE();

JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = 1;
page.ps = 100;

page = ss_service.find(page);

//return AjaxUtil.successs('', page);
return SimpleAjax.available('', RetObj.build(page));