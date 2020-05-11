package com.jeancoder.scm.entry.incall.api.index

import com.jeancoder.scm.ready.incall.api.ProtObj

//return '{"errno":0,"errmsg":"","data":{"user":622,"goodsOnsale":48,"timestamp":1588860312,"orderToDelivery":3}}';


def ret_data = ['user':622, goodsOnsale:48, timestamp:Calendar.getInstance().getTimeInMillis(), orderToDelivery:3];

return ProtObj.success(ret_data);