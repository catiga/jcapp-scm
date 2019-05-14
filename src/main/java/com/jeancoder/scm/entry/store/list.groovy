package com.jeancoder.scm.entry.store

import com.jeancoder.core.result.Result
import com.jeancoder.scm.ready.dto.StoreInfo
import com.jeancoder.scm.ready.util.RemoteUtil

List<StoreInfo> stores = RemoteUtil.connectAsArray(StoreInfo.class, 'project', '/incall/mystore', null);

Result view = new Result();
view.setView("store/list");
view.addObject('stores', stores);
return view;