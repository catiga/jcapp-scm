package com.jeancoder.scm.interceptor.general

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.DataTrans
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.RemoteUtil

@urlmapped(["/incall/order", "/order"])

/**
 * 向交易中心注册
 */

def start = System.currentTimeMillis();

def app_code = 'scm';
def order_type = '1000';
def call_back = '/incall/order/notify';
def dataformat = null;

try {
	//DataTrans ret = RemoteUtil.connect(DataTrans, 'trade', '/incall/reg/trigger', [app_code:app_code,order_type:order_type,callback:call_back]);
	DataTrans ret = JC.internal.call(DataTrans, 'trade', '/incall/reg/trigger', [app_code:app_code,order_type:order_type,callback:call_back]);
	if(ret==null) {
		println 'error, trade reg fail';
	} else {
		GlobalHolder.setDt(ret);
	}
}catch (e) {
	
}


def end = System.currentTimeMillis();
println 'interceptor:general/pre execute time=' + ((end - start)/1000) + '秒';

return true;
