package com.jeancoder.scm.entry.incall.api.category

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def parent = JC.request.param('pid');
//获取分类
def params = [];
def sql = 'select * from Catalog where flag!=? and proj_id=?';
params.add(-1);
params.add(GlobalHolder.proj.id);
if(parent) {
	sql += ' and parent_id=?';
	params.add(parent);
} else {
	sql += ' and parent_id is null';
}
List<Catalog> result = JcTemplate.INSTANCE().find(Catalog, sql, params.toArray());

def data = [];
if(result) {
	for(x in result) {
		def cat_show = x.cat_show==1? true: false;
		
		def c = ["id":x.id,"name":x.cat_name_cn,"keywords":x.cat_name_en,"front_desc":x.cat_info,"parent_id":x.parent_id,"sort_order":x.seq,"show_index":1,
			"is_show":cat_show,"icon_url":x.cat_icon,"img_url":x.cat_icon,"level":1,"front_name":x.cat_name_cn,"p_height":155,"is_category":true,"is_channel":true];
		
		data.add(c);
	}
}

return ProtObj.success(data);

/*
return ["errno":0,"errmsg":"",
	"data":[
		["id":1005000,"name":"居家","keywords":"","front_desc":"回家，放松身心","parent_id":0,"sort_order":1,"show_index":1,"is_show":true,"icon_url":"http://yanxuan.nosdn.127.net/a45c2c262a476fea0b9fc684fed91ef5.png","img_url":"http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg","level":1,"front_name":"回家，放松身心","p_height":155,"is_category":true,"is_channel":true],
		["id":1005001,"name":"餐厨","keywords":"","front_desc":"厨房","parent_id":0,"sort_order":2,"show_index":2,"is_show":true,"icon_url":"http://yanxuan.nosdn.127.net/ad8b00d084cb7d0958998edb5fee9c0a.png","img_url":"http://nos.netease.com/yanxuan/88855173a0cfcfd889ee6394a3259c4f.jpg","level":1,"front_name":"爱，囿于厨房","p_height":155,"is_category":true,"is_channel":true],
		["id":1008000,"name":"配件","keywords":"","front_desc":"配角，亦是主角","parent_id":0,"sort_order":3,"show_index":3,"is_show":true,"icon_url":"http://yanxuan.nosdn.127.net/11abb11c4cfdee59abfb6d16caca4c6a.png","img_url":"http://nos.netease.com/yanxuan/935f1ab7dcfeb4bbd4a5da9935161aaf.jpg","level":1,"front_name":"配角，亦是主角","p_height":155,"is_category":true,"is_channel":true],
		["id":1012000,"name":"杂货","keywords":"","front_desc":"解忧，每个烦恼","parent_id":0,"sort_order":4,"show_index":7,"is_show":true,"icon_url":"http://yanxuan.nosdn.127.net/c2a3d6349e72c35931fe3b5bcd0966be.png","img_url":"http://nos.netease.com/yanxuan/a0c91ae573079830743dec6ee08f5841.jpg","level":1,"front_name":"解忧，每个烦恼","p_height":155,"is_category":true,"is_channel":true],
		["id":1019000,"name":"志趣","keywords":"","front_desc":"爱好，点缀生活","parent_id":0,"sort_order":5,"show_index":9,"is_show":true,"icon_url":"http://yanxuan.nosdn.127.net/7093cfecb9dde1dd3eaf459623df4071.png","img_url":"http://nos.netease.com/yanxuan/72de912b6350b33ecf88a27498840e62.jpg","level":1,"front_name":"周边精品，共享热爱","p_height":155,"is_category":true,"is_channel":true],
		["id":1010000,"name":"服装","keywords":"","front_desc":"贴身的，要亲肤","parent_id":0,"sort_order":6,"show_index":4,"is_show":false,"icon_url":"http://yanxuan.nosdn.127.net/28a685c96f91584e7e4876f1397767db.png","img_url":"http://nos.netease.com/yanxuan/135113d6a43536b717063413fa24d69a.jpg","level":1,"front_name":"贴身的，要亲肤","p_height":0,"is_category":false,"is_channel":false],
		["id":1013001,"name":"洗护","keywords":"","front_desc":"亲肤之物，严选天然","parent_id":0,"sort_order":7,"show_index":5,"is_show":false,"icon_url":"http://yanxuan.nosdn.127.net/9fe068776b6b1fca13053d68e9c0a83f.png","img_url":"http://nos.netease.com/yanxuan/14bb4a29498a0f93a1ea001f26fea1dd.jpg","level":1,"front_name":"亲肤之物，严选天然","p_height":0,"is_category":false,"is_channel":false],
		["id":1011000,"name":"婴童","keywords":"","front_desc":"爱，从心开始","parent_id":0,"sort_order":8,"show_index":6,"is_show":false,"icon_url":"http://yanxuan.nosdn.127.net/1ba9967b8de1ac50fad21774a4494f5d.png","img_url":"http://nos.netease.com/yanxuan/8ab3c73fe90951a942e8b06d848f8743.jpg","level":1,"front_name":"爱，从心开始","p_height":0,"is_category":false,"is_channel":false],
		["id":1005002,"name":"饮食","keywords":"","front_desc":"好吃，高颜值美食","parent_id":0,"sort_order":9,"show_index":8,"is_show":false,"icon_url":"http://yanxuan.nosdn.127.net/c9280327a3fd2374c000f6bf52dff6eb.png","img_url":"http://nos.netease.com/yanxuan/9a29ef4f41c305a12e1459f12abd290f.jpg","level":1,"front_name":"好吃，高颜值美食","p_height":0,"is_category":false,"is_channel":false]
	]
];
*/


