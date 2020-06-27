package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.entity.GoodsContent
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.util.UnitCmp
import com.jeancoder.scm.ready.util.UnitUtil

JCLogger logger = JCLoggerFactory.getLogger('');

def id = JC.request.param('id');

def domain = JC.request.get().getServerName();
def port = JC.request.get().getServerPort();
if(port!=80) {
	domain = domain + ':' + port;
}
def sch = JC.request.get().getSchema();
def prefix = sch + domain + '/img_server/';

GoodsInfo g = null;
if(id && id!='0') {
	g =  GoodsService.INSTANCE().get(id);
	if(g==null) {
		return ProtObj.fail(110001, '商品未找到');
	}
}

def info = null;
String cat_id = '';
if(g!=null) {
	def goods_content = '';
	def has_gallery = 0;
	//查找商品详情和图片
	GoodsContent g_c = GoodsService.INSTANCE().get_content(id);
	List<GoodsImg> g_imgs = GoodsService.INSTANCE().find_goods_imgs(id, null);
	if(g_c) {
		goods_content = g_c.content;
	}
	if(g_imgs) {
		has_gallery = 1;
	}
	
	//查找当前商品对应的顶级分类
	List<String> goods_cat_ids = GoodsService.INSTANCE().get_goods_cats(g.id, '100');	//固定为商品
	if(goods_cat_ids) {
		cat_id = goods_cat_ids.get(0).split(',')[0];
	}
	
	def unit_name = '';
	UnitCmp unit_obj = UnitUtil.convert_by_code(g.unit);
	if(unit_obj!=null) {
		unit_name = unit_obj.name;
	}
	def retail_price = '';
	def cost_price = '';
	if(g.goods_price) {
		retail_price = g.goods_price/100;
	}
	if(g.cost_price) {
		cost_price = g.cost_price/100;
	}
	info = ["id":g.id,"category_id":cat_id,"is_on_sale":1,"name":g.goods_name,"goods_number":100,"sell_volume":1533,"keywords":"",
		"retail_price":retail_price,"min_retail_price":retail_price,"cost_price":cost_price,"min_cost_price":cost_price,
		"goods_brief":g.goods_remark,"goods_desc":goods_content,"sort_order":1,"is_index":1,"is_new":0,"goods_unit":unit_name,
		"https_pic_url":g.goods_picturelink,"list_pic_url":g.goods_picturelink_middle,"freight_template_id":g.ftpl,"freight_type":g.freepost,
		"is_delete":0,"has_gallery":has_gallery,"has_done":1]
}


return ProtObj.success([info:info, "category_id":cat_id, 'prefix':prefix]);

