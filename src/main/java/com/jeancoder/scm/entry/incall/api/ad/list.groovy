package com.jeancoder.scm.entry.incall.api.ad

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def pn = JC.request.param('page');
try {
	pn = Integer.valueOf(pn);
} catch(any) {
	pn = 1;
}
if(pn<1) {
	pn = 1;
}

def ps = 10;
def pid = GlobalHolder.proj.id;

SimpleAjax result = JC.internal.call(SimpleAjax, 'market', '/figure/admin/list', [pid:pid, pn:pn, ps:ps]);
def data = [];
def count = 0;
def totalPages = 0;
def pageSize = 0;
def currentPage = 0;

if(result && result.available) {
	count = result.data['totalCount'];
	totalPages = result.data['ps'];
	pageSize = ps;
	currentPage = result.data['pn'];
	for(x in result.data['result']) {
		def enable = true;
		if(x['flag']!=0) {
			enable = false;
		}
		def tmp_o = ["id":x['id'],"link_type":x['jump_type'],"link":x['url_msg'],"goods_id":x['jump_id'],"image_url":x['picture'],"end_time":x['end_time'],"enabled":enable,"sort_order":x['id'],"is_delete":0];
		data.add(tmp_o);
	}
}

return ProtObj.success([count:count, totalPages:totalPages, currentPage:currentPage, data:data]);


/*
return ["errno":0,"errmsg":"",
	"data":[
		"count":4,"totalPages":1,"pageSize":10,"currentPage":1,
		"data":[
			["id":28,"link_type":0,"link":"","goods_id":1109004,"image_url":"http://yanxuan.nosdn.127.net/ed50cbf7fab10b35f676e2451e112130.jpg","end_time":"2030-01-16 15:50:12","enabled":true,"sort_order":3,"is_delete":0],
			["id":30,"link_type":0,"link":"","goods_id":1109034,"image_url":"http://yanxuan.nosdn.127.net/0251bd141f5b55bd4311678750a6b344.jpg","end_time":"2030-01-16 15:50:12","enabled":true,"sort_order":1,"is_delete":0],
			["id":31,"link_type":0,"link":"","goods_id":1130039,"image_url":"http://yanxuan.nosdn.127.net/19b1375334f2e19130a3ba0e993d7e91.jpg","end_time":"2030-01-16 15:50:12","enabled":true,"sort_order":2,"is_delete":0],
			["id":32,"link_type":0,"link":"","goods_id":1064003,"image_url":"http://yanxuan.nosdn.127.net/b2de2ebcee090213861612909374f9f8.jpg","end_time":"2030-01-16 15:50:12","enabled":true,"sort_order":3,"is_delete":0]
		]
	]
];
*/


