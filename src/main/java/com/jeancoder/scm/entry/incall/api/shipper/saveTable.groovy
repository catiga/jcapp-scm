package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def table = JC.request.param('table');
def defaultData = JC.request.param('defaultData');
def info = JC.request.param('info');

return ProtObj.success("");
