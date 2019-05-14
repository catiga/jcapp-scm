package com.jeancoder.scm.internal.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.WareHouseService

def g_no = JC.internal.param('g_no');
def pid = JC.internal.param('pid');
def sid = JC.internal.param('sid');
def wh_id = JC.internal.param('wh_id');

WareHouseService wh_service = WareHouseService.INSTANCE();
WareHouse default_wh = null;
if(wh_id) {
	default_wh = wh_service.get(wh_id, pid);
} else {
	default_wh = wh_service.get_default_warehouse_by_store(sid, pid);
}
if(default_wh==null) {
	return SimpleAjax.notAvailable('sys_set_error,请绑定门店仓库');
}

def sql = 'select * from GoodsInfo where flag!=? and proj_id=? and (goods_out_no=? or goods_code=? or goods_id=?)';
List<GoodsInfo> result = JcTemplate.INSTANCE().find(GoodsInfo, sql, -1, pid, g_no, g_no, g_no);
if(!result) {
	return SimpleAjax.notAvailable('obj_not_found,商品未找到或已经下架');
}

//根据编码查询
sql = 'select b.goods_price as cost_price, b.goods_picturelink as pic_url, a.* from GoodsStock a right join GoodsInfo b on a.goods_id=b.id where a.flag!=? and ( a.goods_no=? or b.goods_code=? or b.goods_out_no=? or b.goods_id=? ) and a.wh_id=?';
List<GoodsStock> ret = JcTemplate.INSTANCE().find(GoodsStock, sql, -1, g_no, g_no, g_no, g_no, default_wh.id);

if(!ret) {
	ret = [];
	//构造空的库存数据，并提示库存为0，但是需要返回商品信息
	for(g in result) {
		GoodsStock gs = new GoodsStock();
		gs.goods_id = g.id;
		gs.goods_name = g.goods_name;
		gs.goods_no = g.goods_id;
		gs.goods_sku_id = 0;
		gs.spec_unit = g.spec_unit;
		gs.unit = g.unit;
		gs.cost_price = g.cost_price;	//设置成本价
		gs.sale_price = g.goods_price;	//设置销售价
		gs.stock = 0;
		ret.add(gs);
	}
} else if(ret) {
	for(gs in ret) {
		for(g in result) {
			if(gs.goods_id==g.id) {
				gs.sale_price = g.goods_price;	//仅设置销售价
			}
		}
	}
}

def data = null;
if(ret!=null) {
	data = [];
	def keys = [] as String[];
	
	for(GoodsStock gs : ret) {
		def aim_obj = null;
		for(x in keys) {
			if(x.equals(gs.goods_id + "_" + gs.goods_sku_id))
				aim_obj = gs
		}
		if(!aim_obj) {
			BigDecimal num = gs.stock?gs.stock:new BigDecimal(0);	//没有出入库记录设置为0
			for(GoodsStock gs_2 : ret) {
				if(!gs.id.equals(gs_2.id)) {
					def gs_key =  gs.goods_id + "_"  + gs.goods_sku_id;
					def gs_2_key = gs_2.goods_id + "_"  + gs_2.goods_sku_id;
					if(gs_key.equals(gs_2_key)) {
						//num += gs_2.stock;
						def gs_2_stock = gs_2.stock?gs_2.stock:new BigDecimal(0);
						num = num.add(gs_2_stock).setScale(2);
					}
				}
			}
			GoodsStockData gsd = new GoodsStockData();
			gsd.goods_id = gs.goods_id;
			gsd.goods_name = gs.goods_name;
			gsd.goods_sku_id = gs.goods_sku_id==null?0:gs.goods_sku_id;
			gsd.goods_sku_name = gs.goods_sku_name;
			gsd.stock = num;
			gsd.goods_no = gs.goods_no;
			gsd.goods_sku_no = gs.goods_sku_no;
			//gsd.sale_price = gs.cost_price;
			gsd.sale_price = gs.sale_price;
			gsd.cost_price = gs.cost_price;
			gsd.pic_url = gs.pic_url;
			gsd.unit = gs.unit;
			gsd.spec_unit = gs.spec_unit;
			gsd.wh_id = gs.wh_id;
			gsd.wh_name = gs.wh_name;
			data.add(gsd);
			keys+= gs.goods_id + "_"  + gs.goods_sku_id;
		}
	}
}

return SimpleAjax.available('', data);

