package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.incall.api.ProtObj

def req = JC.request.get();

InputStream ins = JC.request.get().getInputStream();

def params = new String(ins.getBytes(), 'UTF-8');

try {
	params = JackSonBeanMapper.jsonToMap(params);
} catch(any) {
	return ProtObj.fail(210001, '参数结构错误');
}

def goods_info = params.get('info');
def specData = params.get('specData');
def specValue = params.get('specValue');
def cat_id = params.get('cateId');



return ProtObj.success(1);
