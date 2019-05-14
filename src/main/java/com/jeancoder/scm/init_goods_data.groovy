package com.jeancoder.scm

import com.jeancoder.jdbc.JCME2SQL
import com.jeancoder.jdbc.sql.SqlParser
import com.jeancoder.scm.ready.entity.GoodsInfo

def file = '/Users/jackielee/Desktop/init_data/init_data_3.csv';

def data = new File(file);

def mul = new BigDecimal(100);

data.readLines().each({
	if(it.length()<10) {
		return;
	}
	def datalines = it.split(',');
	def goods_id = datalines[0];
	def goods_out_no = goods_id;
	
	def goods_name = datalines[1];
	def goods_unit = datalines[2];
	
	def cost_price = datalines[3];
	def sale_price = datalines[4];
	def rec_price = sale_price;
	
	if(cost_price.startsWith('¥')) {
		cost_price = cost_price.substring(1);
	}
	if(sale_price.startsWith('¥')) {
		sale_price = sale_price.substring(1);
	}
	if(rec_price.startsWith('¥')) {
		rec_price = rec_price.substring(1);
	}
	
	def proj_id = 1;
	
	//println goods_id + '_' + goods_out_no + '_' + goods_name + '_' + goods_unit + '_' + cost_price + '_' + sale_price + '_' + rec_price;
	
	def goods = new GoodsInfo();
	goods.goods_id = goods_id;
	goods.goods_out_no = goods_out_no;
	goods.goods_name = goods_name;
	goods.unit = goods_unit;
	goods.spec_unit = goods_unit;
	
	goods.goods_price = new BigDecimal(sale_price).multiply(mul).setScale(2);
	goods.goods_original_price = new BigDecimal(rec_price).multiply(mul).setScale(2);
	goods.cost_price = new BigDecimal(cost_price).multiply(mul).setScale(2);
	goods.proj_id = proj_id;
	
	SqlParser par = JCME2SQL.generateInsert(goods);
	String insert = par.getFormatSql();
	
	insert = "insert into data_goods_info(goods_id, goods_out_no, goods_name, unit, spec_unit, goods_price, goods_original_price, cost_price, proj_id) values('${goods.goods_id}', '${goods.goods_out_no}', '${goods.goods_name}', '${goods.unit}', '${goods.spec_unit}', '${goods.goods_price}', '${goods.goods_original_price}', '${goods.cost_price}', '${goods.proj_id}');";
	
	println insert;
})
