package com.jeancoder.scm.entry.incall.api.user

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

def ap_id = JC.request.param('id');
def pid = GlobalHolder.proj.id;

//提交订单总笔数
def orderSum = 0;
//完成订单总笔数
def orderDone = 0;
//消费总金额
def consumeAmount = 0;
//用户加入购物车的总数量
def cartSum = 0;

//统计总用户订单数
orderSum = JcTemplate.INSTANCE().get(Long, 'select count(id) from OrderInfo where flag!=? and buyerid=?', -1, ap_id);
//统计完成的订单数，即订单状态为非0000和9000的
orderDone = JcTemplate.INSTANCE().get(Long, 'select count(id) from OrderInfo where flag!=? and buyerid=? and oss!=? and oss!=?', -1, ap_id, '0000', '9000');
//统计消费总金额
consumeAmount = JcTemplate.INSTANCE().get(BigDecimal, 'select sum(pay_amount) from OrderInfo where flag!=? and buyerid=? and oss!=? and oss!=?', -1, ap_id, '0000', '9000');

def data = ["orderSum":orderSum,"orderDone":orderDone,"orderMoney":consumeAmount/100,"cartSum":cartSum];

return ProtObj.success(data);
