package com.jeancoder.scm.internal.goods

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsInfo

def g_no = JC.internal.param('g_no');	//同时搜索商品短码
def pid = JC.internal.param('pid');

//根据编码查询
def sql = 'select * from GoodsInfo where flag!=? and proj_id=? and (goods_out_no=? or goods_code=? or goods_id=?)';
List<GoodsInfo> result = JcTemplate.INSTANCE().find(GoodsInfo, sql, -1, pid, g_no, g_no, g_no);

return SimpleAjax.available('', result);