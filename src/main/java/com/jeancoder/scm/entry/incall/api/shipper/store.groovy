package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
def CustomerName = JC.request.param('CustomerName');
def MonthCode = JC.request.param('MonthCode');
def code = JC.request.param('code');
def enabled = JC.request.param('enabled');
def name = JC.request.param('name');
def sort_order = JC.request.param('sort_order');

return ["errno":0,"errmsg":"","data":["id":1,"name":"顺丰速运","code":"SF","sort_order":1,"MonthCode":"5800278123","CustomerName":null,"enabled":1]];
