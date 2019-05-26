package com.jeancoder.scm.entry.gdpack.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.gen.GoodsNoGenerator
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.MoneyUtil

GoodsService ss_service = GoodsService.INSTANCE();
MergeGoodsService merge_service = MergeGoodsService.INSTANCE();
CmpGoodsService cmp_g_s = CmpGoodsService.INSTANCE();

def id = JC.request.param('id');
GoodsPack pack = null;
if(id!=null) {
	pack = cmp_g_s.get_pack(id);
	if(pack==null) {
		return SimpleAjax.notAvailable('obj_not_found,商品组合未找到');
	}
	if(pack.pid!=GlobalHolder.proj.id) {
		return SimpleAjax.notAvailable('belong_error,商品组合编辑权限不足');
	}
} else {
	pack = new GoodsPack();
	pack.pid = GlobalHolder.proj.id;
	pack.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	pack.flag = 0;
}

def gpname = JC.request.param('gpname');
def gpinfo = JC.request.param('gpinfo');
def gpunit = JC.request.param('gpunit');
def gpsn = JC.request.param('gpsn');
//套餐编号只能为整数
try {
	if(gpsn) {
		Integer.valueOf(gpsn);
	} else {
		gpsn = GoodsNoGenerator.generateNo();
	}
} catch(any) {
	return SimpleAjax.notAvailable('param_error,套餐编号请输入数字');
}
def sale_price = JC.request.param('gp_sale_price');
def rec_price = JC.request.param('gp_rec_price');
def cost_price = JC.request.param('gp_cost_price');

pack.name = gpname;
pack.info = gpinfo;
pack.unit = gpunit;
pack.sn = gpsn;

pack.sale_price = MoneyUtil.get_cent_from_yuan(sale_price);
pack.rec_price = MoneyUtil.get_cent_from_yuan(rec_price);
pack.cost_price = MoneyUtil.get_cent_from_yuan(cost_price);

def v = JC.request.param('v');

def data = JC.request.param('data');
data = data.split(",");
if(!data) {
	return SimpleAjax.notAvailable('empty,请选择组合包含的商品');
}

def real_sale_price = pack.sale_price;
def real_rec_price = pack.rec_price;
def real_cost_price = pack.cost_price;
if(v=="1") {
	real_sale_price = new BigDecimal(0);
	real_rec_price = new BigDecimal(0);
	real_cost_price = new BigDecimal(0);
}

def gs = [] ;
for(x in data) {
	def xs = x.split("_");
	def item_code = xs[4];
	//先只处理 item_code =100 即商品的情况
	if(item_code!='100'&&item_code!='500') {
		continue;
	}
	if(item_code=='100') {
		GoodsInfo g = ss_service.get(xs[1]);
		GoodsSku sku = ss_service.get_sku(xs[2]);
		def nums = new BigDecimal(xs[3]);
		if(g==null) {
			return SimpleAjax.notAvailable('not_found,组合包含的商品未找到或已删除，请检查');
		}
		if(nums<0) {
			return SimpleAjax.notAvailable('param_error,组合商品数量错误');
		}
		if(v=="1") {
			def tmp_g_price = g.goods_price?g.goods_price:new BigDecimal(0);
			def tmp_g_original_price = g.goods_original_price?g.goods_original_price:new BigDecimal(0);
			def tmp_g_cost_price = g.cost_price?g.cost_price:new BigDecimal(0);
			
			real_sale_price = tmp_g_price.multiply(nums).add(real_sale_price);
			real_rec_price = tmp_g_original_price.multiply(nums).add(real_rec_price);
			real_cost_price = tmp_g_cost_price.multiply(nums).add(real_cost_price);
		}
		gs.add([g, sku, nums, '100']);
	} else if(item_code=='500') {
		GdMerge g = merge_service.get(xs[1]);
		def nums = new BigDecimal(xs[3]);
		if(g==null) {
			return SimpleAjax.notAvailable('not_found,组合包含的合成品未找到或已删除，请检查');
		}
		if(nums<0) {
			return SimpleAjax.notAvailable('param_error,组合合成品数量错误');
		}
		if(v=="1") {
			def tmp_g_price = g.sale_price?g.sale_price:new BigDecimal(0);
			def tmp_g_rec_price = g.rec_price?g.rec_price:new BigDecimal(0);
			def tmp_g_cost_price = g.cost_price?g.cost_price:new BigDecimal(0);
			
			
			real_sale_price = tmp_g_price.multiply(nums).add(real_sale_price);
			real_rec_price = tmp_g_rec_price.multiply(nums).add(real_rec_price);
			real_cost_price = tmp_g_cost_price.multiply(nums).add(real_cost_price);
		}
		def sku = null;		//合成品没有sku
		gs.add([g, sku, nums, '500']);
	}
}

pack.sale_price = real_sale_price;
pack.rec_price = real_rec_price;
pack.cost_price = real_cost_price;

//先保存基础pack
if(pack==null) {
	id = cmp_g_s.save_pack_info(pack);
	if(id==-1) {
		//内码重复
		return SimpleAjax.notAvailable('param_error,组合套餐编码重复，请检查');
	}
	pack.id = id;
} else {
	cmp_g_s.update_pack(pack);
}

cmp_g_s.save_pack_item(pack, gs);

return SimpleAjax.available('', pack);




