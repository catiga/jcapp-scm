package com.jeancoder.scm.entry.invbatch.aj

import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService

WareHouseService war_service = WareHouseService.INSTANCE();
List<WareHouse> wars = war_service.find();

//return AjaxUtil.successs('', wars);
return SimpleAjax.available('', RetObj.build(wars));
