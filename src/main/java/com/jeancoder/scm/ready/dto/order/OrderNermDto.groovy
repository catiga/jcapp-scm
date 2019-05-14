package com.jeancoder.scm.ready.dto.order

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.scm.ready.entity.OrderNerm

class OrderNermDto {
	
	BigInteger id;
	
	BigInteger order_id;
	
	String caller_num;
	
	String table_num;
	
	String room_num;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	public  OrderNermDto() {};
	
	public  OrderNermDto(OrderNerm nerm) {
		if (nerm == null) {
			return;
		}
		this.order_id = nerm.order_id
		this.caller_num = nerm.caller_num
		this.table_num = nerm.table_num
		this.room_num = nerm.room_num
		this.order_id = nerm.order_id
	}
}
