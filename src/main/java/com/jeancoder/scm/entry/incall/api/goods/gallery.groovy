package com.jeancoder.scm.entry.incall.api.goods

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.service.GoodsService

def goods_id = JC.request.param('goods_id');
def url = JC.request.param('url');
def ct = JC.request.param('ct');

GoodsInfo goods = GoodsService.INSTANCE().get(goods_id);
if(goods==null) {
	return ProtObj.fail(110001, '商品未找到');
}

if(!url) {
	return ProtObj.fail(210001, '商品图片为空');
}

GoodsImg img = new GoodsImg();
img.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
img.content_type = ct;
img.ct_code = 'image';
img.flag = 0;
img.goods_id = goods.id;
img.img_type = '0000';
img.img_url = url;
img.tycode = '100';

JcTemplate.INSTANCE().save(img);

return ProtObj.success(1);
