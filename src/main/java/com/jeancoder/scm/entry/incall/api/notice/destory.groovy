package com.jeancoder.scm.entry.incall.api.notice

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');

SimpleAjax ajax = JC.internal.call(SimpleAjax, 'market', '/figure/admin/remove', [id:id, pid:GlobalHolder.proj.id]);

if(ajax && ajax.available)
	return ProtObj.success("");

return ProtObj.fail(210001, '操作失败');
