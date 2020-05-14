package com.jeancoder.scm.entry.incall.ampi.footprint

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def page = JC.request.param('page');
def size = JC.request.param('size');

return ProtObj.success(null);
