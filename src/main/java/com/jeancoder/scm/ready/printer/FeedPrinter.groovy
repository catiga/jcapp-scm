package com.jeancoder.scm.ready.printer

import groovy.beans.ListenerList

import java.text.SimpleDateFormat

import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem

class FeedPrinter {
	
	static SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
	static void main(def ar) {
		
	}

	def get_printer_content(OrderInfo order, List<OrderItem> items) {
		def order_num = order.order_no;
		def buyerpoint = order.buyerpoint==null?"":order.buyerpoint;
		
		def order_details = "";
		def order_remarks = "";
		def order_time="";
		def goods_smarttemplate = [];
		for(x in items) {
			def goods_name = x.goods_name==null?"":x.goods_name;
			def goods_num = x.buy_num==null?"":x.buy_num;
			def goods_sku_name = x.goods_sku_name==null?"":x.goods_sku_name;
			def x_bk = x.remark==null?"":x.remark;
			order_time = _sdf_.format(order.a_time==null?"":order.a_time);
			String detail =  """
				<!DOCTYPE html>
				<html>
				  <head>
				    <meta charset="utf-8">
				    <title>厨房打印</title>
				  </head>
				  <body style="width:105mm;font-family:微软雅黑;">
				    <div style="width:105mm;">
				      <div>
				        <div style="font-size:21pt;width:105mm"><span>单号：</span></div>
				        <span style="font-size:21pt;word-break:break-all;word-wrap:break-word;width:105mm">${order_num}</span>
				        <div>
				            <span style="font-size:21pt">桌号：</span>
				            <span style="font-size:21pt;word-break:break-all;word-wrap:break-word;width:105mm">${buyerpoint}</span>
				        </div>
				      </div>
				      <span>-------------------------------------------------------</span>
				      <div style="width:105mm">
				        <div style="float:left;width:65mm;">
				          <span style="font-size:21pt; text-align:left;">商品名称</span>
				        </div>
				        <div style="width:20mm;float:left;margin-left:7mm">
				          <span style="font-size:21pt; text-align:left;">数量</span>
				        </div>
				        <div style="width:65mm; float:left;">
				          <span style="font-size:21pt; text-align:left;word-break:break-all;word-wrap:break-word;">${goods_name}</span>
				        </div>
				        <div style="width:25mm;float:left;font-size:21pt;text-align:left;margin-left:7mm;">
				          <span>${goods_num}</span>
				        </div>
				        <div style="clear:both"></div>
				        <div style="margin-top:5mm">
				          <span style="font-size:21pt">备注：</span>
				          <span style="font-size:21pt;word-break:break-all;word-wrap:break-word;">${x_bk}</span>
				        </div>
				      </div>
				      <span style="margin-top:10px;">-------------------------------------------------------</span>
				      <div>
				        <span style="font-size:21pt">下单时间：</span>
				        <div style="width:105mm;font-size:21pt;word-break:break-all;word-wrap:break-word;">
				          ${order_time}
				        </div>
				      </div>
				    </div>
				  </body>
				</html>
			""";
			goods_smarttemplate.add(detail);
		}
		//商品
		return goods_smarttemplate;
	}
}
