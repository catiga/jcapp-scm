package com.jeancoder.scm.entry.incall.api.user

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.OrderUtil

def id = JC.request.param('id');
def pn = JC.request.param('page');
def ps = 10;
try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
JcPage<OrderInfo> page = new JcPage<OrderInfo>();
page.setPn(pn);
page.setPs(ps);

page = JcTemplate.INSTANCE().find(OrderInfo, page, 'select * from OrderInfo where flag!=? and buyerid=? order by a_time desc', -1, id);

def order_list = [];
SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');

for(x in page.result) {
	def o_obj = [:];
	o_obj['id'] = x.id;
	o_obj['order_sn'] = x.order_no;
	o_obj['order_status_text'] = OrderUtil.order_text(x.oss);
	o_obj['add_time'] = _sdf_.format(x.a_time);
	o_obj['actual_price'] = x.total_amount/100;
	o_obj['change_price'] = x.pay_amount/100;
	o_obj['freight_price'] = 0;
	
	//查找对应的具体商品
	List<OrderItem> order_items = JcTemplate.INSTANCE().find(OrderItem, 'select * from OrderItem where order_id=? and flag!=?', x.id, -1);
	def goodsCount = 0;
	def goods_list = [];
	if(order_items) {
		for(y in order_items) {
			goodsCount += y.buy_num;
			goods_list.add([goods_name:y.goods_name, goods_specifition_name_value:y.goods_sku_name, number:y.buy_num, list_pic_url:y.pic_url, retail_price:y.pay_amount/100, unit_price:y.unit_amount/100]);
		}
	}
	o_obj['goodsCount'] = goodsCount;
	o_obj['goodsList'] = goods_list;
	
	order_list.add(o_obj);
}

def data = [count:page.totalCount, totalPages:page.totalPages, pageSize:page.ps, currentPage:page.pn];
data['data'] = order_list;

return ProtObj.success(data);


