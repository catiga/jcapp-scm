package com.jeancoder.scm.entry.incall.api.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.incall.api.eto.GoodsWithSale
import com.jeancoder.scm.ready.util.GlobalHolder

def domain = JC.request.get().getServerName();
def port = JC.request.get().getServerPort();
if(port!=80) {
	domain = domain + ':' + port;
}
def sch = JC.request.get().getSchema();
def prefix = sch + domain + '/img_server/';

def pn = JC.request.param('page');
def ps = JC.request.param('size');
try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
try {
	ps = Integer.valueOf(ps);
	if(ps<1) {
		ps = 10;
	}
}catch(any){
	ps = 10;
}
JcPage<GoodsInfo> page = new JcPage<GoodsInfo>();
page.pn = pn;
page.ps = ps;
//查找在售商品
def pid = GlobalHolder.proj.id;
def params = [];

def sql = 'select a.*, b.id as on_sale from GoodsInfo a left join GoodsForSale b on a.id=b.g_id and a.proj_id=b.pid and b.flag!=? and b.tycode=? where a.flag!=? and a.proj_id=?'
params.add(-1); params.add('100'); params.add(-1); params.add(pid);

page = JcTemplate.INSTANCE().find(GoodsInfo, page, sql, params.toArray());

def page_obj = [count:page.totalCount, totalPages:page.totalPages, pageSize:page.ps, currentPage:page.pn];

def g_data = [];
if(page.result) {
	for(x in page.result) {
		def is_on_sale = false;
		if(x.on_sale!=null) {
			is_on_sale = true;
		}
		def img_url = x.goods_picturelink;
		if(img_url) {
			img_url = prefix + img_url;
		}
		def goods_1 = ["id": x.id,"name": x.goods_name,"goods_brief": x.goods_material,"goods_desc": x.goods_remark,
			"retail_price": x.goods_price,"min_retail_price": x.goods_price,"cost_price": x.cost_price,"min_cost_price": x.cost_price,
			"is_on_sale": is_on_sale,"goods_number": 22, "sell_volume": 2923,"keywords": "",
			"sort_order": 1,"is_index": true,"is_new": 1,"goods_unit": x.unit,"https_pic_url": img_url,
			"freight_template_id": x.ftpl,"freight_type": x.freepost,"is_delete": 0,"has_gallery": 1,"has_done": 1,
			"category_id": 1005000,"category_name": "居家"];
		
		g_data.add(goods_1);
	}
}
page_obj['data'] = g_data;

return ProtObj.success(page_obj);


