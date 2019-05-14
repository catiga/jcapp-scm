package com.jeancoder.scm.entry.supplier.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.SysCity
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.form.ProviderForm
import com.jeancoder.scm.ready.service.SupplierService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.RemoteUtil

ProviderForm form = JC.extract.fromRequest(ProviderForm.class);
if(!form) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error');
}

def id = form.id;

SysCity province = RemoteUtil.connect(SysCity.class, 'project', '/incall/city_by_id', ['id':form.province]);
SysCity city = RemoteUtil.connect(SysCity.class, 'project', '/incall/city_by_id', ['id':form.city]);
SysCity zone = RemoteUtil.connect(SysCity.class, 'project', '/incall/city_by_id', ['id':form.zone]);

Provider entity = null;
if(id) {
	entity = SupplierService.INSTANCE().get(id);
	if(!entity) {
		//return AjaxUtil.fail('obj_not_found', null);
		return SimpleAjax.notAvailable('obj_not_found');
	}
} else {
	entity = new Provider();
	entity.pid = GlobalHolder.proj.id;
	entity.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
}
entity.address = form.address;
entity.contact_name = form.contact_person;
entity.contact_phone = form.contact_phone;
entity.logo = form.logo;
entity.name = form.name;
entity.num = form.num;

if(province!=null) {
	entity.province = province.city_name;
	entity.province_no = province.city_no;
}
if(city!=null) {
	entity.city = city.city_name;
	entity.city_no = city.city_no;
}
if(zone!=null) {
	entity.zone = zone.city_name;
	entity.zone_no = zone.city_no;
}
if(id) {
	SupplierService.INSTANCE().update(entity);
} else {
	SupplierService.INSTANCE().save(entity);
}
//return AjaxUtil.success();
return SimpleAjax.available();



