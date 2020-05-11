package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
def page = JC.request.param('page');

return ["errno":0,"errmsg":"","data":["count":0,"totalPages":0,"pageSize":10,"currentPage":1,"data":[]]];
