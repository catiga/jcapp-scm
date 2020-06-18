package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj

SimpleAjax province_list = JC.internal.call(SimpleAjax, 'project', '/incall/data/city', null);

def p_result = [];
if(province_list && province_list.available) {
	for(x in province_list.data) {
		def p_obj = [id:x['city_no'], code:x['city_no'], name:x['city_name']];
		
		p_result.add(p_obj);
	}
}

return ProtObj.success(p_result);

/*
return ["errno":0,"errmsg":"",
	"data":[
		["id":2,"name":"北京市"],
		["id":3,"name":"天津市"],
		["id":4,"name":"河北省"],
		["id":5,"name":"山西省"],["id":6,"name":"内蒙古自治区"],["id":7,"name":"辽宁省"],["id":8,"name":"吉林省"],["id":9,"name":"黑龙江省"],["id":10,"name":"上海市"],["id":11,"name":"江苏省"],["id":12,"name":"浙江省"],["id":13,"name":"安徽省"],["id":14,"name":"福建省"],["id":15,"name":"江西省"],["id":16,"name":"山东省"],["id":17,"name":"河南省"],["id":18,"name":"湖北省"],["id":19,"name":"湖南省"],["id":20,"name":"广东省"],["id":21,"name":"广西壮族自治区"],["id":22,"name":"海南省"],["id":23,"name":"重庆市"],["id":24,"name":"四川省"],["id":25,"name":"贵州省"],["id":26,"name":"云南省"],["id":27,"name":"西藏自治区"],["id":28,"name":"陕西省"],["id":29,"name":"甘肃省"],["id":30,"name":"青海省"],["id":31,"name":"宁夏回族自治区"],["id":32,"name":"新疆维吾尔自治区"],["id":33,"name":"台湾省"],
		["id":34,"name":"香港特别行政区"],["id":35,"name":"澳门特别行政区"],["id":36,"name":"海外"]
	]
];
*/

