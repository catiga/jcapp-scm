package com.jeancoder.scm.entry.goods.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsModel
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.form.GoodsBasic
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.SupplierService
import com.jeancoder.scm.ready.util.GlobalHolder


GoodsService ss_service = GoodsService.INSTANCE();

GoodsBasic base_info = JC.extract.fromRequest(GoodsBasic.class);

if(base_info==null) {
	return SimpleAjax.notAvailable('param_error,请检查输入参数');
}

def id = base_info.id;

GoodsInfo goods = null;
if(id!=null&&id>0) {
	goods = ss_service.get(id);
	if(goods==null) {
		return SimpleAjax.notAvailable('obj_not_found,货品未找到');
	}
	if(goods.proj_id!=GlobalHolder.proj.id) {
		return SimpleAjax.notAvailable('belong_error,货品编辑权限不足');
	}
} else {
	goods = new GoodsInfo();
}
goods.goods_name = base_info.name;
goods.goods_material = base_info.material;
goods.goods_out_no = base_info.out_no;
goods.goods_remark = base_info.remark;
goods.unit = base_info.unit;
goods.weight = base_info.weight;
goods.goods_id = base_info.num;
goods.spec_unit = base_info.spec_unit;
goods.goods_code = base_info.goods_code;

def gmid = base_info.gmid;
if(gmid) {
	GoodsModel model = ss_service.get_model(gmid);
	if(model!=null) {
		goods.model_id = gmid;
	}
}

def gen_id = null;
if(id!=null&&id>0) {
	ss_service.update(goods);
	gen_id = goods.id;
} else {
	//检查内码编号
	gen_id = ss_service.save(goods);
	if(gen_id==-1) {
		//内码重复
		return SimpleAjax.notAvailable('param_error,货品内码重复，请检查');
	}
}

return SimpleAjax.available('', RetObj.build(gen_id));







