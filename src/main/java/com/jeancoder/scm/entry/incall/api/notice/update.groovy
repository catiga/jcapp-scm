package com.jeancoder.scm.entry.incall.api.notice

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');
def content = JC.request.param('content');
def time = JC.request.param('time');

def pid = GlobalHolder.proj.id;

def figure_type = '90';	//指定为公告

SimpleAjax ajax = JC.internal.call(SimpleAjax, 'market', '/figure/admin/save', [id:id, pid:pid, figure_type:figure_type, info:content, end_time:time]);

return ProtObj.success("");
