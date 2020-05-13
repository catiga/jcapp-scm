package com.jeancoder.scm.entry.incall.ampi.settings

import com.jeancoder.app.sdk.JC;
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.incall.api.ProtObj;

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

JCLogger logger = JCLoggerFactory.getLogger('');
logger.info(request_data);

return ProtObj.success(1);
