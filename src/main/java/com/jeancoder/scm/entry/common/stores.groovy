package com.jeancoder.scm.entry.common

import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.util.RemoteUtil


List<StoreInfo> stores = RemoteUtil.connectAsArray(StoreInfo, 'project', '/incall/mystore', null);

if(stores==null||stores.empty) {
	return SimpleAjax.notAvailable('store_not_found,门店未找到');
}

return SimpleAjax.available('', stores);