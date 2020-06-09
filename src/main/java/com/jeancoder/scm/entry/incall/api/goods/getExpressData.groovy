package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.FreightTpl
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

//查找运费模版
List<FreightTpl> kd_result = JcTemplate.INSTANCE().find(FreightTpl, 'select * from FreightTpl where flag!=? and proj_id=? order by a_time desc', -1, GlobalHolder.proj.id);
def kd_data = [];
if(kd_result) {
	for(x in kd_result) {
		kd_data.add(["value":x.id + '',"label":x.ft_name]);
	}
}

//查找分类信息，只查找顶级分类
List<Catalog> cate_result = CatalogService.INSTANCE().find_by_parent(null);
def cate_data = [];
if(cate_result) {
	for(x in cate_result) {
		cate_data.add(["value":x.id + '',"label":x.cat_name_cn]);
	}
}

return ProtObj.success([kd:kd_data, cate:cate_data]);

/*
return ["errno":0,"errmsg":"",
	"data":[
		"kd":[
			["value":14,"label":"生鲜速配"],["value":15,"label":"中通"],["value":17,"label":"包邮"],["value":19,"label":"顺丰特惠"],["value":20,"label":"康平产品模板"],["value":21,"label":"顺丰生鲜速配"]
		],
		"cate":[
			["value":1005000,"label":"居家"],["value":1005001,"label":"餐厨"],["value":1005002,"label":"饮食"],["value":1008000,"label":"配件"],["value":1010000,"label":"服装"],["value":1011000,"label":"婴童"],["value":1012000,"label":"杂货"],["value":1013001,"label":"洗护"],["value":1019000,"label":"志趣"]
		]
	]
];
*/


