package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.gen.GoodsNoGenerator
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.MoneyUtil
import com.jeancoder.scm.ready.util.UnitCmp
import com.jeancoder.scm.ready.util.UnitUtil

def req = JC.request.get();

InputStream ins = JC.request.get().getInputStream();

def params = new String(ins.getBytes(), 'UTF-8');

try {
	params = JackSonBeanMapper.jsonToMap(params);
} catch(any) {
	return ProtObj.fail(210001, '参数结构错误');
}

def goods_info = params.get('info');
def specData = params.get('specData');
def specValue = params.get('specValue');
def cat_id = params.get('cateId');

//开始更新商品sku信息
GoodsModel model = null;
if(specValue) {
	model = GoodsService.INSTANCE().get_model(specValue);
}

def update = false;
GoodsInfo goods = null;
if(goods_info['id']) {
	goods = GoodsService.INSTANCE().get(goods_info['id'], GlobalHolder.proj.id);
	if(goods==null) {
		return ProtObj.fail(110001, '商品未找到');
	}
	update = true;
}
if(goods==null) {
	goods = new GoodsInfo();
	goods.proj_id = GlobalHolder.proj.id;
	goods.a_time = new Date();
	goods.flag = 0;
	goods.goods_id = GoodsNoGenerator.generateNo();
	if(model!=null) {
		goods.model_id = model.id;
	}
}
goods.goods_name = goods_info['name'];
goods.goods_picturelink = goods_info['https_pic_url'];
goods.goods_picturelink_middle = goods_info['list_pic_url'];
goods.goods_remark = goods_info['goods_brief'];
if(goods_info['freight_template_id']) {
	goods.ftpl = goods_info['freight_template_id'];
}
if(goods_info['freight_type']) {
	goods.freepost = goods_info['freight_type'];
}
if(goods_info['goods_unit']) {
	UnitCmp uc = UnitUtil.match_one_by_name(goods_info['goods_unit']);
	if(uc!=null) {
		goods.unit = uc.name;
	}
}
if(goods_info['retail_price']) {
	goods.goods_price = MoneyUtil.get_cent_from_yuan(goods_info['retail_price'] + '');
}
if(goods_info['cost_price']) {
	goods.cost_price = MoneyUtil.get_cent_from_yuan(goods_info['cost_price'] + '');
}
//更新商品信息
if(update) {
	JcTemplate.INSTANCE().update(goods);
} else {
	goods.id = JcTemplate.INSTANCE().save(goods);
}

//开始更新商品详情
if(goods_info['goods_desc']) {
	GoodsContent content = GoodsService.INSTANCE().get_content(goods.id);
	if(content) {
		content.content = goods_info['goods_desc'];
		JcTemplate.INSTANCE().update(content);
	} else {
		content = new GoodsContent();
		content.content = goods_info['goods_desc'];
		content.flag = 0;
		content.goods_id = goods.id;
		JcTemplate.INSTANCE().save(content);
	}
}

//处理商品上下架
def is_on_sale = goods_info['is_on_sale'];
if(is_on_sale=='1') {
	//上架
	GoodsService.INSTANCE().for_sale(goods, GlobalHolder.proj.id);
} else {
	//下架
	GoodsService.INSTANCE().cancel_sale(goods.id, '100');
}

//处理商品分类
Catalog cat = CatalogService.INSTANCE().get(cat_id);
if(cat!=null) {
	GoodsService.INSTANCE().sale_catalog_for_goods([goods], cat);
}

//开始处理商品sku
if(model && specData) {
	//只有在选择的商品模型是有效的情况下才处理sku数据
	if(!update) {
		//新增情况下的处理方式
		for(x in specData) {
			GoodsSku sku = new GoodsSku();
			sku.flag = 0;
			sku.goods_id = goods.id;
			if(x['cost']) 
				sku.cost_price = MoneyUtil.get_cent_from_yuan(x['cost']);
			sku.sku_no = x['goods_sn'];
			if(x['retail_price'])
				sku.sku_price = MoneyUtil.get_cent_from_yuan(x['retail_price']);
			sku.remark = x['goods_name'];
			sku.skus = x['value'];
			if(x['goods_weight']) {
				sku.weight = new BigDecimal(x['goods_weight']);
			}
			if(x['goods_number']) {
				sku.stock = new BigDecimal(x['goods_number'] + '').intValue();
			}
			JcTemplate.INSTANCE().save(sku);
		}
	} else {
		//更新情况下的处理方式
		def remain_sku_ids = [];
		for(x in specData) {
			GoodsSku sku = new GoodsSku();
			sku.flag = 0;
			sku.goods_id = goods.id;
			if(x['cost']) {
				sku.cost_price = MoneyUtil.get_cent_from_yuan(x['cost'] + '');
			}
			sku.sku_no = x['goods_sn'];
			if(x['retail_price']) {
				sku.sku_price = MoneyUtil.get_cent_from_yuan(x['retail_price'] + '');
			}
			sku.remark = x['goods_name'];
			sku.skus = x['value'];
			
			if(x['goods_weight']) {
				sku.weight = new BigDecimal(x['goods_weight']);
			}
			if(x['goods_number']) {
				sku.stock = new BigDecimal(x['goods_number'] + '').intValue();
			}
			
			if(x['id']==0) {
				def sku_id = JcTemplate.INSTANCE().save(sku);
				remain_sku_ids.add(sku_id);
			} else {
				sku.id = x['id'];
				JcTemplate.INSTANCE().update(sku);
				remain_sku_ids.add(sku.id);
			}
		}
		if(!remain_sku_ids.empty) {
			//删除不在列表重的sku
			def sql = 'update GoodsSku set flag=-1 where flag!=? and goods_id=? and id not in (';
			def batch_params = []; batch_params.add(-1); batch_params.add(goods.id);
			for(x in remain_sku_ids) {
				sql += '?,';
				batch_params.add(x);
			}
			sql = sql.substring(0, sql.length() - 1) + ')';
			JcTemplate.INSTANCE().batchExecute(sql, batch_params.toArray());
		}
	}
}

return ProtObj.success(1);
