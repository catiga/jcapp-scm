package com.jeancoder.scm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem

def oss = JC.internal.param('oss')?.toString()?.trim();

def sid = JC.internal.param('sid')?.toString()?.trim();
def pid = JC.internal.param('pid')?.toString()?.trim();

println "oss"+   oss
println "sid"+  sid
println "pid"+  pid


def params = [];
def sql = 'select * from OrderInfo where flag!=? and pid=?';
params.add(-1); params.add(pid);

if(sid) {
	sql += ' and store_id=?';
	params.add(sid);
}

oss = oss?.split(",");
if(oss) {
	sql += ' and oss in (';
	for(x in oss) {
		sql += '?,';
		params.add(x);
	}
	sql = sql.substring(0, sql.length() - 1) + ')';
}

sql += ' AND to_days(a_time)= to_days(now())  order by a_time asc';
println "sql_"+  sql

List<OrderInfo> order_list = JcTemplate.INSTANCE().find(OrderInfo, sql, params.toArray());
if(order_list) {
	sql = 'select * from OrderItem where flag!=? and order_id in (';
	def param_items = []; param_items.add(-1);
	for(x in order_list) {
		sql += '?,';
		param_items.add(x.id);
	}
	sql = sql.substring(0, sql.length() - 1) + ') order by order_id asc';
	
	List<OrderItem> items = JcTemplate.INSTANCE().find(OrderItem, sql, param_items.toArray());
	if(items) {
		for(x in order_list) {
			def order_details = [];
			for(y in items) {
				if(x.id==y.order_id) {
					order_details.add(y);
				}
			}
			x.items = order_details;
		}
	}
}

return SimpleAjax.available('', order_list);




