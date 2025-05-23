package com.jeancoder.scm.entry.incall.ampi.catalog

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogIndexService
import com.jeancoder.scm.ready.util.GlobalHolder

//首先获取推荐到首页的分类
def dscs = JC.request.param('dscs');	//前端传过来的要获取的访问分类的渠道
def pid = GlobalHolder.proj.id;

if(!dscs) {
	dscs = 'home_index';
}

List<Catalog> channels = CatalogIndexService.INSTANCE.find_dsc_catalogs(pid, dscs, null);
def channel = [];
if(channels) {
	for(x in channels) {
		def t_obj = [id:x.id, name:x.cat_name_cn, keywords:x.cat_name_en, front_desc:x.cat_info, sort_order:x.seq, show_index:x.seq, is_show:x.cat_show,
			icon_url:x.cat_icon, img_url:x.cat_icon, level:1, front_name:x.cat_name_cn, p_height:155, is_category:1, is_channel:1];
		channel.add(t_obj);
	}
}

return ProtObj.success([categoryList:channel]);

/*
return ["errno":0,"errmsg":"",
	"data":[
		"categoryList":[
			["id":1005000,"name":"居家","keywords":"","front_desc":"回家，放松身心","parent_id":0,"sort_order":1,"show_index":1,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/a45c2c262a476fea0b9fc684fed91ef5.png","img_url":"http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg","level":"L1","front_name":"回家，放松身心","p_height":155,"is_category":1,"is_channel":1],
			["id":1005001,"name":"餐厨","keywords":"","front_desc":"厨房","parent_id":0,"sort_order":2,"show_index":2,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/ad8b00d084cb7d0958998edb5fee9c0a.png","img_url":"http://nos.netease.com/yanxuan/88855173a0cfcfd889ee6394a3259c4f.jpg","level":"L1","front_name":"爱，囿于厨房","p_height":155,"is_category":1,"is_channel":1],
			["id":1008000,"name":"配件","keywords":"","front_desc":"配角，亦是主角","parent_id":0,"sort_order":3,"show_index":3,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/11abb11c4cfdee59abfb6d16caca4c6a.png","img_url":"http://nos.netease.com/yanxuan/935f1ab7dcfeb4bbd4a5da9935161aaf.jpg","level":"L1","front_name":"配角，亦是主角","p_height":155,"is_category":1,"is_channel":1],
			["id":1012000,"name":"杂货","keywords":"","front_desc":"解忧，每个烦恼","parent_id":0,"sort_order":4,"show_index":7,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/c2a3d6349e72c35931fe3b5bcd0966be.png","img_url":"http://nos.netease.com/yanxuan/a0c91ae573079830743dec6ee08f5841.jpg","level":"L1","front_name":"解忧，每个烦恼","p_height":155,"is_category":1,"is_channel":1],
			["id":1019000,"name":"志趣","keywords":"","front_desc":"爱好，点缀生活","parent_id":0,"sort_order":5,"show_index":9,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/7093cfecb9dde1dd3eaf459623df4071.png","img_url":"http://nos.netease.com/yanxuan/72de912b6350b33ecf88a27498840e62.jpg","level":"L1","front_name":"周边精品，共享热爱","p_height":155,"is_category":1,"is_channel":1]
		]
	]
];
*/
