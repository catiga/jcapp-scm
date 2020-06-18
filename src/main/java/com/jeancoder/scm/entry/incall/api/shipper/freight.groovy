package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.FreightTpl
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

//查找运费模板设置
def sql = 'select * from FreightTpl where flag!=? and proj_id=?';

List<FreightTpl> result = JcTemplate.INSTANCE().find(FreightTpl, sql, -1, GlobalHolder.proj.id);
def data = [];
if(result) {
	for(x in result) {
		def pack_price = 0;
		if(x.package_price!=null) {
			pack_price = x.package_price/100;
		}
		def d = ["id":x.id,"name":x.ft_name,"package_price":pack_price,"freight_type":x.freight_type,"is_delete":0];
		data.add(d);
	}
}

return ProtObj.success(data);

/*
return ["errno":0,"errmsg":"",
	"data":[
		["id":14,"name":"生鲜速配","package_price":0,"freight_type":0,"is_delete":0],
		["id":15,"name":"中通","package_price":0,"freight_type":0,"is_delete":0],
		["id":17,"name":"包邮","package_price":0,"freight_type":0,"is_delete":0],
		["id":19,"name":"顺丰特惠","package_price":0,"freight_type":0,"is_delete":0],
		["id":20,"name":"康平产品模板","package_price":0,"freight_type":0,"is_delete":0],
		["id":21,"name":"顺丰生鲜速配","package_price":0,"freight_type":0,"is_delete":0]
	]
];
*/

