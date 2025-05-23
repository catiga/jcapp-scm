package com.jeancoder.scm.entry.incall.api.shipper

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.FreightRule
import com.jeancoder.scm.ready.entity.FreightTpl
import com.jeancoder.scm.ready.incall.api.ProtObj
import com.jeancoder.scm.ready.util.GlobalHolder

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

//更新基本信息
FreightTpl freight = JcTemplate.INSTANCE().get(FreightTpl, 'select * from FreightTpl where flag!=? and proj_id=? and id=?', -1, GlobalHolder.proj.id, info['id']);
if(freight==null) {
	return ProtObj.fail(110001, '模板未找到')
}
freight.freight_type = info['freight_type'];
freight.package_price = _to_mul_(info['package_price'], new BigDecimal(100));
freight.ft_name = info['name'];
JcTemplate.INSTANCE().update(freight);

def a_time = Calendar.getInstance().getTime();
//开始保存明细数据
def de_data = [];
de_data.addAll(map_to_rule(defaultData, a_time, freight.id));
de_data.addAll(map_to_rule(table, a_time, freight.id));

//删除所有已经存在的模版详细数据，然后进行保存
def sql = 'update FreightRule set flag=-1 where flag!=? and ftpl=?';
JcTemplate.INSTANCE().batchExecute(sql, -1, freight.id);
//保存数据
if(de_data) {
	for(x in de_data) {
		JcTemplate.INSTANCE().save(x);
	}
}

return ProtObj.success("");



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
	BigDecimal _1_ = new BigDecimal(1);
	
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


