package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.incall.api.dto.goods.Goods
import com.jeancoder.scm.ready.incall.api.dto.goods.SKU


def goods_1_sku_1 = new SKU(
	"id": 246,
	"goods_id": 1009024,
	"goods_specification_ids": "7",
	"goods_sn": "12313",
	"goods_number": 200,
	"retail_price": 0.5,
	"cost": 1,
	"goods_weight": 2,
	"has_change": 0,
	"goods_name": "懒人椅",
	"is_on_sale": "1",
	"is_delete": 0,
	"value": "一根葱"
);
def goods_1_sku_2 = new SKU(
	"id": 251,
	"goods_id": 1009024,
	"goods_specification_ids": "77",
	"goods_sn": "1234",
	"goods_number": 100,
	"retail_price": 5,
	"cost": 1,
	"goods_weight": 2,
	"has_change": 0,
	"goods_name": "懒人椅",
	"is_on_sale": "1",
	"is_delete": 0,
	"value": "一个面包"
);
def goods_1_sku_list = []; goods_1_sku_list.add(goods_1_sku_1); goods_1_sku_list.add(goods_1_sku_2);

def goods_1 = new Goods(
	"id": 1009024,
	"category_id": 1005000,
	"is_on_sale": true,
	"name": "支付测试兼打赏",
	"goods_number": 22,
	"sell_volume": 2923,
	"keywords": "",
	"retail_price": "0.5-5",
	"min_retail_price": 0.5,
	"cost_price": "1-1",
	"min_cost_price": 1,
	"goods_brief": "此商品可以测试分享到朋友圈",
	"goods_desc": "<p><br></p><p><img src=\"http://yanxuan.nosdn.127.net/34a6a0daa3f7a397a38aad14cb9e90fa.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/76637af0eec246b318cb129b768de637.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18fee22626e61fc1d1a01916914016ba.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91f57a9bb142e1c1e2ff0bbea6f9af96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/252d80fd75eb1254d746d0b57c267650.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4b07697992a2b14de6fd0a5811936d71.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c499439d6081bb4e836955b7514c1b96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bed437fdc091d020a8f805bcc8830bd8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0fc5febdb817abd7a1040bab03f048b7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a0417b3986c9dc082124fcc360390021.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a5c9d24c652d4dee7946ef925105f3f2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b10272c58f95dd6737ce1cd41452a21d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/510c6ef36760238b38ed59cd6e47a21f.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6371348b917c021c55dc393fc59d4d28.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/de4079b128e57c5c0fa8a8177e9bc6e7.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/160966fbc772787f824dc1dbd5afb16d.png\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bb3c8d3f10f2aca0908871c8e598aa0e.jpg\"></p>",
	"sort_order": 1,
	"is_index": true,
	"is_new": 0,
	"goods_unit": "件",
	"https_pic_url": "https://githttps.hiolabs.com/5b7c1d0a-a12f-48e5-9487-efb1a81a6864",
	"list_pic_url": "http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png",
	"freight_template_id": 17,
	"freight_type": 0,
	"is_delete": 0,
	"has_gallery": 1,
	"has_done": 1,
	"category_name": "居家",
	"product": goods_1_sku_list
);

return ProtObj.success([count:1, totalPages:1, pageSize:10, currentPage:1, data: [goods_1, goods_1]]);

