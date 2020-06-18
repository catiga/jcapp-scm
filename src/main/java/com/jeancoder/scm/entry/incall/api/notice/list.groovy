package com.jeancoder.scm.entry.incall.api.notice

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def pid = GlobalHolder.proj.id;
SimpleAjax ajax = JC.internal.call(SimpleAjax, 'market', '/figure/admin/list', [pn:1, ps:50, pid:pid, figure_type:'90']);

def data = [];
if(ajax && ajax.available && ajax.data) {
	for(x in ajax.data.result) {
		def d = ["id":x['id'],"content":x['info'],"end_time":x['end_time'],"is_delete":0];
		data.add(d);
	}
}

return ProtObj.success(data);

/*
return ["errno":0,"errmsg":"",
	"data":[
		["id":8,"content":"完全开源小程序商城 - github搜索：海风小店","end_time":"2022-12-02 23:59:59","is_delete":0],
		["id":9,"content":"可测试支付流程，但不发货不退款","end_time":"2022-12-02 23:59:59","is_delete":0],
		["id":111,"content":"如果可以，请在github点个star，谢谢","end_time":"2022-12-02 23:59:59","is_delete":0]
	]
];
*/
