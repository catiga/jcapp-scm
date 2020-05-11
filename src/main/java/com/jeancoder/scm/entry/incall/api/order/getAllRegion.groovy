package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.api.ProtObj

SimpleAjax province_list = JC.internal.call(SimpleAjax, 'project', '/incall/data/city', null);

def p_result = [];
if(province_list && province_list.available) {
	for(x in province_list.data) {
		def p_obj = [id:x['city_no'], value:x['city_no'], label:x['city_name']];
		//查询省份的城市列表
		SimpleAjax city_list = JC.internal.call(SimpleAjax, 'project', '/incall/data/city', [p:x['id']]);
		def c_list = [];
		if(city_list && city_list.available) {
			for(y in city_list.data) {
				def c_obj = [id:y['city_no'], value:y['city_no'], label:y['city_name']];
				
				SimpleAjax zone_list = JC.internal.call(SimpleAjax, 'project', '/incall/data/city', [p:y['id']]);
				if(zone_list && zone_list.available) {
					def z_list = [];
					for(z in zone_list.data) {
						def z_obj = [id:z['city_no'], value:z['city_no'], label:z['city_name']];
						z_list.add(z_obj);
					}
					c_obj['children'] = z_list;
				}
				c_list.add(c_obj);
			}
		}
		p_obj['children'] = c_list;
		p_result.add(p_obj);
	}
}

return ProtObj.success(p_result);
