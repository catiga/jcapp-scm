package com.jeancoder.scm.entry.incall.api.ad

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
def status = JC.request.param('status');

return 'OK';
