package com.jeancoder.scm.entry.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.SupplierService
import com.jeancoder.scm.ready.util.UnitUtil

Result view = new Result();
view.setView("goods/add");

def g_id = JC.request.param('g_id');
if(!g_id) {
	g_id = 0;
}

GoodsService ss_service = GoodsService.INSTANCE();
GoodsInfo goods = ss_service.get(g_id);
if(goods==null) {
	goods = new GoodsInfo();
} else {
	//查找并显示商品的sku模型数据
	if(goods.model_id) {
		GoodsModel model = ss_service.get_model(goods.model_id);
		view.addObject('model', model);
	}
}
view.addObject('goods', goods);

GoodsContent content = ss_service.get_content(g_id);
if(content==null) {
	content = new GoodsContent();
}
view.addObject('content', content);

view.addObject('units', UnitUtil.toList());

//供应商
SupplierService provider_service = SupplierService.INSTANCE();

JcPage<Provider> page = new JcPage<Provider>();
page.pn = 1;
page.ps = 200;
page = provider_service.find(page);
view.addObject('providers', page);

return view;


