package com.jeancoder.scm.entry.incall.ampi.index

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.service.CatalogIndexService
import com.jeancoder.scm.ready.util.GlobalHolder

//首先获取推荐到首页的分类
def dscs = JC.request.param('dscs');	//前端传过来的要获取的访问分类的渠道
def pid = GlobalHolder.proj.id;

List<Catalog> channels = CatalogIndexService.INSTANCE.find_dsc_catalogs(pid, dscs, null);
def channel = [];
if(channels) {
	for(x in channels) {
		def t_obj = [id:x.id, name:x.cat_name_cn, keywords:x.cat_name_en, front_desc:x.cat_info, sort_order:x.seq, show_index:x.seq, is_show:x.cat_show, 
			icon_url:x.cat_icon, img_url:x.cat_icon, level:1, front_name:x.cat_name_cn, p_height:155, is_category:1, is_channel:1];
		channel.add(t_obj);
	}
}
//开始寻找每个channel下的商品
def categoryList = [];
if(channel) {
	for(x in channel) {
		def t_obj = [id:x['id'], name:x['name'], height:155, banner:x['img_url']];
		//查找对应分类下的商品
		def result = CatalogIndexService.INSTANCE.find_catalog_merge_goods(pid, x['id'], null, 1, 6);
		def goodsList = [];
		if(result) {
			for(y in result) {
				def goods = y[1];
				def goods_sku = y[0];
				goodsList.add(build_index_goods_data(goods, goods_sku));
			}
		}
		
		t_obj['goodsList'] = goodsList;
		categoryList.add(t_obj);
	}
}


return [
	"errno": 0,
	"errmsg": "",
	"data": [
		"channel": channel,
		"banner": [[
			"id": 30,
			"link_type": 0,
			"link": "",
			"goods_id": 1109034,
			"image_url": "http://yanxuan.nosdn.127.net/0251bd141f5b55bd4311678750a6b344.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 1,
			"is_delete": 0
		], [
			"id": 31,
			"link_type": 0,
			"link": "",
			"goods_id": 1130039,
			"image_url": "http://yanxuan.nosdn.127.net/19b1375334f2e19130a3ba0e993d7e91.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 2,
			"is_delete": 0
		], [
			"id": 28,
			"link_type": 0,
			"link": "",
			"goods_id": 1109004,
			"image_url": "http://yanxuan.nosdn.127.net/ed50cbf7fab10b35f676e2451e112130.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 3,
			"is_delete": 0
		], [
			"id": 32,
			"link_type": 0,
			"link": "",
			"goods_id": 1064003,
			"image_url": "http://yanxuan.nosdn.127.net/b2de2ebcee090213861612909374f9f8.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 3,
			"is_delete": 0
		]],
		"notice": [[
			"id": 8,
			"content": "完全开源小程序商城 - github搜索：海风小店",
			"end_time": 1669996799,
			"is_delete": 0
		], [
			"id": 9,
			"content": "可测试支付流程，但不发货不退款",
			"end_time": 1669996799,
			"is_delete": 0
		], [
			"id": 111,
			"content": "如果可以，请在github点个star，谢谢",
			"end_time": 1669996799,
			"is_delete": 0
		]],
		"categoryList": categoryList
	]
];

def build_index_goods_data(GoodsInfo goods, GoodsSku goods_sku) {
	def _9999 = new BigDecimal(99999999);
	def _100 = new BigDecimal(100);
	
	def category_id = 0;
	def cost_price = '1-1';
	def freight_template_id = goods.ftpl;
	def freight_type = goods.freepost;
	
	def goods_brief = goods.goods_remark;
	def goods_desc = goods.goods_remark;
	def goods_number = goods_sku.stock;
	def goods_unit = goods.unit;
	def has_done = 1;
	def has_gallery = 1;
	def https_pic_url = goods.goods_picturelink;
	def id = goods.id;
	def sku_id = goods_sku.id;
	
	def tyc = goods.gt;
	
	def is_delete = 0;
	def is_index = 1;
	def is_new = 1;
	def is_on_sale = 1;
	def keywords = "";
	def list_pic_url = goods.goods_picturelink_big;
	def min_cost_price = goods.goods_price;
	if(min_cost_price==null) {
		min_cost_price = _9999;
	}
	min_cost_price = min_cost_price.divide(_100);
	
	def min_retail_price = goods.goods_price;
	if(min_retail_price==null) {
		min_retail_price = _9999;
	}
	min_retail_price = min_retail_price.divide(_100);
	
	def name = goods.goods_name;
	def retail_price = goods.goods_price;
	if(retail_price==null) {
		retail_price = _9999;
	}
	retail_price = retail_price.divide(_100);
	
	def sell_volume = 2923;
	def sort_order = 1;
	
	
	return [category_id:category_id, cost_price:cost_price, freight_template_id:freight_template_id, freight_type:freight_type,
		goods_brief:goods_brief, goods_desc:goods_desc, goods_number:goods_number, goods_unit:goods_unit, has_done:has_done, has_gallery:has_gallery,
		https_pic_url:https_pic_url, id:id, sku_id:sku_id, is_delete:is_delete, is_index:is_index, is_new:is_new, is_on_sale:is_on_sale,
		keywords:keywords, list_pic_url:list_pic_url, min_cost_price:min_cost_price, min_retail_price:min_retail_price,
		name:name, retail_price:retail_price, sell_volume:sell_volume, sort_order:sort_order,
		tyc:tyc
		];
}