/*
return ["errno":0,"errmsg":"",
	"data":["info":["id":1181000,"category_id":1005000,"is_on_sale":1,"name":"母亲节礼物-舒适安睡组合","goods_number":100,"sell_volume":1533,"keywords":"","retail_price":"999","min_retail_price":999,"cost_price":"900","min_cost_price":900,"goods_brief":"安心舒适是最好的礼物","goods_desc":"<p><img src=\"http://yanxuan.nosdn.127.net/3ddfe10db43f7df33ba82ae7570ada80.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7682b7930b4776ce032f509c24a74a1e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e0bb6a50e27681925c5bb26bceb67ef4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ba63b244c74ce06fda82bb6a29cc0f71.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3c7808c3a4769bad5af4974782f08654.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/71798aaac23a91fdab4d77e1b980a4df.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c88cbb2dd2310b732571f49050fe4059.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5dfdcd654e0f3076f7c05dd9c19c15ea.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bd55d6ef7af69422d8d76af10ee70156.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/bae571b22954c521b35e446d652edc1d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e709c4d9e46d602a4d2125e47110f6ae.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/83e41915035c418db177af8b1eca385c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/f42c561e6935fe3e0e0873653da78670.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8317726fbae80b98764dc4c6233a913e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ba904b7c948b8015db2171435325270f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4969c73d0d8f29bffb69529c96ca4889.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d80b9b8c5c31031d1cd5357e48748632.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3fe79bdae40662a7b1feed3179d3dd1f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/79eef059963b12479f65e782d1dca128.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e5a8f64f4297ccc01b41df98b0f252c8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a940b9e9525c4861407e4c3b07b02977.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/224b8b81cbe12e4ad060a50f1e26601a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/85e151647452fad718effb7b1adc18e2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d47444ff3bb9dc0aa4ab1f9b84d83768.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/136262743f0c849cc0c55c8e7963dd7e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/331a97cbaff5b25a3b08ed7cdfe29df9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/89b450aa0a8afe1db566dcad926f1fe8.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c1cf94f13b7280a97e751cebe573fa78.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1822c23def83b77e7607c24237eeec74.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/2af234312b3914d6d0bc316f92134614.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c4f8ab2b3813275d954a8bedcf902d26.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/42f18842ff0c92ed849c4401ae47bb61.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a8ea64a35799e50ab31ecb65345fe8f4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/18759fa90cd153bdd744280807c3c155.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/431f00d068a8e747959deb3b7bdd495a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5bd3b44f1f4c627bfa39f7809e866ec6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/7fc36778fe2f6129b9c26e8298c5be7e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c568432e3d5ab6786cd9dcae8008891b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ec82ff5aecafa48807117da68cce2ce9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b8eccbed570da595e6f8a71ed4abc42c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/9cae1fed6ecefcd61435fe6e2c700fd6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e306a418f82777399f5e88b93cca22db.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a66d717084e23864ce079f936557709f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6ae06c6505cdbf87a0210fe3b8727d5f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/58ac2086725b0ba2fe800195f274a0b4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5e2e9d9eb099647fbe041ec6645ac034.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8154357c0fab82bd4e67cda9aaa128c0.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4325763b738ec3183ecf0d82b2b28e32.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/06d8ea9d10035a00f26c5c52cc601ca7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/499f30b9e69b5dddf3db36f105756111.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ed7e1733d54e711a560edb3a76f1a60c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b6474347eebdb917d2e827fd526dd01c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b2c0691f9204c5ebc94b4ff678919ca7.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/b4811e702a6884a9251d7cc9e3b06b6f.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d518783c054695acf329e81d597fdec3.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/835ce09e785cca635c176008975053a1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/769af780de81a302c0a3b03ed8e6c528.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/da34f99daf9141f0fe56a766461b8485.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/d7c9cd8722a2f9a78e158ce02ec5d4f2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/09ea18953884b15227819e326103462b.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5ef728213983842edf1aec27b2c1f5b6.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/95409f2a884dcfeaabfe5e61fcf9ec37.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/6807836dc2a940ba56ea10c7a63b14c9.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e1d976d06853e7a0e6c9cc4ab484ac8a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/47f5673dec5005092f6d897d6335966c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1b0109abd0e6a0d13fa2423a96c1167e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/916111a8f94cc0bd39375b3dcac14e35.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c1360df3d6b703c5df9b2f47a2a3d12e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/1d5a31eb93ef873a165993bd99f29df1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/997a48948b89dd7261ed5a59ba884f45.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/eb96d9689735c9f4019ebf76da43b2b2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a92cf2172e6cafe080e4511205568d79.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c9e94570428f197292bb3f43609963f5.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/37145f06cce747311692ad7f276645db.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c9a698b71ed911364fc6f243006c241c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e89db969711efaa441c43d6b90498a0c.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3803bb1a18229562f18943512b1d3524.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/235cbb5be905ac2b87e7e8f7c8d90144.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/3e38435b3fdbae4ee80b83995592901e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/8ceb7cd3231585da60a74dd2c1aa9015.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e151e225c2e30aab7ccf086094381577.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/363c19306953daf10968f4aa86617997.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/4237a392cf2e69b110ad4ecf35e44059.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/da8ab35ada2dfe55006db01efa96e51a.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/aa1d4fd00b7879db3f1051dc6d16aa87.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/302a8f2d997ff22bedcd837672cdafc2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a39ff68c00522aef0472402958a334d2.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/86ccd0eb677c8b552398869d11a8917e.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/a6d0ede352da947060d912d903646a5d.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e6a118bf95bdb61891409d25f193e9c4.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c214066e9bf65d60bcebd691b5b1cbc1.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/c301559ba3ee280bcbf2fc4269bfa9ca.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/573748f5c12ecb4515ba00a7b6e981dc.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/27bcc8bf512a7e6f994a9683b3deea82.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/e22a4507fd1e4b5ef859035e857ae419.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/27b07b4ca709c33ad71b368f87781307.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/ef31eb48bcb133728bffda7e1239b592.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/5f49aaaca59c0733ec92f100d01bc0af.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/818889261deb75044e1018ec53485d85.jpg\"></p><p><img src=\"http://yanxuan.nosdn.127.net/200369f023243e2faeb18a2a0a352ef1.jpg\"></p>","sort_order":1,"is_index":1,"is_new":0,"goods_unit":"件","https_pic_url":"http://yanxuan.nosdn.127.net/6f3e94fa4b44341bda5a73224d605896.jpg","list_pic_url":"http://yanxuan.nosdn.127.net/1f67b1970ee20fd572b7202da0ff705d.png","freight_template_id":19,"freight_type":0,"is_delete":0,"has_gallery":1,"has_done":1],"category_id":1005000]
];
*/

