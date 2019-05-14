package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.GoodsStockData
import com.jeancoder.scm.ready.entity.GoodsInfo
import com.jeancoder.scm.ready.entity.GoodsSku
import com.jeancoder.scm.ready.entity.StockTakeForm
import com.jeancoder.scm.ready.entity.StockTakeFormItem
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.util.GlobalHolder

class StockTakeService {

	private static final StockTakeService __instance__ = new StockTakeService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public static StockTakeService INSTANCE() {
		return __instance__;
	}
	
	JcPage<StockTakeForm> find(JcPage<StockTakeForm> page, def form_no, def status, def warehouse) {
		String sql = 'select * from StockTakeForm where flag!=? and pid=?';
		def params = [];
		params.add(-1);
		params.add(GlobalHolder.proj.id);
		
		if(form_no) {
			sql += ' and icno=?';
			params.add(form_no);
		}
		if(warehouse) {
			sql += ' and wh_id=?';
			params.add(warehouse);
		}
		if(status) {
			sql += ' and icss=?';
			params.add(status);
		}
		
		return jc_template.find(StockTakeForm.class, page, sql, params.toArray());
	}
	
	StockTakeForm get(def id, def pid = GlobalHolder.proj.id) {
		if(!id) {
			return null;
		}
		String sql = 'select * from StockTakeForm where flag!=? and pid=? and id=?';
		return jc_template.get(StockTakeForm.class, sql, -1, pid, id);
	}
	
	void update_form_item(StockTakeForm form, GoodsInfo goods, GoodsSku sku, def real_stock, def rec_stock) {
		String sql = 'select * from StockTakeFormItem where flag!=? and icid=? and goods_id=?';
		def params = []; params.add(-1); params.add(form.id); params.add(goods.id);
		if(sku!=null) {
			sql += ' and goods_sku_id=?'; params.add(sku.id);
		}
		StockTakeFormItem form_item = jc_template.get(StockTakeFormItem, sql, params.toArray());
		
		boolean do_save = false;
		if(form_item==null) {
			do_save = true;
			form_item = new StockTakeFormItem();
			form_item.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			form_item.flag = 0;
			form_item.goods_id = goods.id;
			form_item.goods_name = goods.goods_name;
			form_item.goods_no = goods.goods_id;
			form_item.goods_sku_id = sku?.id;
			form_item.goods_sku_name = sku?.skus;
			form_item.goods_sku_no = sku?.sku_no;
			form_item.icid = form.id;
			form_item.spec = goods.spec_unit;
			form_item.unit = goods.unit;
		}
		
		form_item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		form_item.realnum = real_stock;
		form_item.recnum = rec_stock;
		if(do_save) {
			jc_template.save(form_item);
		} else {
			jc_template.update(form_item);
		}
	}
	
	BigInteger save_form(def form_id, def icname, def icinfo, def ictype, WareHouse house) {
		//首先判断是否存在同仓库正在进行的库存盘点表
		String sql = 'select * from StockTakeForm where flag!=? and pid=? and wh_id=? and icss=?';
		StockTakeForm ex_form = jc_template.get(StockTakeForm.class, sql, -1, GlobalHolder.proj.id, house.id, '0000');
		if(ex_form!=null) {
			return BigInteger.valueOf(-1l);
		}
		StockTakeForm form = jc_template.get(StockTakeForm.class, 'select * from StockTakeForm where flag!=? and pid=? and id=?', -1, GlobalHolder.proj.id, form_id);
		boolean do_save = false;
		if(form==null) {
			do_save = true;
			form = new StockTakeForm();
			form.pid = GlobalHolder.proj.id;
			form.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			form.flag = 0;
			form.icss = '0000';
			form.wh_id = house.id;
			form.wh_name = house.name;
			form.ictype = ictype;
			if(house.store_id!=null) {
				form.storeid = house.store_id;
				form.storename = house.store_name;
			}
			form.ouid = GlobalHolder.token.user.id;
			form.ouname = GlobalHolder.token.user.name;
			//生成盘点编号
			form.icno = form.pid + '' + form.wh_id + '' + this.nextInt(100, 999) + this.nextInt(10, 99);
		}
		form.icinfo = icinfo;
		form.icname = icname;
		if(do_save) {
			form_id = jc_template.save(form);
		} else {
			jc_template.update(form);
		}
		return form_id;
	}
	
	void update_form(StockTakeForm form) {
		jc_template.update(form);
	}
	
	boolean start_form(StockTakeForm form) {
		//首先检查对应的目标仓库有没有正在进行中的盘点表
		String sql = 'select * from StockTakeForm where flag!=? and pid=? and wh_id=? and icss=?';
		StockTakeForm stform = jc_template.get(StockTakeForm.class, sql, -1, GlobalHolder.proj.id, form.wh_id, '1000');
		if(stform!=null) {
			return false;
		}
		form.icss = '1000'
		jc_template.update(form);
		return true;
	}
	
	void finish_form(StockTakeForm form) {
		form.icss = '6000';
		jc_template.update(form);
	}
	
	List<StockTakeFormItem> get_items(StockTakeForm form) {
		if(form==null) {
			return null;
		}
		String sql = 'select * from StockTakeFormItem where flag!=? and icid=? order by a_time asc';
		return jc_template.find(StockTakeFormItem.class, sql, -1, form.id);
	}
	
	void init_form_items(StockTakeForm form, List<GoodsStockData> stock_items) {
		//首先删除所有的form下对应的数据，所以要谨慎调用
		String sql = 'update StockTakeFormItem set flag=-1 where icid=? and pid=?';
		jc_template.batchExecute(sql, form.id, GlobalHolder.proj.id);
		
		Timestamp a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for(x in stock_items) {
			StockTakeFormItem si = new StockTakeFormItem();
			si.a_time = a_time;
			si.c_time = a_time;
			si.flag = 0;
			si.goods_id = x.goods_id;
			si.goods_no = x.goods_no;
			si.goods_name = x.goods_name;
			si.goods_sku_id = x.goods_sku_id;
			si.goods_sku_no = x.goods_sku_no;
			si.goods_sku_name = x.goods_sku_name;
			si.icid = form.id;
			si.pid = GlobalHolder.proj.id;
			si.recnum = x.stock;
			si.spec = x.spec_unit;
			si.unit = x.unit;
			jc_template.save(si);
		}
	}
	
	def nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
}
