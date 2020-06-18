package com.jeancoder.scm.entry.incall.api.ad

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');
def enabled = JC.request.param('enabled');
def is_delete = JC.request.param('is_delete');
def sort_order = JC.request.param('sort_order');

def pid = GlobalHolder.proj.id;
def picture = JC.request.param('image_url');
def title = JC.request.param('title');
def info = JC.request.param('info');
def url_msg = JC.request.param('link');
def type = JC.request.param('type');

def start_time = JC.request.param('start_time');
def end_time = JC.request.param('end_time');

def figure_type = JC.request.param('figure_type');	//广告呈现类型

def content = JC.request.param('content');

def jump_type = JC.request.param('link_type');		//跳转类型，商品或者是链接
def jump_id = JC.request.param('goods_id');
def jump_name = JC.request.param('jump_name');
def jump_img = JC.request.param('jump_img');
def jump_info = JC.request.param('jump_info');

def param_dic = [:];
param_dic['id'] = id;
param_dic['enabled'] = enabled;
param_dic['is_delete'] = is_delete;
param_dic['sort_order'] = sort_order;
param_dic['pid'] = pid;
param_dic['picture'] = picture;
param_dic['title'] = title;
param_dic['info'] = info;
param_dic['url_msg'] = url_msg;
param_dic['type'] = type;
param_dic['start_time'] = start_time;
param_dic['end_time'] = end_time;
param_dic['figure_type'] = figure_type;
param_dic['content'] = content;
param_dic['jump_type'] = jump_type;
param_dic['jump_id'] = jump_id;
param_dic['jump_name'] = jump_name;
param_dic['jump_img'] = jump_img;
param_dic['jump_info'] = jump_info;

SimpleAjax result = JC.internal.call(SimpleAjax, 'market', '/figure/admin/save', param_dic);

if(result && result.available) {
	def x = result.data;
	def enable = true;
	if(x['flag']!=0) {
		enable = false;
	}
	def tmp_o = ["id":x['id'], 'title':x['title'], 'info':x['info'], "link_type":x['jump_type'],"link":x['url_msg'],"goods_id":x['jump_id'],
		"image_url":x['picture'], "start_time":x['start_time'], "end_time":x['end_time'],"enabled":enable,"sort_order":x['id'],"is_delete":0];
	
	return ProtObj.success(tmp_o);
}

return ProtObj.fail(110001, result.messages[0]);

/*
return ["errno":0,"errmsg":"","data":["id":30,"link_type":0,"link":"","goods_id":1109034,"image_url":"http://yanxuan.nosdn.127.net/0251bd141f5b55bd4311678750a6b344.jpg","end_time":1894780212,"enabled":"0","sort_order":3,"is_delete":0]]
*/



