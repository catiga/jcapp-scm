package com.jeancoder.scm.entry.incall.api.notice

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');
def content = JC.request.param('content');
def time = JC.request.param('time');

return ProtObj.success("");
