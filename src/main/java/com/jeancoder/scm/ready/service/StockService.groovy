package com.jeancoder.scm.ready.service

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.AuthUser
import com.jeancoder.scm.ready.dto.GoodsStockFullForm
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.GsForm
import com.jeancoder.scm.ready.entity.OrderItem
import com.jeancoder.scm.ready.entity.StockOpBatch
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.support.exception.BelowSaftyStockException
import com.jeancoder.scm.ready.util.DateUtil
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.MoneyUtil

class StockService {
	
	private static final StockService __instance__ = new StockService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public static StockService INSTANCE() {
		return __instance__;
	}
	
	def gs_forms(JcPage<GsForm> page, def bt, def wh, def op, def gn) {
		String sql = 'select * from GsForm where flag!=?';
		def param = [];
		param.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?'; param.add(GlobalHolder.proj.id);
		}
		if(bt) {
			sql += ' and batch_id=?'; param.add(bt);
		}
		if(wh) {
			sql += ' and wh_id=?'; param.add(wh);
		}
		if(op) {
			sql += ' and batch_id in (select id from StockOpBatch where op_type=?)'; param.add(op);
		}
		if(gn) {
			sql += ' and id in (select form_id from GoodsStock where goods_no=?)'; param.add(gn);
		}
		sql += ' order by happen_time desc';
		return jc_template.find(GsForm, page, sql, param.toArray());
	}
	
	public GoodsStockFullForm get_gs_form(def id) {
		if(!id) {
			return null;
		}
		String sql = 'select * from GsForm where flag!=? and pid=? and id=?';
		GsForm form = jc_template.get(GsForm.class, sql, -1, GlobalHolder.proj.id, id);
		if(form==null) {
			return null;
		}
		sql = 'select * from GoodsStock where flag!=? and form_id=?';
		List<GoodsStock> stocks = jc_template.find(GoodsStock.class, sql, -1, id);
		GoodsStockFullForm full_form = new GoodsStockFullForm(form, stocks);
		return full_form;
	}
	
	public GsForm get_gs_form_simple(def id) {
		if(!id) {
			return null;
		}
		String sql = 'select * from GsForm where flag!=? and pid=? and id=?';
		GsForm form = jc_template.get(GsForm.class, sql, -1, GlobalHolder.proj.id, id);
		return form;
	}
	
	public List<GsForm> find_out_put_form(WareHouse aim_house) {
		String sql = 'select * from GsForm where flag!=? and aim_wh_id=? and pid=?';
		return jc_template.find(GsForm.class, sql, -1, aim_house.id, GlobalHolder.proj.id);
	}
	
	public JcPage<StockOpBatch> find_batches(JcPage<StockOpBatch> page) {
		String sql = "select * from StockOpBatch where flag!=? and pid=? order by a_time desc";
		page = jc_template.find(StockOpBatch.class, page, sql, -1, GlobalHolder.proj.id);
		return page;
	}
	
	public StockOpBatch get(def id) {
		String sql = "select * from StockOpBatch where flag!=? and pid=? and id=?";
		return jc_template.get(StockOpBatch.class, sql, -1, GlobalHolder.proj.id, id);
	}
	
	public void update(StockOpBatch e) {
		e.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		jc_template.update(e);
	}
	
	public StockOpBatch save(StockOpBatch s) {
		s.pid = GlobalHolder.proj.id;
		s.flag = 0;
		s.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		s.c_time = s.a_time;
		BigInteger id = jc_template.save(s);
		s.id = id;
		return s;
	}
	
	public List<StockOpBatch> find_by_code(Integer in_house, def op_type, def pid = GlobalHolder.proj.id) {
		String sql = 'select * from StockOpBatch where flag!=? and pid=? and in_house=?';
		def param = [];
		param.add(-1); param.add(pid); param.add(in_house);
		if(op_type) {
			sql += ' and op_type=?';
			param.add(op_type);
		}
		sql += ' order by is_def_sys desc';
		return jc_template.find(StockOpBatch, sql, param.toArray());
	}
	
	public List<StockOpBatch> find_all_batch(def pid = GlobalHolder.proj.id) {
		String sql = 'select * from StockOpBatch where flag!=? and pid=?';
		def param = [];
		param.add(-1); param.add(pid);
		sql += ' order by is_def_sys desc';
		return jc_template.find(StockOpBatch, sql, param.toArray());
	}
	
	public void update_gs_form(GsForm form) {
		jc_template.update(form);
	}
	
	/**
	 * 执行入库操作
	 * @param batch
	 * @param warehouse
	 * @param gs
	 * gs format，标准协议
	 * 商品, SKU, 数量, 生产日期, 有效期, 成本价, 可用状态
	 * 
	 * @param a_time
	 * @param remark
	 * @param happen_user 实际发生人
	 * @param op_user     操作记录人
	 * @return
	 */
	public def in_gs_form(StockOpBatch batch, WareHouse warehouse, List<?> gs, Timestamp a_time, def remark, AuthUser happen_user, GsForm original_form) {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		//保存form
		GsForm form = new GsForm();
		form.a_time = ts;
		form.batch_id = batch.id;
		form.batch_name = batch.bname;
		form.c_time = ts;
		form.flag = 0;
		form.happen_time = a_time;
		form.pid = GlobalHolder.proj.id;
		form.remark = remark;
		form.wh_id = warehouse.id;
		
		if(happen_user!=null) {
			form.happen_uid = happen_user.id;
			form.happen_uname = happen_user.name;
		}
		AuthToken token = GlobalHolder.token;
		AuthUser op_user = token==null?null:token.user;
		if(op_user!=null) {
			form.op_uid = op_user.id;
			form.op_uname = op_user.name;
		}
		if(original_form!=null) {
			form.origdata = original_form.id;
		}
		
		BigInteger form_id = jc_template.save(form);
		
		for(m in gs) {
			this.in_goods_batch(batch, warehouse, m, ts, form_id);
		}
		form.id = form_id;
		return form;
	}
	
	/**
	 * 单个商品入库，入库操作不判断库存
	 * @param batch
	 * @param house
	 * @param arr_param
	 * 协议数组
	 * 商品, SKU, 数量, 生产日期, 有效期, 成本价, 可用状态
	 * 
	 * @param a_time
	 * @param form
	 * @return
	 */
	protected def in_goods_batch(StockOpBatch batch, WareHouse house, List<?> arr_param, Timestamp a_time, BigInteger form) {
		GoodsInfo g = arr_param[0];
		GoodsSku sku = arr_param[1];
		BigDecimal num = arr_param[2];
		num = Math.abs(num);
		Date product_date = DateUtil.parse_yyyyMMdd(arr_param[3]);
		Date quality_date = DateUtil.parse_yyyyMMdd(arr_param[4]);
		BigDecimal cost_price = arr_param[5];
		Integer confirm_status = arr_param[6];
		BigDecimal tax_fee = MoneyUtil.get_yuan_from_cent(arr_param[7]?.toString());
			
		GoodsStock gs = new GoodsStock();
		gs.pid = GlobalHolder.proj.id;
		gs.a_time = a_time;
		gs.flag = confirm_status;
		gs.goods_id = g.id;
		gs.goods_name = g.goods_name;
		gs.goods_no = g.goods_id;
		gs.goods_sku_id = sku==null?null:sku.id;
		gs.goods_sku_name = sku==null?null:sku.skus;
		gs.goods_sku_no = sku==null?null:sku.sku_no;
		gs.in_house = batch.in_house;
		gs.op_type = batch.op_type;
		gs.opbatchid = batch.id;
		gs.opbatchsn = batch.sn;
		gs.stock = num;
		
		gs.stunicode = g.id.toString() + '_' + (sku==null?'0':sku.id.toString());
		
		//设置入库相关的单位规格信息 开始
		gs.unit = g.unit;
		gs.weight = g.weight;
		gs.spec_unit = g.spec_unit;
		//设置入库相关的单位规格信息 结束
		
		gs.form_id = form;
		gs.store_id = house.store_id;
		gs.store_name = house.store_name;
		gs.wh_id = house.id;
		gs.wh_name = house.name;
		
		gs.parainfo = null;
		
		gs.product_date = DateUtil.format_yyyyMMdd(product_date);
		gs.quality_date = DateUtil.format_yyyyMMdd(quality_date);
		
		if(cost_price==null) {
			gs.cost_price = (sku==null?g.cost_price:sku.sku_price);
		} else {
			gs.cost_price = cost_price;
		}
		if(tax_fee!=null) {
			gs.tax_fee = tax_fee;
		}
		
		jc_template.save(gs);
	}
	
	/**
	 * 执行出库操作，增加一次出库对应的入库库存判断
	 * TODO 
	 * 出库操作的特殊性在于要对每一次的出库库存做一次入库批次选择
	 * @param batch
	 * @param pub_warehouse 发生仓库
	 * @param acc_warehouse 目标仓库
	 * @param gs			[g, sku, nums, item]
	 * @param a_time
	 * @param remark
	 * @param happen_user
	 * @param op_user
	 * @return
	 */
	public def out_gs_form(StockOpBatch batch, WareHouse pub_warehouse, WareHouse acc_warehouse, List<?> gs, 
		Timestamp a_time, def remark, AuthUser happen_user, def pid = GlobalHolder.proj.id) {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		//保存form
		GsForm form = new GsForm();
		form.a_time = ts;
		form.batch_id = batch.id;
		form.batch_name = batch.bname;
		form.c_time = ts;
		form.flag = 0;
		form.happen_time = a_time;
		form.pid = pid;
		form.remark = remark;
		form.wh_id = pub_warehouse.id;
		form.wh_name = pub_warehouse.name;
		
		if(happen_user!=null) {
			form.happen_uid = happen_user.id;
			form.happen_uname = happen_user.name;
		}
		AuthToken token = GlobalHolder.token;
		AuthUser op_user = token==null?null:token.user;
		if(op_user!=null) {
			form.op_uid = op_user.id;
			form.op_uname = op_user.name;
		}
		if(acc_warehouse!=null) {
			form.aim_wh_id = acc_warehouse.id;
			form.aim_wh_name = acc_warehouse.name;
		}
		
		BigInteger form_id = jc_template.save(form);
		
		def fail_ops = [];
		for(m in gs) {
			try {
				this.out_goods_batch(batch, pub_warehouse, m, ts, form_id, pid);
			}catch(BelowSaftyStockException e) {
				//记录失败的出库记录
				fail_ops.add([m[0], m[1], m[2]]);
			}
		}
		if(gs.size()==fail_ops.size()) {
			//说明全部失败，则删除掉这笔表单
			form.id = form_id;
			form.flag = -1;
			jc_template.update(form);
		}
		return fail_ops;
	}
	
	/**
	 * 单个商品出库
	 * 出库操作需要判断库存
	 * @param batch
	 * @param house
	 * @param arr_param		[g, sku, nums, item] item仅对商品销售有意义
	 * arr_param协议数组
	 * 商品，SKU，出库数量
	 * 
	 * @param a_time
	 * @param form
	 * @return
	 */
	protected def out_goods_batch(StockOpBatch batch, WareHouse house, List<?> arr_param, Timestamp a_time, BigInteger form, def pid = GlobalHolder.proj.id) {
		GoodsInfo g = arr_param[0];
		GoodsSku sku = arr_param[1];
		BigDecimal num = arr_param[2];
		OrderItem item = null;
		if(batch.op_type=='2000'&&arr_param.size()>3) {
			item = arr_param[3];
		}
		
		//def fail_ops = [];
		List<GoodsStock> available_stocks = this.find_goods_sku_stocks(house, g, sku, num);
		if(available_stocks==null||available_stocks.empty) {
			throw new BelowSaftyStockException();
		}
		//需要拆分出库记录
		BigDecimal next_deplus = num;
		for(GoodsStock it_gs : available_stocks) {
			BigDecimal available_nums = it_gs.stock;
			if(next_deplus<=0) {
				//说明不再需要扣减库存，则直接break；
				break;
			}
			BigDecimal curr_deplus = next_deplus>available_nums?available_nums:next_deplus;
			//next_deplus -= available_nums;
			next_deplus = next_deplus.subtract(available_nums);
			
			BigDecimal tmp_value = curr_deplus.multiply(new BigDecimal(-1));
			Date product_date = DateUtil.parse_yyyyMMdd(it_gs.product_date);
			Date quality_date = DateUtil.parse_yyyyMMdd(it_gs.quality_date);
			BigDecimal cost_price = it_gs.cost_price;

			GoodsStock gs = new GoodsStock();
			gs.pid = pid;
			gs.a_time = a_time;
			gs.flag = 0;
			gs.goods_id = g.id;
			gs.goods_name = g.goods_name;
			gs.goods_no = g.goods_id;
			gs.goods_sku_id = sku==null?null:sku.id;
			gs.goods_sku_name = sku==null?null:sku.skus;
			gs.goods_sku_no = sku==null?null:sku.sku_no;
			gs.in_house = batch.in_house;
			gs.op_type = batch.op_type;
			gs.opbatchid = batch.id;
			gs.opbatchsn = batch.sn;
			gs.stock = tmp_value;
			gs.unit = g.unit;
			gs.spec_unit = g.spec_unit;
			gs.weight = g.weight;
			
			gs.form_id = form;
			gs.store_id = house.store_id;
			gs.store_name = house.store_name;
			gs.wh_id = house.id;
			gs.wh_name = house.name;
			
			gs.remark = item==null?null:item.id;
			
			gs.parainfo = item==null?null:JackSonBeanMapper.toJson(item);
			
			gs.product_date = DateUtil.format_yyyyMMdd(product_date);
			gs.quality_date = DateUtil.format_yyyyMMdd(quality_date);
			
			if(cost_price==null) {
				gs.cost_price = (sku==null?g.cost_price:sku.sku_price);
			} else {
				gs.cost_price = cost_price;
			}
			gs.origdata = it_gs.id;
			jc_template.save(gs);
		}
	}
	
	protected List<GoodsStock> find_goods_sku_stocks(WareHouse ware, GoodsInfo g, GoodsSku s, BigDecimal num) {
		GoodsService gs_service = GoodsService.INSTANCE();
		
		BigDecimal sum_stock = gs_service.get_goods_sku_stock(g, s, ware);
		if(sum_stock==null) {
			sum_stock = new BigDecimal(0);
		}
		BigDecimal rest_stock = sum_stock.subtract(num).subtract(this.default_safty_stock())
		if(rest_stock.longValue() < 0l) {
			//说明库存不足，低于设置的安全库存，该商品无法出库
			throw new BelowSaftyStockException();
		}
		
		def strategy = this.default_out_pub_strategy();
		def strategy_orderby;
		if(strategy==1) {
			//按照入库时间优先
			strategy_orderby = 'a_time asc';
		} else {
			//其他情况都是利润差值优先
			strategy_orderby = 'cost_price asc';
		}
		List<GoodsStock> stocks = gs_service.find_goos_sku_stock(g, s, ware, strategy_orderby);
		return stocks;
	}
	
	/**
	 * 剩余库存系统支持的出库策略
	 * 1:入库时间优先：按照入库时间从早到晚优先
	 * 2.利润差值优先：按照入库价格从低到高优先
	 * @return
	 */
	public def default_out_pub_strategy() {
		//直接返回设定好的
		return 1;
	}
	
	public BigDecimal default_safty_stock() {
		//默认库存一件不留
		return new BigDecimal(0);
	}
}














