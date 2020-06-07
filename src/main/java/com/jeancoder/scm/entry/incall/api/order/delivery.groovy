package com.jeancoder.scm.entry.incall.api.order

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.DicExpress
import com.jeancoder.scm.ready.incall.api.ProtObj

def sql = 'select * from DicExpress where flag!=? order by id asc';

def result = JcTemplate.INSTANCE().find(DicExpress, sql, -1);

return ProtObj.success(result);

/*
return 
["errno":0,"errmsg":"",
	"data":[
		["id":1,"name":"顺丰速运","code":"SF","sort_order":1,"MonthCode":"5800278123","CustomerName":null,"enabled":1],
		
		["id":2,"name":"百世快递","code":"HTKY","sort_order":2,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":3,"name":"中通快递","code":"ZTO","sort_order":3,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":4,"name":"申通快递","code":"STO","sort_order":4,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":5,"name":"圆通速递","code":"YTO","sort_order":5,"MonthCode":null,"CustomerName":null,"enabled":1],
		
		["id":6,"name":"韵达速递","code":"YD","sort_order":6,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":7,"name":"邮政快递包裹","code":"YZPY","sort_order":7,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":8,"name":"EMS","code":"EMS","sort_order":8,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":9,"name":"天天快递","code":"HHTT","sort_order":9,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":10,"name":"京东物流","code":"JD","sort_order":10,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":11,"name":"全峰快递","code":"QFKD","sort_order":11,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":12,"name":"国通快递","code":"GTO","sort_order":12,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":13,"name":"优速快递","code":"UC","sort_order":13,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":14,"name":"德邦快递","code":"DBL","sort_order":14,"MonthCode":null,"CustomerName":null,"enabled":0],
		["id":15,"name":"顺丰特惠","code":"SF","sort_order":15,"MonthCode":"5800279123","CustomerName":null,"enabled":1]
	]
];
*/


