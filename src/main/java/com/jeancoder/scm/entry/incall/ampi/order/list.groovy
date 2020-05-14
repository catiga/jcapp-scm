package com.jeancoder.scm.entry.incall.ampi.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def page = JC.request.param('page');
def size = JC.request.param('size');

def showType = JC.request.param('showType');

def token = JC.request.get().getHeader('X-Nideshop-Token');

return ProtObj.success([count:0, data:[]]);
