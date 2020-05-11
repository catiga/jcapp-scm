package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
def page = JC.request.param('page');

return ["errno":0,"errmsg":"","data":["count":2,"totalPages":1,"pageSize":10,"currentPage":1,"data":[["id":10214,"user_id":1528,"goods_id":1009024,"goods_sn":"1234","product_id":251,"goods_name":"日式和风懒人沙发","goods_aka":"懒人椅","goods_weight":2,"add_price":0.2,"retail_price":0.2,"number":1,"goods_specifition_name_value":"蓝色","goods_specifition_ids":"77","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-04-22 17:41:09","is_fast":1,"is_delete":1],["id":10213,"user_id":1528,"goods_id":1009024,"goods_sn":"1234","product_id":251,"goods_name":"日式和风懒人沙发","goods_aka":"懒人椅","goods_weight":2,"add_price":0.2,"retail_price":0.2,"number":1,"goods_specifition_name_value":"蓝色","goods_specifition_ids":"77","checked":1,"list_pic_url":"http://yanxuan.nosdn.127.net/149dfa87a7324e184c5526ead81de9ad.png","freight_template_id":17,"is_on_sale":1,"add_time":"2020-04-22 17:40:06","is_fast":1,"is_delete":1]]]];
