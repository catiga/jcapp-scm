package com.jeancoder.scm.entry.common

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.scm.ready.util.RemoteUtil

def id = JC.request.param('id');

def params = ['id':id];
def ret = RemoteUtil.connect("project", '/incall/city', params);

return ret;