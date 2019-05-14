package com.jeancoder.scm.internal.incall.order

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.MD5Util
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.DataTrans
import com.jeancoder.scm.ready.dto.NotifyObj
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.entity.OrderInfo
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService
import com.jeancoder.scm.ready.service.MergeGoodsService
import com.jeancoder.scm.ready.service.OrderService
import com.jeancoder.scm.ready.service.SaleMgrService
import com.jeancoder.scm.ready.service.StockService
import com.jeancoder.scm.ready.service.WareHouseService
import com.jeancoder.scm.ready.util.GlobalHolder

JCLogger LOGGER = JCLoggerFactory.getLogger('GOODS_ORDER_NOTIFY');

def oc = JC.internal.param('oc');
def on = JC.internal.param('on');
def sid = JC.internal.param('sid');
def sname = JC.internal.param('sname');
def sign = JC.internal.param('sign');
def pt = JC.internal.param('pt');
def op = JC.internal.param('op');
def pay_amount = JC.internal.param('pay_amount');

def pid = JC.internal.param('pid');
if(!pid) {
	pid = GlobalHolder.proj.id;
}
SysProjectInfo project = new SysProjectInfo();
project.id = new BigInteger(pid.toString());
GlobalHolder.setProj(project);
try {
	//DataTrans dt = GlobalHolder.getDt();
	
	def app_code = 'scm';
	def order_type = '1000';
	def call_back = '/incall/order/notify';
	def dataformat = null;
	
	DataTrans dt = JC.internal.call(DataTrans, 'trade', '/incall/reg/trigger', [app_code:app_code,order_type:order_type,callback:call_back]);
	if(dt==null||dt.code!='0') {
		return NotifyObj.build('-1', '交易出库错误', '1001', '交易模块注册错误', null);
	}
	def code_result = null;
	dt.data.each{
		it-> if(it.order_type=='1000') {
			code_result = it.token;
			return;
		}
	}
	if(!code_result) {
		code_result = '';
	}
	//计算sign
	def orig_str = 'oc='+oc+'&on='+on;
	def orig_str_sign = MD5Util.getStringMD5(orig_str + code_result);
	
	if(sign!=orig_str_sign) {
		return NotifyObj.build('-1', '交易出库错误', '2001', '密钥签名验证错误', null);
	}
	
	GoodsService goods_service = GoodsService.INSTANCE();
	CmpGoodsService cmp_goods_service = CmpGoodsService.INSTANCE();
	MergeGoodsService merge_goods_service = MergeGoodsService.INSTANCE();
	
	WareHouseService wh_service = WareHouseService.INSTANCE();
	OrderService order_service = OrderService.INSTANCE();
	StockService stock_service = StockService.INSTANCE();
	//查找对应的销售出库批次／默认
	Integer in_house = 2;	//默认为销售出库
	String op_type = '2000';
	
	if(op=='refund') {
		//销售退单
		in_house = 1;
		op_type = '2001';
	}
	
	// 先统一设置为支付成功
	List<OrderInfo> order_result_list = order_service.find_order_by_num(pid, on);
	for(x in order_result_list) {
		try {
			x.pay_type = pt;
			x.pay_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			x.pay_amount = new BigDecimal(pay_amount);
			x.oss = "1000";
		}catch(any){}
		JcTemplate.INSTANCE().update(x);
	}
	
	List<StockOpBatch> op_batches = stock_service.find_by_code(in_house, op_type, pid);
	//系统未设置可用的销售批次
	if(op_batches==null||op_batches.empty) {
		LOGGER.error('order_pub_error, for not_sale_batch_allowed');
		return NotifyObj.build('-1', '交易库存操作失败，未设置可用销售出库或退单批次', '3001', '未设置可用销售出库或退单批次', null);
	}
	
	//执行订单成功状态修改
	List<OrderInfo> order_result = order_service.find_order_by_num(pid, on);
	if(op==null) {
		//所有订单状态均设置为待出库／待制作
		//order_result.each({o -> o.oss = '1020'; o.pay_type=pt; JcTemplate.INSTANCE().update(o)});
		for(x in order_result) {
			x.pay_type = pt; x.pay_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			if(x.dss=='130'||x.dss=='190') {
				//店内自取与电商类订单，设置为待取货
				x.oss = '2000';		//待领取状态
			} else {
				//其他均设置为待制作状态
				x.oss = '1020';
			}
			try {
				x.pay_amount = new BigDecimal(pay_amount);
			}catch(any){}
			JcTemplate.INSTANCE().update(x);
		}
	} else if(op=='refund') {
		//所有订单状态均设置为待出库／待制作
		order_result.each({o -> o.oss = '8800'; JcTemplate.INSTANCE().update(o)});
	}
	
	//开始执行具体的库存扣减操作
	StockOpBatch batch = op_batches.get(0);
	
	def fail_ops = [];
	
	//执行商品订单相关操作
	for(x in order_result) {
		def store_id = x.store_id;
		WareHouse default_wh = wh_service.get_default_warehouse_by_store(store_id, pid);
		if(default_wh==null) {
			return NotifyObj.build('-1', '交易出库失败', '3003', '未设置出库仓库', null);
		}
		//查找订单对应的明细
		List<OrderItem> order_items = order_service.find_order_items(x);
		if(op==null) {
			def fail_op_1 = SaleMgrService.INSTANCE.pub_goods(batch, default_wh, x, order_items);
			if(fail_op_1) {
				fail_ops.addAll(fail_op_1);
			}
		} else if(op=='refund') {
			def fail_op_1 = SaleMgrService.INSTANCE.refund_goods(batch, default_wh, x, order_items);
			if(fail_op_1) {
				fail_ops.addAll(fail_op_1);
			}
		}
	}
	if(fail_ops!=null&&!fail_ops.empty) {
		NotifyObj ret_obj = NotifyObj.build('-1', '交易出库失败', '3004', '可用库存不足', fail_ops);
		return ret_obj;
	}
	LOGGER.info('PUB_STOCK_FINISHED');
	return NotifyObj.succ();
} catch (any) {
	LOGGER.error("",any)
	return NotifyObj.build('-1', '系统错误', '2001', '未知错误', null);
} finally {
	GlobalHolder.remove();
}










