package com.jeancoder.scm.internal.goods

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.gen.GoodsNoGenerator
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.service.GoodsService

def g_info = JC.internal.param('g');
def pid = JC.internal.param('pid');

println g_info;
g_info = JackSonBeanMapper.jsonToMap(g_info);

// {id=null, goods_sn=122, goods_material=12, goods_name=21, sale_price=12, original_price=12, cost_price=12, goods_unit=101}
def id = g_info['id'];
def goods_sn = g_info['goods_sn'];
def goods_material = g_info['goods_material'];
def goods_name = g_info['goods_name'];
def sale_price = g_info['sale_price'];
def original_price = g_info['original_price'];
def cost_price = g_info['cost_price'];
def goods_unit = g_info['goods_unit'];
def goods_code = g_info['goods_code'];
def imgs = g_info['imgs'];

GoodsInfo goods = null;
def update = true;
if(id) {
	goods = GoodsService.INSTANCE().get(id, pid);
	if(!goods) {
		return SimpleAjax.notAvailable('obj_not_found,货品id不存在');
	}
} else {
	//根据编码查询
	def sql = 'select * from GoodsInfo where flag!=? and proj_id=? and goods_out_no=?';
	List<GoodsInfo> result = JcTemplate.INSTANCE().find(GoodsInfo, sql, -1, pid, goods_sn);
	if(result) {
		if(result.size()>1) {
			return SimpleAjax.notAvailable('obj_repeat,货品编码不唯一');
		}
		goods = result.get(0);
	} else {
		goods = new GoodsInfo();
		goods.proj_id = new BigInteger(pid + '');
		goods.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		goods.flag = 0;
		update = false;
	}
}
goods.goods_out_no = goods_sn;
goods.goods_name = goods_name;
goods.goods_material = goods_material;
goods.unit = goods_unit;
goods.goods_code = goods_code;

try {
	goods.goods_price = new BigDecimal(sale_price).multiply(100);
} catch(any) {}
try {
	goods.goods_original_price = new BigDecimal(original_price).multiply(100);
} catch(any) {}
try {
	goods.cost_price = new BigDecimal(cost_price).multiply(100);
} catch(any) {}

if(!goods.goods_id) {
	goods.goods_id = GoodsNoGenerator.generateNo();
}

if(update) {
	JcTemplate.INSTANCE().update(goods);
} else {
	goods.id = JcTemplate.INSTANCE().save(goods);
}

//开始处理商品图片
if(imgs) {
	imgs = imgs.split(',');
	if(imgs) {
		for(x in imgs) {
			GoodsImg img = new GoodsImg();
			img.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			img.content_type = 'image/jpeg';
			img.flag = 0;
			img.goods_id = goods.id;
			img.img_type = '0000';
			img.img_url = x;
			img.tycode = '100';
			img.ct_code = 'image';
			GoodsService.INSTANCE().save_img(img);
		}
		
	}
}

return SimpleAjax.available();

