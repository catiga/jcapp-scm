package com.jeancoder.scm.entry.incall.api.ad

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');
def pid = GlobalHolder.proj.id;

SimpleAjax result = JC.internal.call(SimpleAjax, 'market', '/figure/admin/get', [pid:pid, id:id]);

def tmp_o = null;
if(result && result.available) {
	if(result.data) {
		def x = result.data;
		def enable = true;
		if(x['flag']!=0) {
			enable = false;
		}
		tmp_o = ["id":x['id'], 'title':x['title'], 'info':x['info'], "link_type":x['jump_type'],"link":x['url_msg'],"goods_id":x['jump_id'],
			"image_url":x['picture'], "start_time":x['start_time'], "end_time":x['end_time'],"enabled":enable,"sort_order":x['id'],"is_delete":0];
	}
}

return ProtObj.success(tmp_o);

/*
return ["errno":0,"errmsg":"","data":["id":30,"link_type":0,"link":"","goods_id":1109034,"image_url":"http://yanxuan.nosdn.127.net/0251bd141f5b55bd4311678750a6b344.jpg","end_time":1894780212,"enabled":0,"sort_order":3,"is_delete":0]];
*/
