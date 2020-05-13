package com.jeancoder.scm.entry.incall.ampi.settings

import com.jeancoder.app.sdk.JC;
import com.jeancoder.scm.ready.incall.api.ProtObj;

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

return ProtObj.success(1);
