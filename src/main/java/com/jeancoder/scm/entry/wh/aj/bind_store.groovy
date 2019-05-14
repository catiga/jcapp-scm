package com.jeancoder.scm.entry.wh.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.RemoteUtil

def sid = JC.request.param('sid');

//def params = ['id':sid];
//StoreInfo ret = RemoteUtil.connect(StoreInfo.class, "project", '/incall/store_by_id', params);

List<StoreInfo> ret = RemoteUtil.connectAsArray(StoreInfo, 'project', '/incall/mystore', null);

if(ret==null) {
	return SimpleAjax.notAvailable('store_not_found,门店未找到');
}
StoreInfo store = null;
for(x in ret) {
	if(x.id.toString()==sid) {
		store = x;break;
	}
}
if(store==null) {
	return SimpleAjax.notAvailable('store_not_found,门店未找到');
}

WareHouseService w_ser = WareHouseService.INSTANCE();
def id = JC.request.param('id');
WareHouse ware = w_ser.get(id);
if(ware==null) {
	return SimpleAjax.notAvailable('ware_not_found,仓库未找到');
}
ware.store_id = store.id;
ware.store_name = store.store_name;
w_ser.update(ware);
return SimpleAjax.available();



