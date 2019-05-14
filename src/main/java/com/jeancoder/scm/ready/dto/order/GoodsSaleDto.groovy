package com.jeancoder.scm.ready.dto.order

import java.util.List

class GoodsSaleDto {
	String order_no;
	Long pay_time;
	String store_name;
	String pay_type;
	String member_no;
	String original_cost;
	String pay_amount;
	String preferential_amount;
	BigInteger ouid;
	List<OrderItemDto> items;
}
