package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');

return ["errno":0,"errmsg":"","data":["orderSum":0,"orderDone":0,"orderMoney":null,"cartSum":null]];
