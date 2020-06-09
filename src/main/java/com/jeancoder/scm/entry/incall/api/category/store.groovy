package com.jeancoder.scm.entry.incall.api.category

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

def id = JC.request.param('id');
def keyword = JC.request.param('keyword');
def name = JC.request.param('name');
def front_name = JC.request.param('front_name');
def front_desc = JC.request.param('front_desc');

def parent_id = JC.request.param('parent_id');
def sort_order = JC.request.param('sort_order');
def show_index = JC.request.param('show_index');
def is_show = JC.request.param('is_show');
def icon_url = JC.request.param('icon_url');
def img_url = JC.request.param('img_url');		//暂时用不到
def level = JC.request.param('level');
def p_height = JC.request.param('p_height');
def is_category = JC.request.param('is_category');
def is_channel = JC.request.param('is_channel');

Catalog cat = null;
def update = true;
if(id && id!='0') {
	cat = CatalogService.INSTANCE().get(id);
	if(cat==null) {
		return ProtObj.fail(110001, '分类未找到');
	}
} else {
	cat = new Catalog();
	cat.proj_id = GlobalHolder.proj.id;
	update = false;
}
cat.cat_name_en = keyword;
cat.cat_name_cn = name;
cat.cat_info = front_name;

try {
	cat.seq = Integer.valueOf(sort_order);
}catch(any) {} 

cat.cat_content = front_desc;
cat.cat_show = is_show=='0'?0:1;
cat.cat_icon = icon_url;

Catalog parent = null;
if(parent_id && parent_id!='0') {
	parent = CatalogService.INSTANCE().get(parent_id);
}
if(parent) {
	cat.parent_id = parent.id;
} else {
	cat.parent_id = null;
}
cat.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
if(update) {
	JcTemplate.INSTANCE().update(cat);
} else {
	JcTemplate.INSTANCE().save(cat);
}

def data = ["id":cat.id,"name":cat.cat_name_cn,"keywords":cat.cat_name_en,"front_desc":cat.cat_info,"parent_id":cat.parent_id,"sort_order":cat.id,
	"show_index":cat.seq,"is_show":cat.cat_show,"icon_url":cat.cat_icon,"img_url":cat.cat_icon,"level":"L1","front_name":cat.cat_name_cn,"p_height":155,"is_category":1,"is_channel":1];

return ProtObj.success(data);

//return ["errno":0,"errmsg":"","data":["id":1005000,"name":"居家","keywords":"","front_desc":"回家，放松身心","parent_id":0,"sort_order":1,"show_index":1,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/a45c2c262a476fea0b9fc684fed91ef5.png","img_url":"http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg","level":"L1","front_name":"回家，放松身心","p_height":155,"is_category":1,"is_channel":1]];
