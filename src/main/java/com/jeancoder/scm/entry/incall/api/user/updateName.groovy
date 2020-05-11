package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');
def name = JC.request.param('name');

return ProtObj.success(1);
