package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');		//用户地址数据id
def name = JC.request.param('name');	//联系人
def user_id = JC.request.param('user_id');
def country_id = JC.request.param('country_id');
def province_id = JC.request.param('province_id');	//省份编号
def province_name = JC.request.param('province_name');
def city_id = JC.request.param('city_id');
def city_name = JC.request.param('city_name');
def district_id = JC.request.param('district_id');
def district_name = JC.request.param('district_name');
def address = JC.request.param('address');
def mobile = JC.request.param('mobile');
def is_default = JC.request.param('is_default');
def is_delete = JC.request.param('is_delete');
def full_region = JC.request.param('full_region');

return ProtObj.success("");
