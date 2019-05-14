package com.jeancoder.scm.ready.util

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GdMerge
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.entity.GoodsSku

class ConvertToGoods {

	static def convert_pack(List<GoodsPack> packs) {
		def items = null;
		if(packs) {
			items = [];
			for(x in packs) {
				GoodsSku gs = new GoodsSku();
				gs.id = new BigInteger('-' + x.sn);
				gs.goods_id = x.id;
				gs.sku_no = x.sn;
				gs.sku_img = x.pic_url;
				gs.stock = 9999;
				gs.skus = '{}';
				gs.flag = 0;
				
				GoodsInfo g = new GoodsInfo();
				g.id = x.id;
				g.goods_id = x.sn;
				g.goods_name = x.name;
				g.goods_picturelink = x.pic_url;
				g.goods_picturelink_big = x.pic_url;
				g.goods_state = '0000';
				g.goods_price = x.sale_price;
				g.flag = 0;
				g.gt = '200';	//设置套餐
				
				//增加查找套餐内包含的商品列表
				def sql = 'select * from GoodsPackItem where flag!=? and gpid=?';
				List<GoodsPackItem> pack_items = JcTemplate.INSTANCE().find(GoodsPackItem, sql, -1, x.id);
				//查找套餐内商品的可替换商品
				if(pack_items) {
					for(y in pack_items) {
						sql = 'select * from GoodsPackItemVert where flag!=? and item_id=?';
						List<GoodsPackItemVert> pack_item_vs = JcTemplate.INSTANCE().find(GoodsPackItemVert, sql, -1, y.id);
						y.verts = pack_item_vs;
					}
				}
				items.add([gs, g, pack_items]);
			}
		}
		return items;
	}
	
	static def convert_merge(List<GdMerge> merge) {
		def items = null;
		if(merge) {
			items = [];
			for(x in merge) {
				GoodsSku gs = new GoodsSku();
				//gs.id = x.id;
				gs.id = new BigInteger('-' + x.sn);
				gs.goods_id = x.id;
				gs.sku_no = x.sn;
				gs.sku_img = x.pic_url;
				gs.stock = 9999;
				gs.skus = '{}';
				gs.flag = 0;
				
				GoodsInfo g = new GoodsInfo();
				g.id = x.id;
				g.goods_id = x.sn;
				g.goods_name = x.name;
				g.goods_picturelink = x.pic_url;
				g.goods_picturelink_big = x.pic_url;
				g.goods_state = '0000';
				g.goods_price = x.sale_price;
				g.flag = 0;
				g.gt = '300';	//设置合成品
				items.add([gs, g]);
			}
		}
		return items;
	}
}
