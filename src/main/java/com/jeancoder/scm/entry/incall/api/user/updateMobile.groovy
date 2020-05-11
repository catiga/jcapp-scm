package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');
def mobile = JC.request.param('mobile');

return ProtObj.success(1);
