package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

def goods_id = JC.request.param('goodsId');

List<GoodsImg> goods_imgs = GoodsService.INSTANCE().find_goods_imgs(goods_id, null);
def galleryData = [];

def domain = JC.request.get().getServerName();
def port = JC.request.get().getServerPort();
if(port!=80) {
	domain = domain + ':' + port;
}
def sch = JC.request.get().getSchema();
def prefix = sch + domain + '/img_server/';

if(goods_imgs) {
	for(x in goods_imgs) {
		def gi = ["id":x.id,"url":prefix + x.img_url, type:x.img_type, ct:x.content_type];
		galleryData.add(gi);
	}
}

return ProtObj.success([galleryData:galleryData]);

/*
return ["errno":0,"errmsg":"",
	"data":
	[
		"galleryData":[
			["id":677,"url":"http://yanxuan.nosdn.127.net/355efbcc32981aa3b7869ca07ee47dac.jpg"],
			["id":678,"url":"http://yanxuan.nosdn.127.net/43e283df216881037b70d8b34f8846d3.jpg"],
			["id":679,"url":"http://yanxuan.nosdn.127.net/12e41d7e5dabaf9150a8bb45c41cf422.jpg"],
			["id":680,"url":"http://yanxuan.nosdn.127.net/5c1d28e86ccb89980e6054a49571cdec.jpg"]
		]
	]
]
*/
