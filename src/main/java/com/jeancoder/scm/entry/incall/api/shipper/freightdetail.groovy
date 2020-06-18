package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.FreightRule
import com.jeancoder.scm.ready.entity.FreightTpl
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def freight_id = JC.request.param('id');
FreightTpl template = JcTemplate.INSTANCE().get(FreightTpl, 'select * from FreightTpl where flag!=? and id=? and proj_id=?', -1, freight_id, GlobalHolder.proj.id);

//获取省份数据
SimpleAjax province_list = JC.internal.call(SimpleAjax, 'project', '/incall/data/city', null);

def freight = null;
def data = [];
def defaultData = [];
def had_default = false;
if(template) {
	freight = ["id":template.id,"name":template.ft_name,"package_price":template.package_price/100,"freight_type":template.freight_type,"is_delete":0];
	//查找模版对应的地区详细
	List<FreightRule> result = JcTemplate.INSTANCE().find(FreightRule, 'select * from FreightRule where flag!=? and ftpl=? order by ac asc', -1, template.id);
	if(result) {
		for(x in result) {
			if(x.ac=='0') {
				had_default = true;
				def fm = x.fm?x.fm/100:0;
				def cfm = x.cfm?x.cfm/100:0;
				def freemoney_value = x.freemoney_value?x.freemoney_value/100:0;
				def d_data = ["id":x.id,"template_id":x.ftpl,"is_default":1,"area":x.ac,"start":x.ff,"start_fee":fm,"add":x.cff,"add_fee":cfm,"free_by_number":x.freewn_value,"free_by_money":freemoney_value,"is_delete":0];
				defaultData.add(d_data);
			} else {
				String area_name = '';
				if(province_list && province_list.available) {
					if(x.ac) {
						for(y in x.ac.split(',')) {
							String y_name = '';
							for(z in province_list.data) {
								if(y==z['city_no']) {
									y_name = z['city_name'];
									break;
								}
							}
							if(y_name!='') {
								area_name = area_name + ',' + y_name;
							}
						}
					}
				}
				def fm = x.fm?x.fm/100:0;
				def cfm = x.cfm?x.cfm/100:0;
				def freemoney_value = x.freemoney_value?x.freemoney_value/100:0;
				def d_data = ["id":x.id,"template_id":x.ftpl,"is_default":0,"area":x.ac,"start":x.ff,"start_fee":fm,"add":x.cff,"add_fee":cfm,"free_by_number":x.freewn_value,"free_by_money":freemoney_value,"is_delete":0,"areaName":area_name];
				data.add(d_data);
			}
		}
	}
}

if(!had_default) {
	def fm = 0;
	def cfm = 0;
	def freemoney_value = 0;
	def d_data = ["id":0,"template_id":freight_id,"is_default":1,"area":'0',"start":1,"start_fee":0,"add":1,"add_fee":0,"free_by_number":0,"free_by_money":0,"is_delete":0];
	defaultData.add(d_data);
}

return ProtObj.success([freight:freight, data:data, defaultData:defaultData]);

/*
return ["errno":0,"errmsg":"",
	"data":[
		"freight":["id":15,"name":"中通","package_price":0,"freight_type":0,"is_delete":0],
		"data":[
			["id":66,"template_id":15,"is_default":0,"area":"10,11,12,13","start":1,"start_fee":5,"add":1,"add_fee":0,"free_by_number":0,"free_by_money":0,"is_delete":0,"areaName":"上海市,江苏省,浙江省,安徽省"],
			["id":68,"template_id":15,"is_default":0,"area":"3,4,14,15,16,17,18,19,20,2","start":1,"start_fee":8,"add":1,"add_fee":0,"free_by_number":0,"free_by_money":0,"is_delete":0,"areaName":"北京市,天津市,河北省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省"],
			["id":69,"template_id":15,"is_default":0,"area":"6,8,9,21,22,25,26,29,5,7,23,24,28,30,31","start":1,"start_fee":10,"add":1,"add_fee":0,"free_by_number":0,"free_by_money":0,"is_delete":0,"areaName":"山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,广西壮族自治区,海南省,重庆市,四川省,贵州省,云南省,陕西省,甘肃省,青海省,宁夏回族自治区"],
			["id":70,"template_id":15,"is_default":0,"area":"27,32","start":1,"start_fee":15,"add":1,"add_fee":8,"free_by_number":0,"free_by_money":0,"is_delete":0,"areaName":"西藏自治区,新疆维吾尔自治区"]
		],
		"defaultData":[
			["id":49,"template_id":15,"is_default":1,"area":"0","start":1,"start_fee":6,"add":1,"add_fee":0,"free_by_number":0,"free_by_money":0,"is_delete":0]
		]
	]
]
*/

