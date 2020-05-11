package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
def page = JC.request.param('page');


return ["errno":0,"errmsg":"","data":["count":1,"totalPages":1,"pageSize":10,"currentPage":1,"data":[["id":439,"name":"呃呃呃","user_id":1528,"country_id":0,"province_id":110000, province_name:"北京市", "city_id":110100, city_name:"北京市", "district_id":110101, district_name:"东城区", "address":"开局","mobile":"13555222255","is_default":0,"is_delete":0,"full_region":"北京市北京市东城区开局"]]]];
