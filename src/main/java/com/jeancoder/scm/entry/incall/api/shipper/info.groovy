package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');

return ["errno":0,"errmsg":"","data":["id":1,"name":"顺丰速运","code":"SF","sort_order":1,"MonthCode":"5800278123","CustomerName":null,"enabled":1]];
