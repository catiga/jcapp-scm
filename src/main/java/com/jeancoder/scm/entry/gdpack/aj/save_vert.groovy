package com.jeancoder.scm.entry.gdpack.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.MoneyUtil

GoodsService ss_service = GoodsService.INSTANCE();
MergeGoodsService merge_service = MergeGoodsService.INSTANCE();
CmpGoodsService cmp_g_s = CmpGoodsService.INSTANCE();

def gpid = JC.request.param('gpid');
def data = JC.request.param('data');
def item_type = JC.request.param('item_type');
if(!item_type) {
	item_type = '100';
}

//data 格式 item_id_gid_gsid_num_price
def xs = data.split("_");
if(!xs || xs.length!=5) {
	return SimpleAjax.notAvailable('format_error,请检查替代品设置');
}

def item_id = xs[0];
def g_id = xs[1];
def gs_id = xs[2];
def num = xs[3];
def price = xs[4];

try {
	num = Integer.valueOf(num.toString().trim());
	if(num<=0) {
		return SimpleAjax.notAvailable('format_error,数量设置错误');
	}
} catch(any) {
	return SimpleAjax.notAvailable('format_error,数量设置错误');
}

try {
	price = new BigDecimal(price.toString().trim());
} catch(any) {
	return SimpleAjax.notAvailable('format_error,数量设置错误');
}

GoodsPackItem item = cmp_g_s.get_pack_item(item_id);
if(!item) {
	return SimpleAjax.notAvailable('obj_not_found,套餐内标准品已被删除');
}
def g = null;
def sku = null;
if(item_type=='100') {
	g = ss_service.get(g_id);
	sku = ss_service.get_sku(gs_id);
} else {
	//查找合成品
	g = merge_service.get(g_id);
}
if(!g) {
	return SimpleAjax.notAvailable('obj_not_found,商品或合成品未找到');
}

//查找是否已经存在该商品的替代品
def sql = 'select * from GoodsPackItemVert where flag!=? and item_id=? and fid=? and it_code=?';
GoodsPackItemVert exist_item = JcTemplate.INSTANCE().get(GoodsPackItemVert, sql, -1, item_id, g.id, item_type);
if(!exist_item) {
	//做保存操作
	exist_item = new GoodsPackItemVert();
	exist_item.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	exist_item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	exist_item.flag = 0;
	exist_item.fid = g.id;
	if(item_type=='100') {
		exist_item.fno = g.goods_id;
		exist_item.fname = g.goods_name;
		if(sku) {
			exist_item.fkid = sku.id;
			exist_item.fkno = sku.sku_no;
			exist_item.fkname = sku.skus;
		}
		exist_item.cost_price = g.cost_price;
		exist_item.rec_price = g.goods_original_price;
		exist_item.sale_price = g.goods_price;
	} else {
		exist_item.fno = g.sn;
		exist_item.fname = g.name;
		exist_item.cost_price = g.cost_price;
		exist_item.rec_price = g.rec_price;
		exist_item.sale_price = g.sale_price;
	}
	
	exist_item.gpid = item.gpid;
	
	
	exist_item.it_code = item_type;	//默认为商品
	exist_item.item_id = item.id;
	exist_item.num = num;
	exist_item.unit = g.unit;
	exist_item.fdd_price = price.multiply(new BigDecimal(100));
	JcTemplate.INSTANCE().save(exist_item);
} else {
	//做更新操作
	if(item_type=='100') {
		exist_item.cost_price = g.cost_price;
		exist_item.rec_price = g.goods_original_price;
		exist_item.sale_price = g.goods_price;
		
		exist_item.fno = g.goods_id;
		exist_item.fname = g.goods_name;
		if(sku) {
			exist_item.fkid = sku.id;
			exist_item.fkno = sku.sku_no;
			exist_item.fkname = sku.skus;
		}
	} else {
		exist_item.cost_price = g.cost_price;
		exist_item.rec_price = g.rec_price;
		exist_item.sale_price = g.sale_price;
		
		exist_item.fno = g.sn;
		exist_item.fname = g.name;
	}
	exist_item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	exist_item.fdd_price = price.multiply(new BigDecimal(100));
	exist_item.num = num;
	JcTemplate.INSTANCE().update(exist_item);
}
return SimpleAjax.available();




