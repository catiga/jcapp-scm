package com.jeancoder.scm.entry.incall.api.index

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.scm.ready.incall.api.ProtObj

JCLogger logger = JCLoggerFactory.getLogger('');

def pindex = JC.request.param('pindex');

logger.info('pindex=' + pindex);

def ret_data = [:];
ret_data['newUser'] = 7;
ret_data['oldUser'] = 0;
ret_data['addCart'] = 6;
ret_data['addOrderSum'] = 0;
ret_data['payOrderNum'] = 0;
ret_data['payOrderSum'] = 0;

def old_user_list = [];
ret_data['oldData'] = old_user_list;

def new_user_list = [];
new_user_list.add(["id":1658,"nickname":"靜","name":"","username":"微信用户6f7a2962-dcaf-4847-a882-68d5136d98a7","password":"oLMM4435TSvbEp7nUMcc_rZyVD2c","gender":2,"birthday":0,"register_time":"2020-05-07 09:35:50","last_login_time":"2020-05-07 09:35:50","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKvH9eIPco0g6ib9Ae7FN5fBKVQibTyrMuJJfRwE2ibTIB1qPHiaKAvvv1u012P862yRWLzarnAmoIJFw/132","weixin_openid":"oLMM4435TSvbEp7nUMcc_rZyVD2c","name_mobile":0,"country":"Austria","province":"Vorarlberg","city":""]);
new_user_list.add(["id":1659,"nickname":"大茂","name":"","username":"微信用户cd3854bc-ca07-4b25-999f-8775ee6fed6b","password":"oLMM440OGfGtk7G8L7PvagrpCTJI","gender":1,"birthday":0,"register_time":"2020-05-07 10:05:54","last_login_time":"2020-05-07 10:05:54","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/g4wzd5sQZzBFn9ricxv9fDMlvxnETBwq7t4rRIm6vFGNh31X6Z5XoJnLNUr4sa7OJLuNlHu10zBPL0PgFHicCPXA/132","weixin_openid":"oLMM440OGfGtk7G8L7PvagrpCTJI","name_mobile":0,"country":"China","province":"Sichuan","city":"Chengdu"]);
new_user_list.add(["id":1660,"nickname":"if","name":"","username":"微信用户e6052e9b-129f-4afb-8da6-19c871943deb","password":"oLMM44_I7PB-ftIErN1qD97hkDOY","gender":1,"birthday":0,"register_time":"2020-05-07 13:29:31","last_login_time":"2020-05-07 13:29:31","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/g4wzd5sQZzCXdPdTnL3o6ibwPiaOEx35RN6cBzkuCnyOgCTD8PUT42dnibkck7mY3nUR8D8arH2afy4kyj8mkWEeg/132","weixin_openid":"oLMM44_I7PB-ftIErN1qD97hkDOY","name_mobile":0,"country":"","province":"","city":""]);
new_user_list.add(["id":1661,"nickname":"重新来过","name":"","username":"微信用户a567a32d-8f3c-4147-8c22-0518001ef6eb","password":"oLMM442U8nZ0otNd2rnq670aQ9Lc","gender":1,"birthday":0,"register_time":"2020-05-07 14:02:27","last_login_time":"2020-05-07 14:02:27","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJpNorAZxeB9Ws5fm3wvbYqtEq6P7WH7QOdIAxAXnQmtUibxWXx8d9SV6OSrGeqCE3oMIBFpS37cGg/132","weixin_openid":"oLMM442U8nZ0otNd2rnq670aQ9Lc","name_mobile":0,"country":"China","province":"Guangdong","city":"Shenzhen"]);
new_user_list.add(["id":1662,"nickname":"Mozzie","name":"","username":"微信用户82162f0e-6405-4ba9-a225-87cff585971d","password":"oLMM449QP2hLo3yAmMoPLhjH4peE","gender":1,"birthday":0,"register_time":"2020-05-07 15:22:33","last_login_time":"2020-05-07 15:22:33","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/LrykpzTTTGaYMetzhIWEnqI1dJn1NAFFtzmURG2U4UVDsHfBPCC6o7I98U4441uouxhK90bc6DwicTNxGm43kGw/132","weixin_openid":"oLMM449QP2hLo3yAmMoPLhjH4peE","name_mobile":0,"country":"China","province":"Yunnan","city":"Kunming"]);
new_user_list.add(["id":1663,"nickname":"守望星空","name":"","username":"微信用户12aa4bd6-b603-4ff7-89a6-ef00af5f41ed","password":"oLMM441aHu9ZdMf5xPMBm5DIbssI","gender":1,"birthday":0,"register_time":"2020-05-07 15:29:48","last_login_time":"2020-05-07 15:29:48","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJNM7kxqvPs3RLfeic1ctvWWXxtWJydMCaY2wVDxCTnlCG8DXWBwbBDDmibHsqNgBZJcwU6raW7TAlw/132","weixin_openid":"oLMM441aHu9ZdMf5xPMBm5DIbssI","name_mobile":0,"country":"Qatar","province":"","city":""]);
new_user_list.add(["id":1664,"nickname":"虎子","name":"","username":"微信用户a0c9ceeb-57cc-4081-a698-4834a7b278ec","password":"oLMM44wNsZ7QSL_-Qis4tEozHw2k","gender":1,"birthday":0,"register_time":"2020-05-07 15:56:31","last_login_time":"2020-05-07 15:56:31","last_login_ip":"","mobile":"","register_ip":"","avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLG56ialmIiciczKic4loDG5ibzRdiaG5VtRic0LibohaRJ259QtjxuSvHia4HqJWBe94lpHmh6DQbpibdG9tGA/132","weixin_openid":"oLMM44wNsZ7QSL_-Qis4tEozHw2k","name_mobile":0,"country":"China","province":"Zhejiang","city":"Wenzhou"]);
ret_data['newData'] = new_user_list;

return ProtObj.success(ret_data);

