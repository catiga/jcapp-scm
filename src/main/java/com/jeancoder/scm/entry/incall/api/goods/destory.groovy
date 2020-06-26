package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');

GoodsInfo goods = GoodsService.INSTANCE().get(id);
if(goods!=null) {
	goods.flag = -1;
	JcTemplate.INSTANCE().update(goods);
	
	//从已上架商品中移除
	GoodsService.INSTANCE().cancel_sale(goods.id, '100');
	
	//删除在售商品分类
	def remove_sale = JcTemplate.INSTANCE().batchExecute('update GfCatalog set flag=-1 where flag!=? and g_id=? and pid=? and tycode=?', -1, goods.id, GlobalHolder.proj.id, '100');
	
	//删除货品所属分类
	def remove_cat = JcTemplate.INSTANCE().batchExecute('update GoodsCatalog set flag=-1 where flag!=? and g_id=? and pid=? and tycode=?', -1, goods.id, GlobalHolder.proj.id, '100');
	println remove_sale + '---' + remove_cat;
}

return ProtObj.success(1);
