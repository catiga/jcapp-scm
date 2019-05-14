package com.jeancoder.scm.internal.goods

import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.util.UnitUtil

def conf = UnitUtil.toList();

return SimpleAjax.available('', conf);