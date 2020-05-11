package com.jeancoder.scm.entry.incall.api.ad

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');
def sort = JC.request.param('sort');

return ProtObj.success(1);
