package com.jeancoder.scm.entry.incall.api.ad

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
def enabled = JC.request.param('enabled');
def end_time = JC.request.param('end_time');
def goods_id = JC.request.param('goods_id');
def image_url = JC.request.param('image_url');
def is_delete = JC.request.param('is_delete');
def link = JC.request.param('link');
def link_type = JC.request.param('link_type');
def sort_order = JC.request.param('sort_order');

return ["errno":0,"errmsg":"","data":["id":30,"link_type":0,"link":"","goods_id":1109034,"image_url":"http://yanxuan.nosdn.127.net/0251bd141f5b55bd4311678750a6b344.jpg","end_time":1894780212,"enabled":"0","sort_order":3,"is_delete":0]]
