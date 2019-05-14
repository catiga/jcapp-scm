package com.jeancoder.scm.entry.wh.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.SysCity
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.form.WHForm
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.RemoteUtil

def id = JC.request.param('id');

WHForm form = JC.extract.fromRequest(WHForm.class);

WareHouseService w_ser = WareHouseService.INSTANCE();

WareHouse e = null;
if(id!='0') {
	e = w_ser.get(id);
	if(e==null) {
		//return AjaxUtil.fail('obj_not_found', null);
		return SimpleAjax.notAvailable('obj_not_found');
	}
} else {
	e = new WareHouse();
}

SysCity province = RemoteUtil.connect(SysCity.class, 'project', '/incall/city_by_id', ['id':form.province]);
SysCity city = RemoteUtil.connect(SysCity.class, 'project', '/incall/city_by_id', ['id':form.city]);
SysCity zone = RemoteUtil.connect(SysCity.class, 'project', '/incall/city_by_id', ['id':form.zone]);

e.name = form.name;
e.info = form.info;
e.remark = form.remark;
e.code = form.code;
e.level = form.level;
e.type = form.getType();
e.contact_person = form.person;
e.contact_phone = form.phone;
e.address = form.address;

if(province!=null) {
	e.province = province.city_name;
	e.province_no = province.city_no;
}
if(city!=null) {
	e.city = city.city_name;
	e.city_no = city.city_no;
}
if(zone!=null) {
	e.zone = zone.city_name;
	e.zone_no = zone.city_no;
}


if(id!='0') {
	w_ser.update(e);
} else {
	w_ser.save(e);
}

//return AjaxUtil.success();
return SimpleAjax.available();



