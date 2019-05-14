package com.jeancoder.scm.ready.prefer

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.incall.MCCompute
import com.jeancoder.scm.ready.prep.McForCashier
import com.jeancoder.scm.ready.prep.McInfo
import com.jeancoder.scm.ready.prep.PrepData

class McPrefer extends Prefer {

	SimpleAjax compute(def order_param, def card_code, def op, def g_list) {
		def pid = order_param['pid']
		def sid = order_param['sid']
		def oid = order_param['id']
		def on = order_param['on']
		def oc = order_param['oc']
		
		def param = [card_code:card_code,g:g_list];
		MCCompute ret_data = null;
		try {
			def p = JackSonBeanMapper.mapToJson(param);
			def request_param = [p:p,pid:pid,sid:sid,on:on,oc:oc];
			if(op) {
				request_param.put('op', op);
			}
			def ret_json = JC.internal.call('crm', '/api/order/compute_mc_price', request_param);

			ret_data = JackSonBeanMapper.fromJson(ret_json, MCCompute);
		}catch(any) {
			any.printStackTrace();
		}

		if(ret_data==null) {
			return SimpleAjax.notAvailable('comm_error,服务通讯失败');
		}
		if(ret_data.code=='0') {
			//成功
			McInfo mc_info = new McInfo(card_num:ret_data.accountMc.cn,name:ret_data.accountMc.mcname,phone:ret_data.accountMc.mcmobile,levname:ret_data.accountMc.levelname,balance:ret_data.accountMc.balance);
			McForCashier mfc_obj = new McForCashier(mc:mc_info);
			mfc_obj.items = ret_data.items;

			PrepData for_trade_prep = new PrepData(order_id:oid,oc:'1000',prefcode:'100',other:mfc_obj);
			for_trade_prep.pref_amount = ret_data.offerAmount;
			for_trade_prep.pay_amount = ret_data.totalAmount;
			return SimpleAjax.available('', for_trade_prep);
		} else {
			return SimpleAjax.notAvailable('compute_fail,优惠价计算失败');
		}
	}
}
