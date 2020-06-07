package com.jeancoder.scm.ready.util

import com.jeancoder.scm.ready.order.OrderConstants

class OrderUtil {

	static def dss = OrderDss._ss_;
	
	static String _create_ = '0000';
	
	static def order_text(String oss) {
		String oss_text = oss;
		if(oss==OrderConstants._order_status_create_) {
			oss_text = "创建";
		} else if(oss==OrderConstants._order_status_apply_refund_) {
			oss_text = '申请退款';
		} else if(oss==OrderConstants._order_status_cancel_) {
			oss_text = '已取消';
		} else if(oss==OrderConstants._order_status_closed_) {
			oss_text = '关闭';
		} else if(oss==OrderConstants._order_status_delivering_) {
			oss_text = '已发货';
		} else if(oss==OrderConstants._order_status_delivering_nopub_) {
			oss_text = '已发货';
		} else if(oss==OrderConstants._order_status_delivering_nopub_ok_) {
			oss_text = '已发货';
		} else if(oss==OrderConstants._order_status_drawback_ok_) {
			oss_text = '已退款';
		} else if(oss==OrderConstants._order_status_payed_) {
			oss_text = '已支付';
		} else if(oss==OrderConstants._order_status_oss_on) {
			oss_text = '支付成功出库失败';
		} else if(oss==OrderConstants._order_status_taked_) {
			oss_text = '已收货';
		}
		return oss_text;
	}
}
