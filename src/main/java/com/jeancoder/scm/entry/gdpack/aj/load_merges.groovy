package com.jeancoder.scm.entry.gdpack.aj

import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService


MergeGoodsService ss_service = MergeGoodsService.INSTANCE();

JcPage<GdMerge> page = new JcPage<GdMerge>();
page.pn = 1;
page.ps = 1000;

page = ss_service.find(page);

return SimpleAjax.available('', RetObj.build(page));
