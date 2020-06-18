package com.jeancoder.scm.entry.incall.api.shipper

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.FreightRule
import com.jeancoder.scm.ready.entity.FreightTpl
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.MoneyUtil

def params = JC.request.get().getInputStream();
if(params) {
	params = new String(params.getBytes(), 'UTF-8');
}

try {
	params = JackSonBeanMapper.jsonToMap(params);
} catch(any) {
	return ProtObj.fail(210001, '参数错误');
}

def info = params['info'];
def defaultData = params['defaultData'];
def table = params['table'];

//开始保存模板信息
FreightTpl f_tpl = new FreightTpl();
f_tpl.a_time = new Date();
f_tpl.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
f_tpl.flag = 0;
f_tpl.freight_type = info['freight_type'];
f_tpl.package_price = MoneyUtil.get_cent_from_yuan(info['package_price']);
f_tpl.ft_name = info['name'];
f_tpl.proj_id = GlobalHolder.proj.id;
f_tpl.is_def_sys = 0;

f_tpl.id = JcTemplate.INSTANCE().save(f_tpl);

BigDecimal _100_ = new BigDecimal(100);
BigDecimal _1000_ = new BigDecimal(1000);

def a_time = Calendar.getInstance().getTime();

//开始保存明细数据
def de_data = [];
de_data.addAll(map_to_rule(defaultData, a_time, f_tpl.id));
de_data.addAll(map_to_rule(table, a_time, f_tpl.id));

if(de_data) {
	for(x in de_data) {
		JcTemplate.INSTANCE().save(x);
	}
}

return ProtObj.success(1);

//转化成bigdecimal
BigDecimal trans(def value) {
	def ret_value = null;
	if(value!=null) {
		try {
			ret_value = new BigDecimal(value.toString());
		} catch(any) {}
	}
	return ret_value;
}

BigDecimal _to_mul_(def value, BigDecimal bd) {
	value = trans(value);
	if(value!=null) {
		value = value.multiply(bd);
	}
	return value;
}

List<FreightRule> map_to_rule(def data_list, Date a_time, BigInteger tpl_id) {
	BigDecimal _100_ = new BigDecimal(100);
	BigDecimal _1000_ = new BigDecimal(1000);
	
	def data = [];
	for(x in data_list) {
		def defaultData = x;
		//首先添加default_data
		String default_ac = defaultData['area']?defaultData['area']:'0';
		BigDecimal default_ff = _to_mul_(defaultData['start'], _1_);
		BigDecimal default_fm = _to_mul_(defaultData['start_fee'], _100_); //trans(defaultData[0]['start_fee']);
		BigDecimal default_cff = _to_mul_(defaultData['add'], _1_);
		BigDecimal default_cfm = _to_mul_(defaultData['add_fee'], _100_);
		def default_freeByMoney = defaultData['freeByMoney'];
		def default_freeByNumber = defaultData['freeByNumber'];
		BigDecimal default_free_by_money = _to_mul_(defaultData['free_by_money'], _100_);
		BigDecimal default_free_by_number = trans(defaultData['free_by_number']);
		
		FreightRule ftl_rule = new FreightRule();
		ftl_rule.ac = default_ac;
		ftl_rule.ff = default_ff;
		ftl_rule.fm = default_fm;
		ftl_rule.cff = default_cff;
		ftl_rule.cfm = default_cfm;
		ftl_rule.freemoney_flag = default_freeByMoney?1:0;
		ftl_rule.freemoney_value = default_free_by_money;
		ftl_rule.freewn_flag = default_freeByNumber?1:0;
		ftl_rule.freewn_value = default_free_by_number;
		ftl_rule.fpt = '0';
		ftl_rule.fpv = '0';
		ftl_rule.a_time = a_time;
		ftl_rule.flag = 0;
		ftl_rule.c_time = new Timestamp(a_time.getTime());
		ftl_rule.ftpl = tpl_id;
		
		data.add(ftl_rule);
	}
	
	return data;
}


