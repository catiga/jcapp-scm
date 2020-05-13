package com.jeancoder.scm.entry.incall.ampi.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory

def hp_wxapp_app_id = 'wx7a8d97a1f1a98596';
def hp_wxapp_app_key = '34fcc7920dd4622756a93e2a55c5411d';

JCLogger logger = JCLoggerFactory.getLogger('');

def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

logger.info(request_data);

def userInfo = [:];
userInfo['avatar'] = 'http://hbimg.b0.upaiyun.com/a10dfc94500be4eda3469e5d2ef942ddc56b1fd27de7-uOLg3r_fw658';
userInfo['nickname'] = '腾腾';

return com.jeancoder.scm.ready.incall.api.ProtObj.success([token:'123456', userInfo:userInfo, is_new:0]);