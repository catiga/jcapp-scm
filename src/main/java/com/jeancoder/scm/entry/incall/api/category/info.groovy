package com.jeancoder.scm.entry.incall.api.category

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService

def cat_id = JC.request.param('id');

Catalog cat = CatalogService.INSTANCE().get(cat_id);
if(cat==null) {
	return ProtObj.fail(110001, '分类未找到');
}

def data = ["id":cat.id,"name":cat.cat_name_cn,"keywords":cat.cat_name_en,"front_desc":cat.cat_info,"parent_id":cat.parent_id,"sort_order":cat.seq,
	"show_index":cat.seq,"is_show":cat.cat_show,"icon_url":cat.cat_icon,"img_url":cat.cat_icon,"level":"L1","front_name":cat.cat_name_cn,"p_height":155,"is_category":1,"is_channel":1];

return ProtObj.success(data);

/*
return ["errno":0,"errmsg":"",
	"data":["id":1005000,"name":"居家","keywords":"","front_desc":"回家，放松身心","parent_id":0,"sort_order":1,"show_index":1,"is_show":1,"icon_url":"http://yanxuan.nosdn.127.net/a45c2c262a476fea0b9fc684fed91ef5.png","img_url":"http://nos.netease.com/yanxuan/f0d0e1a542e2095861b42bf789d948ce.jpg","level":"L1","front_name":"回家，放松身心","p_height":155,"is_category":1,"is_channel":1]];
*/
