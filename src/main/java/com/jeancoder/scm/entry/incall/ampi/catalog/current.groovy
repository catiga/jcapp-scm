package com.jeancoder.scm.entry.incall.ampi.catalog

import com.jeancoder.app.sdk.JC

def catalog_id = JC.request.param('id');

return ["errno":0,"errmsg":"","data":["id":1005000,"name":"居家","img_url":"http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg","p_height":155]];
