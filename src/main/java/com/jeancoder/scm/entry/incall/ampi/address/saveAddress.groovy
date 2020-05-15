package com.jeancoder.scm.entry.incall.ampi.address

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj

/**
{"id":1194,"name":"腾腾","mobile":"13501020884",
"province_id":29,"province_no":"120000","province_name":"天津市","city_id":61,
"city_no":"120100","city_name":"天津市","district_id":532,"district_no":"南开区","district_name":"南开区",
"address":"勇武街道12123","is_default":0}
 */
def request_data = new String(JC.request.get().getInputStream().getBytes(), 'UTF-8');

def front_user_case = JC.request.attr('front_user_case');
if(front_user_case==null) {
	return ProtObj.fail(-1, '请登录后操作');
}
def ap_id = front_user_case['ap_id'];
def pid = front_user_case['pid'];

try {
	request_data = JackSonBeanMapper.jsonToMap(request_data);
} catch(any) {
	request_data = null;
}
if(request_data==null) {
	return ProtObj.fail(110001, '请输入正确的地址信息参数');
}
def id = request_data['id'];
def name = request_data['name'];
def mobile = request_data['mobile'];
def province_code = request_data['province_no'];
def province_name = request_data['province_name'];
def city_code = request_data['city_no'];
def city_name = request_data['city_name'];
def zone_code = request_data['district_no'];
def zone_name = request_data['district_name'];
def address = request_data['address'];
def is_def = request_data['is_default'];

def param_dic = [id:id, name:name, mobile:mobile, 
	province_code:province_code, province_name:province_name, city_code:city_code, city_name:city_name,
	zone_code:zone_code, zone_name:zone_name, address:address, is_def:is_def,
	pid:pid, ap_id:ap_id]
SimpleAjax result = JC.internal.call(SimpleAjax, 'crm', '/h5/address/save', param_dic);
if(!result.available) {
	return ProtObj.fail(110002, '地址信息保存失败');
}

return ProtObj.success(1);