/*
return [
	"errno": 0,
	"errmsg": "",
	"data": [
		"channel": [[
			"id": 1005000,
			"name": "居家",
			"keywords": "",
			"front_desc": "回家，放松身心",
			"parent_id": 0,
			"sort_order": 1,
			"show_index": 1,
			"is_show": 1,
			"icon_url": "http://yanxuan.nosdn.127.net/a45c2c262a476fea0b9fc684fed91ef5.png",
			"img_url": "http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg",
			"level": "L1",
			"front_name": "回家，放松身心",
			"p_height": 155,
			"is_category": 1,
			"is_channel": 1
		], [
			"id": 1005001,
			"name": "餐厨",
			"keywords": "",
			"front_desc": "厨房",
			"parent_id": 0,
			"sort_order": 2,
			"show_index": 2,
			"is_show": 1,
			"icon_url": "http://yanxuan.nosdn.127.net/ad8b00d084cb7d0958998edb5fee9c0a.png",
			"img_url": "http://nos.netease.com/yanxuan/88855173a0cfcfd889ee6394a3259c4f.jpg",
			"level": "L1",
			"front_name": "爱，囿于厨房",
			"p_height": 155,
			"is_category": 1,
			"is_channel": 1
		], [
			"id": 1008000,
			"name": "配件",
			"keywords": "",
			"front_desc": "配角，亦是主角",
			"parent_id": 0,
			"sort_order": 3,
			"show_index": 3,
			"is_show": 1,
			"icon_url": "http://yanxuan.nosdn.127.net/11abb11c4cfdee59abfb6d16caca4c6a.png",
			"img_url": "http://nos.netease.com/yanxuan/935f1ab7dcfeb4bbd4a5da9935161aaf.jpg",
			"level": "L1",
			"front_name": "配角，亦是主角",
			"p_height": 155,
			"is_category": 1,
			"is_channel": 1
		], [
			"id": 1012000,
			"name": "杂货",
			"keywords": "",
			"front_desc": "解忧，每个烦恼",
			"parent_id": 0,
			"sort_order": 4,
			"show_index": 7,
			"is_show": 1,
			"icon_url": "http://yanxuan.nosdn.127.net/c2a3d6349e72c35931fe3b5bcd0966be.png",
			"img_url": "http://nos.netease.com/yanxuan/a0c91ae573079830743dec6ee08f5841.jpg",
			"level": "L1",
			"front_name": "解忧，每个烦恼",
			"p_height": 155,
			"is_category": 1,
			"is_channel": 1
		], [
			"id": 1019000,
			"name": "志趣",
			"keywords": "",
			"front_desc": "爱好，点缀生活",
			"parent_id": 0,
			"sort_order": 5,
			"show_index": 9,
			"is_show": 1,
			"icon_url": "http://yanxuan.nosdn.127.net/7093cfecb9dde1dd3eaf459623df4071.png",
			"img_url": "http://nos.netease.com/yanxuan/72de912b6350b33ecf88a27498840e62.jpg",
			"level": "L1",
			"front_name": "周边精品，共享热爱",
			"p_height": 155,
			"is_category": 1,
			"is_channel": 1
		]],
		"banner": [[
			"id": 30,
			"link_type": 0,
			"link": "",
			"goods_id": 1109034,
			"image_url": "http://yanxuan.nosdn.127.net/0251bd141f5b55bd4311678750a6b344.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 1,
			"is_delete": 0
		], [
			"id": 31,
			"link_type": 0,
			"link": "",
			"goods_id": 1130039,
			"image_url": "http://yanxuan.nosdn.127.net/19b1375334f2e19130a3ba0e993d7e91.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 2,
			"is_delete": 0
		], [
			"id": 28,
			"link_type": 0,
			"link": "",
			"goods_id": 1109004,
			"image_url": "http://yanxuan.nosdn.127.net/ed50cbf7fab10b35f676e2451e112130.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 3,
			"is_delete": 0
		], [
			"id": 32,
			"link_type": 0,
			"link": "",
			"goods_id": 1064003,
			"image_url": "http://yanxuan.nosdn.127.net/b2de2ebcee090213861612909374f9f8.jpg",
			"end_time": 1894780212,
			"enabled": 1,
			"sort_order": 3,
			"is_delete": 0
		]],
		"notice": [[
			"id": 8,
			"content": "完全开源小程序商城 - github搜索：海风小店",
			"end_time": 1669996799,
			"is_delete": 0
		], [
			"id": 9,
			"content": "可测试支付流程，但不发货不退款",
			"end_time": 1669996799,
			"is_delete": 0
		], [
			"id": 111,
			"content": "如果可以，请在github点个star，谢谢",
			"end_time": 1669996799,
			"is_delete": 0
		]],
		"categoryList": [[
			"id": 1005000,
			"name": "居家",
			"goodsList": [[
				"id": 1009024,
				"category_id": 1005000,
				"is_on_sale": 1,
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
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "https://githttps.hiolabs.com/5b7c1d0a-a12f-48e5-9487-efb1a81a6864",
				"list_pic_url": "http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png",
				"freight_template_id": 17,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			], [
				"id": 1086015,
				"category_id": 1005000,
				"is_on_sale": 1,
				"name": "支付测试老板向",
				"goods_number": 100,
				"sell_volume": 2711,
				"keywords": "",
				"retail_price": "49.9",
				"min_retail_price": 49.9,
				"cost_price": "100",
				"min_cost_price": 100,
				"goods_brief": "开源不易，请打赏",
				"goods_desc": "<p><br></p><p><img src=\"http://yanxuan.nosdn.127.net/1438c1c7d5fac9ee8a957f0e498efbab.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/80d0037836d80ec0aa25ff641c261735.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d527b3bb238e9399d00c94e59f067ab6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b4004fb355802f2bf0ade96e53777f7b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9e2156d0593a949ef23458950a1c3b45.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c1670a25eb39faab38247fbbd302e1aa.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/488551c7d433c52a5d6511ca5dca96ba.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5b3997a05bce53a3e65cc22357d09034.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f97f5fc70124c70b56598086acf3e4f0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/2c48c53b8727b1d0209a1db7d03f1f50.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bc22d8146d53d256d7972478a7858a42.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8592232780fdca35d29c9e9cef0e25f4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/56e16e7ae04456d801533b969de58127.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fe7ad603619f4614c11a3afacf98e9f9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c04470af48bf83539efe1ea0380cda20.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/14d70de1f7ea5309e97f145a895bdd6d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4cd3c3a60d92575400098cd31cf994bb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c46be17c0c367292dd3a612d277898f6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/68e909b6bbfd4760f6d94170bb9f1e32.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6e2fa55c8e1f12d592d85582c8be22f4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9ccbf36677da68035d9cca9deaf53bb3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ae50a463802c44ff72b340374c93cbe3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7afafb4d6813b46bf0ad3e750ee150d2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fbcb8970bf6c845ce6c47ce5b15e8c74.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f0a696a54810118531e752d2f12bdfa4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/28ccd9c35226d3e5c7c2911b37ed4a4f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/288c91f8d06c08ca0f7c84a7c24e73be.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8a132263063ce01eb7b1aeb772343841.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e71801586b48c9c9e0fff8a839eb9cf0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/87df9ec07b264d5f504a29d89ee2b383.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1b170f3f949467f5eb92406a6a348ae8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6d02894b6d9134f0f0246b5cf51b116e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1b00efd8962c004cfa2ed7b1c785f090.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/80ecf013fd3b56477fe2473f76d46131.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9f469a6d04a48d833f91e7c84bee11f7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7c87cf3b2e6ae0f67edc0a45f71a427b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/390276a05dabfa7578e6f7d316692732.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c8a5f9c29d293488c2b35453e137691a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c13b7d09ed64a8cb73970cd7b66093a0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f6a10c6dbf2116b893cf487c2a959c82.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9d7d50e5fcd430b9ccde72cbd84addea.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a733fc402f2ed628c003c9f08c59f993.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/25d64e1901953a651bc7de7e3edde87c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0c1526ed336c04ad9703d3a203d42d88.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6768f1bac041434d73bb3c30bbbed21b.jpg\"></p>",
				"sort_order": 1,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/0e9c8555164427bafa704aa73cbe707f.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/d5c2ecfe0fb00cdd8b829975bab21a31.png",
				"freight_template_id": 15,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			]],
			"banner": "http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg",
			"height": 155
		], [
			"id": 1005001,
			"name": "餐厨",
			"goodsList": [[
				"id": 1064021,
				"category_id": 1005001,
				"is_on_sale": 1,
				"name": "清欢日式可调节台灯",
				"goods_number": 100,
				"sell_volume": 0,
				"keywords": "",
				"retail_price": "199",
				"min_retail_price": 199,
				"cost_price": "100",
				"min_cost_price": 100,
				"goods_brief": "木铁结合，全体可调节",
				"goods_desc": "<p><img src=\"http://yanxuan.nosdn.127.net/15e1d839714a67bff57259d61d7ca2e5.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/43ba9bbc931f954e0aaeb90631a179ac.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ac5ca160c90c1a700160d5d024ad611b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0f2d157d83955d5c616dc3d647da6f66.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a2ee5af100a5432f0614e9717196787e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91fb28c19d495e47797ef250940ab618.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6f37742eed13ea12d8c341e1e3b988e9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5ff15b419a0248b7b53bd60286067766.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/21c4dacdc844da911e43eaac0975908a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3f8741dd387c51378f78bdbf65856a41.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/dbe9726936999c62ac8bce3b620cd045.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d12ad2de3cb646e76380c064ccfe038f.jpg\"></p>",
				"sort_order": 3,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/0804833605816d03e57705a77f1b8aed.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/c83a3881704094ddd3970099ca77d115.png",
				"freight_template_id": 17,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			]],
			"banner": "http://nos.netease.com/yanxuan/88855173a0cfcfd889ee6394a3259c4f.jpg",
			"height": 155
		], [
			"id": 1008000,
			"name": "配件",
			"goodsList": [[
				"id": 1083009,
				"category_id": 1008000,
				"is_on_sale": 1,
				"name": "海洋之心永生花",
				"goods_number": 1000,
				"sell_volume": 1331,
				"keywords": "",
				"retail_price": "90",
				"min_retail_price": 90,
				"cost_price": "80",
				"min_cost_price": 80,
				"goods_brief": "厄瓜多尔玫瑰，精致美感",
				"goods_desc": "<p><img src=\"http://yanxuan.nosdn.127.net/1b746ed72f6bf99405c749cb6c9da6eb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/06b441ba473cabbc5189da42a2c0eb77.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b3f24e6e91b100c8132feb082f1a34e9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/696cba61d6b37131c2322b66d0495b93.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f1b458886ef3de6961975f86425fafb0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/63635766ac69f5009483eaca4d696da2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3b0d9fdd0909325b8a067e2e7d70c157.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/82da955cf9e36e3d08dadc59f5f300c3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/20d2cc7cdf5e6efe02bfe466e66cd9f6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3a337c585ca3a1bbeee9785e0f6a191c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/65277e00b7fc1a3319881a3692777268.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/31e4584649abe39c7f197ba8924fb74c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fbc59ffa03f9901cdccfba627b60e1ce.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d4b72e337df5ed195cfbc661a3777845.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/080ce5c403e912ac791b31eb0c289511.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/78b066ce0b49497a9501948063322263.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7fb74a8a28be5f5c6dacfd16f978afe2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/36b820addabfb7caf910529969a3f1bb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/219fdb339a336e4f72f40c8849069b15.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bfb962ce656900f82c46c2e2d3a5df1d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/01946020d4e1500d250d28c7e7e0dae5.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/20b66f85a1606fda8fc700a85c17d8d7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/31a70dab0b81c7eac9696b2116a6df99.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/611db5a79922174f7fc267267c01fb6a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ceeea13917bc6ba5d8b358007feacc4f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/95cb22dfb9901f209719996cb40c1d41.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e98ac4660f3907189a73040d91c74609.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8593ac139f076079f1600d5c9dc8018d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0045d4dd0585566f1b93eaf844e3073a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f9d407b3292b173daec7044c3db1fd6c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1f5e7818316caa798f48fae4f3cdb938.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/be171d645ed6e4f2651df9d3f6f4231e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ea8bf6c7ebc5641209fb1c846f3bef5f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/95b1e32659f10a90e0ea01255d318df2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5c60047bd75a8b0cc943925f2ecd6e63.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0d5fdfb237a0ff76f276662437a49bdd.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/476a35de0402c6ffb483d890ab0ddbef.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/02a627d54428aced361f96e3e6eca226.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e6b5d161c87cae4b6d713fd79e135045.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d893f5518e755af8686cf13e4490a129.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/54fa3793ff82c378ab52a9eaf6fa4f7e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/053b11e1be5bbf24773853025125dec0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/57798ce86240acd240f4b56c9aa04f01.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8a379abfeada13c4696c48379a035373.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8a2d49a4c05ee87b057e9f0bd1c22a66.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91bdd265ca73b68ecc940bda58c67d5d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/46328006b2280246628dbbf890cb7b72.jpg\"></p>",
				"sort_order": 1,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/4b862041e2c90ac3fcbbd78994884272.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/76e5c820f6bb71a26517ffa01f499871.png",
				"freight_template_id": 14,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			]],
			"banner": "http://nos.netease.com/yanxuan/935f1ab7dcfeb4bbd4a5da9935161aaf.jpg",
			"height": 155
		], [
			"id": 1012000,
			"name": "杂货",
			"goodsList": [[
				"id": 1135052,
				"category_id": 1012000,
				"is_on_sale": 1,
				"name": "日式简约素色窗帘",
				"goods_number": 100,
				"sell_volume": 2776,
				"keywords": "",
				"retail_price": "259",
				"min_retail_price": 259,
				"cost_price": "200",
				"min_cost_price": 200,
				"goods_brief": "日式极简美学 舒适棉麻质感",
				"goods_desc": "<p><img src=\"http://yanxuan.nosdn.127.net/2edc19a016d64d3b29aa71b0bf6cc928.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6bdc1fc55629dbfccbdfaa6b17ebfacc.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4ef1e93a0d3f71d7862441ffd922e522.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/85a1e175e39a6576dffb2f0ca28597f9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/486aedd7ce019d1c465bf8bbf77314bc.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b9f8f7045f07d1bb742f67120009afd0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c813c0a23951007b020b9b03d0f31cb0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9a87cebbc5b127abf6a6e5129d4ed20c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/38e85411821a681c2dd5a5b9bd3cab92.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d3907fa867c71f4090699898d53b937d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c284309b66cc8d073f528c7ebf78c5fb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1ecb27fc699a97883f7d14c7e606daa3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/eaf351d46c216f2f3a3588b3985d18c0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/785ab1ac578124415bbd718813413333.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8605647112b62e72f78ee27d29eede3d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3952310896b95dcdc3adb986e5487a86.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/030f115457262723b3d821987e38b111.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/cf4254e1d4962d6e9f5e4734785f57ae.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ce29d7cdc0e56477a623d03856646448.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/515086ca9168f8008291e98a01f4e4c9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d9bb9d186d4a364ed0c91606d561a7f5.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/63204b444e0d06a7fafb6c5e35153eaa.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3d13212d5d43f766d3f5c37ccb4e5714.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/19d5b4dbb590d90c3e40525a3ef35019.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0a1fcdbac2d1bf232e95389e20ed07c4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8d230fbda487cf445cd57f4296b7a487.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5819e9bbdeda97281990f61c531fdbbb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/802799f78d7069fc4dc44ff339729dc9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/de5108481df4cbc2e064a1fcc477475f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4a94262fecc2427d613ad7cbdd0a59f6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7b18c9c41d0520d1e0f1b58a7ae13e5e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/564d7115798352b765da2633949a1818.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9f84553b8cfa29fb222b17bc89140fa9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3b01288738e632c5afda98184bb62095.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/23088788bbfd7bf4b60ee06e9722ed74.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8b2a400e968802cd64d31bdc038b3e2a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b0e31fe278c7704fed496ea82c679044.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/2054d55fc3bdaa380ce28c1fa9b4b00d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4d03bae43ba821cd933d0e652e9d3bb7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/cc1c458d99de149af69d8ac7582b72dd.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b92c88836f39dfc194d7e56cfdfcaf5c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1446a01f9725a03ca79ab3e57fdc181a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/63fb7826d9f2d34f22317110d60a5b9d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91b1dced907315eb0e771a504d29e6d7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/985d212a97d26d4ff1029ee4efdbc4f1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a8978ab0059b90f8b018b1de05fa4e8d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/25f62074f6e7e3e2b95a71e32aa43311.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9e8fa97196d22fb3bbc4cd871267cc79.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0c3478950cc4f473849ed3ab3de21bd4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/838a150e46e5720a78f61ac48d380891.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/2abd7db65b092e91ce87c09bf4c4c17f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e603a799d802123ceaf5dbf3de02b051.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8460ba6d6347be878f035b7457ec9057.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3b02f932cfd15a19a2f1cebfab19b2cb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/41fe9fb3c58a41023a74ef1740573fec.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/19cc0338b0efefe7f0140efde638c705.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8e670a47f65733d99c4884f90b49d8e6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4859ec0aeecc39ed751f2a07daecad10.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8b7b2a56a1ea98119e89ae93b71e68c3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6e9767a0f40cbaf19307bd0c1ef97df9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5ca04a1436a6598858fc74db1f571529.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/738fc25303e4bb32d1efe45641f1b120.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c5de7c6679cfe0361ac9d164276739ae.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5e1edc1ea0bcc4cd92134ce8cc775577.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e1eaacf6e5dc52fecb3e9c889db2ee32.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/78cea538e5017c2782a96e215f42d6f3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f268a66c2e2476c075dd87416790b761.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a9f6900f77c23e57a0a2633554ce0c8a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9db7f3c92e24143e8ece310935785e41.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/923a8a571519b89b1a03e898871ef586.jpg\"></p>",
				"sort_order": 34,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/38129b4cdabcdf5610160d3a15ef259b.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/63f5da1f5363af43aa91864bf66af48e.png",
				"freight_template_id": 15,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			], [
				"id": 1135051,
				"category_id": 1012000,
				"is_on_sale": 1,
				"name": "日式素雅纯色流星纹窗帘",
				"goods_number": 100,
				"sell_volume": 1816,
				"keywords": "",
				"retail_price": "299",
				"min_retail_price": 299,
				"cost_price": "200",
				"min_cost_price": 200,
				"goods_brief": "日式素雅设计 流星纹简约肌理",
				"goods_desc": "<p><img src=\"http://yanxuan.nosdn.127.net/70d4eceeb3f066bd4ea015fca75f424c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ebc7b596ef941847afe073a173bcfa59.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c14d1ecbff1c587aefd16d65b47cae51.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4d269aeec1f4410c68b2f2c3f77f7985.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8ae8263c89b5fbc0de5df0c21d67d3d2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/915f66ecc21197b4d99b5c3df5cbffab.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4781c28a5295b239075883ccaa0a9c74.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91a4983796259d464e1f3795da189a17.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3950395fcc7e7fdc388a2ccf2b6ac0e7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9112abffdf6edd3b0ab8f9fb364d84f7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/96dcd6ccfec216bf2ce45393ca0aead1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6dffe4e2cce9fb45abe1182bcb1939e2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d358645610f642ef7fd262c0101790e0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fb51ccf74568565ba366f0eabf54ccbd.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7517d9dc2d70be25722a009083ed82df.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/11deb1b2b5efa58291c8a62355df6017.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/172053c17a9a97a8108f8fd170a6b07f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a1c0ac59021d408dcd9845c8702522a1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/781d08f49d1870ac9cf8b93dacfbf711.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0b764a61f92c6db3440f6b4aed6a7625.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/31ec911d03541ace9eddfbf3fcdf0414.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9d602c94b739bb01da939d75a9187a16.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/cc557a3e4478f089973519699a01c01c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b1efdae30fe5189bb998509d446ff271.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/12116c10da83b5835a36315711640765.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9491370cd9c5486fd9aa22ef9fdbd6fc.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/37ca15cf48249bc390fcc05077741b98.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/99fec7d83ae7e82a4426d3859875d8c7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e9912fa7c176dc1b64c060992eb40b60.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a04c0b364a1fa322ca6a71f35708e3ad.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8604f596b1d0703547d40608dfe16f2a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3b86422ab0ffe13da8f627245ec4dc98.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3b2d3c68d23acb03a3c542cccb301bfb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d563205fb46fc71c3c37448673f5e714.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18efa2d731c643a56a2976b4c793eec2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0b644146188b5ceba4b86ec82352f660.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d9c51c9c2c94f28102cb516656c7c761.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/769660ddc2ad27a1e62543460ff2bc41.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c95e64b4454063ba74c8cb17a95d60cf.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9a79d20d29a65bd010cf084c7b783d96.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18a89642689470308392fa485a633709.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/706c31eaec80f9417fbcdd40c16a7f63.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bec2e276673ab2787f03b8bd444044af.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3afb40257bc5fac38369dd465fe7732c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5cfe30745d27efdc8080944bd8d8c927.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e3aabaab6a42e859c2362998f235a564.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6946e6dfe5a3026c67672c69061a06ae.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/778f89a8b141f03b6fcb206692b37d7f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5b991c295b7bc2f6a6e04f7e79cede25.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0e3f9724481dda6c024854750a3aeaa2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/48bc347fe73c771d19787229da647421.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7a2a0bfe4036e4c3add5ba33313da069.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3ec518bd9a7f9914841cc92e3e52fa45.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d3054be42bc630607c3a46a9fe2ce6ae.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1b368f5bd63b6b59b2064af102a1fa8e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/dda7a836f7a7c54256b35253bbfba90f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/91216189bef9aa48018bbf8e654443ea.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/10b8c1cace53090fe30a9834365ee37e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/dbf5782db6be5a01de6d0cf1c64eea30.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c49df712817bfe30ec83a26af1b8b1de.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/510c05dc331823949d1567c552562a9e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c673ce9d4d825503402f1c352b8e82ec.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/378135cf12f5c044871b59c060cacbb9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/dd58aebd08f03474c7ed159c005a986c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5a99049b512e9a388ffb1520827b2739.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fa65626e5df752d70ae4662d1b15dc8a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/84d1dcf5083fd69abf4451a660f31d59.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6f781abaecbfb60889be6f46f83f4069.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/26a745d26c76a4577feaf58b27ff557b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/887eb48649e1db53f3047340404209f6.jpg\"></p>",
				"sort_order": 35,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/95dbdb2e63eeed44c4a40ea586ad4196.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/9126151f028a8804026d530836b481cb.png",
				"freight_template_id": 15,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			]],
			"banner": "http://nos.netease.com/yanxuan/a0c91ae573079830743dec6ee08f5841.jpg",
			"height": 155
		], [
			"id": 1019000,
			"name": "志趣",
			"goodsList": [[
				"id": 1135050,
				"category_id": 1019000,
				"is_on_sale": 1,
				"name": "多功能封闭式环保除菌猫砂盆",
				"goods_number": 100,
				"sell_volume": 998,
				"keywords": "",
				"retail_price": "179",
				"min_retail_price": 179,
				"cost_price": "100",
				"min_cost_price": 100,
				"goods_brief": "银离子吸附脏东西，多功能",
				"goods_desc": "<p><img src=\"http://yanxuan.nosdn.127.net/6f9856cef886e1578d0ba1daf06aadbd.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fcce9b61ea1af9bda9b7638ac4034c97.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4a84c927116f566c078b1985bb60fbb3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b5b516f6bb5b16ef1920ed1588bfa4cd.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7ded3831f2446017a468b0e104956eb2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/99ec99cd3ea915ed37ed6f59c8e62adb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f033ec89ba23c4fc3349466e6136e47c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6e03379ee27dc7d2f98196552e7d7b47.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e131e7d59c392a7eb533cdb6e777d962.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ab4cda6c3c129220a5791355e93e11b3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9f18edc32227e8b3f5617daab586a18e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bf4f9a496337fcc8f3bd2c9532d2f7ca.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/cd8cfbb3d0b39486d424aefddf12e555.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/2488258ec7b15f6bc8b96189a886fe7d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/77529a9626a81a9df8c5c19a41ac96c1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3439fb6332c9ee7012d2d5994edf74f2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a6961a8a1e1398e172781617e118ae83.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/732ac476b83f419320492849c31fc848.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8a4e368f99a04df29d2f250bc0736972.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b52a0c19568d8f765a29dd79283d6443.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9cbb767f8be804a262ed7409636824a6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5de865a2825e13b1ccd80de36a5765eb.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9a4920ac6fe97730ee089db970715f2e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d91316b5b392fe680106a8f7c61a2f65.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3819f3548b43b26c4f680e6e6c7b82c0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c960601200e839c17631082df5e4a6b4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1e9a2bb9e1f25bc89d624936a6bd8280.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b9194503234882f2b110c99c82c9dbd1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/238d2ceebbeb8ebe2e863fda104c54e2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c2550765a86158c2549b3cc4c7156188.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1d3c324c167a224c7c1117ae94a81829.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/73455e610f8852b2bb574b3bd214bad3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18de3a806a61d06e4865b6e49a64adf7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4514f12874e8a3b4ad0441519eaf052a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ccec28a161de79a21b904b10c86e6634.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/31b2c3eca2dd71b21724359790d6d008.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b28254f776f2f254aea6f00a3f1ddbe8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5fe6c65035197a77e9887dc27b2f70d8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ad39f94fa16203561b40e70ed7a87401.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4554c1ed5868d311541aae8927b002ce.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a370d559bf323cc0bae895037619a42f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/eb1118c62ab6360fa5735093e6cdf5ac.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ce5994478404bf511510010a08ac82df.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a58329a1df06c244f4a06bd589e1968f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a2c8414cfd43a130f7678a43a55f74af.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7ec67f519962351decb3790096261449.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/eeaef8ff18b2c981b6c911891ffb2f3b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/665714ca119b377e1b499e6840fe39d7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bbc1aaea92b9c66bea37412ea46786d8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/08b4a552c1643710048eac58816a03e1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/46cf57c497b2b4224019ec21fcadebab.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8d8cdb00adca3271a807e663af0ad5d9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/675ff22efc7509f1afa7b60abf1aa97d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/979e353f2676974f747048cdc8d43071.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/36e5b65856b2bc83db180d2e3efd3ba8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8c3c35bacfa560be63f9a993578d1368.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b47efb83708e09fafe58255e719641c3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/989b6751ac604e61d974530e288c1559.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b4397c327aa439dafe4a353219cb9666.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/69452ed503f60fa2eebe1231e76bac18.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/008516851ad05fc88068d1a5076d4f62.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f3c21358c9003d0dffdced74729754a3.jpg\"></p>",
				"sort_order": 2,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/155edc218e5712837b8669b12e0f48a7.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/366f3f3f0e8971c8cf871e2b55b74ff2.png",
				"freight_template_id": 15,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			], [
				"id": 1130039,
				"category_id": 1019000,
				"is_on_sale": 1,
				"name": "房型封闭式凉感条纹宠物窝",
				"goods_number": 100,
				"sell_volume": 312,
				"keywords": "",
				"retail_price": "89",
				"min_retail_price": 89,
				"cost_price": "40",
				"min_cost_price": 40,
				"goods_brief": "日式面料，四季可用",
				"goods_desc": "<p><img src=\"http://yanxuan.nosdn.127.net/2e60bc695ec19eb5f68c0d323f315986.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/47f959a579fccb31dd8f43f378a4642c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fd69887384e3a47c48f3a96a89dbc860.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/df0f383d44bf5a434d495554dada9d68.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5666f8baee48295b10326d7c4bbaf5f6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8d67ba2c88a98f4c128b18a48d0813f0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/08de797e6dcb46246731e4f27d53aed4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/835ab4094a214b31c9a38690188c6f18.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/00710ccdd9545eff77c5b48ffc12ce35.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fe0e441beb8db4ebd61f88f8666bb01d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/96523bb2d102ca5bd4c039625ac787ea.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e4aafa566ae813f6209b7cb191bac318.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/cc73bce3f2615a7d4940df0069d2a371.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d6b9fafaaa8f407c1e95037632a6b527.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f047a0b9ef5adb0cb3d279d943ba67ea.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8fd45ae1c96714c2f708f6c90a820dc3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1184cc1635b79cea484683d89e1dc107.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1c4bcf07a8c152006aeea1dbcef9f6e0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8fc099d360d6eba103ab35fb79ad1989.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/411b4738ea1a8676bb02f6dd150455b4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9c80b63dad938193a29d5d79a2b279b9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6c479654ce5c8308ffa850b2baabff4f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3bb717930ebf0bc908f37b6e25f76a90.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8841053574ac6ec5123c8ecc7f31016a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/87f2f7b4c08bec75c6d334f08170e504.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0bb5731d33e08ed46b42914e287aa130.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0a1b7ec870bc558e8d58827832d4cc2f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/67626b790405d8bda762bb7b71abad72.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e397ec0bc1e138759b77dd17b466b565.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5541fc204f73f4e2b5800ca1e20c74ca.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4c87ed19400338de755d8c9a263781b7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/317595b21489f43d0bdcdf04b498b453.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/cfe2a70989e10e1c526193807b2a5156.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d4776216f6490fedc6a660d787895915.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/aa976e6d0460daa3f22d4a9ddbb85655.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0554b5974d419d2d398bb4abfc3c58fd.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d2cc642bcb1f97d742bc6a2e51ec4278.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/79c4e4d513f40805a51dd7bf31e34cdf.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/155589dad685c2dac737bd52bf6578d6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6e25b510ff8c50488509aba0555dcabf.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e0ab6ec0bc3217f56490672543bcbe3b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a3541f1cf3c2d8a10d52dea8fc804562.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9bf9a5ece1d5e1b3fb6002fc4a33c12b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/fccf95a9a92cc4ef478ec43cb5ccc5c1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b8cd32d1dacedd385d80fc2a9a186306.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b751308cbb3ebbaa919994889bedd8ad.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0a9a8fa5c7530885ac59b14fde6e43f8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3860b5c02fc81d76da85d952ea6afd49.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3dc6361930475090bb701f0b0594e89b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/236950202693dd70bd6d0d441a5b1887.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/0968d338c1dd5b630c8d9c36bcc6bfc5.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6160c1cf012984fdfce72143a4229bca.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7c92c4834d33b34a133365c6b790270a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/83dad41c5b7b09e973d6e60c18e8b3ac.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/27ef4ef4212b48031865533d538db321.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e57d45af1e54c2f408aa96493e7e15b9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8c9ebfcc5539e4cee23b77a0613d1208.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/959e2bbd613279b43e42046acf3408f6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5b9293e40c5ff4d38feb97267c5fe6f9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bdb0cbd93e4b12d14d93be7d7263f8ab.jpg\"></p>",
				"sort_order": 3,
				"is_index": 1,
				"is_new": 0,
				"goods_unit": "件",
				"https_pic_url": "http://yanxuan.nosdn.127.net/54e00407a82fab70a369c79c79e38b51.jpg",
				"list_pic_url": "http://yanxuan.nosdn.127.net/03c73e1f1ce1d2365e83b3230e507030.png",
				"freight_template_id": 14,
				"freight_type": 0,
				"is_delete": 0,
				"has_gallery": 1,
				"has_done": 1
			]],
			"banner": "http://nos.netease.com/yanxuan/72de912b6350b33ecf88a27498840e62.jpg",
			"height": 155
		]]
	]
];
*/



