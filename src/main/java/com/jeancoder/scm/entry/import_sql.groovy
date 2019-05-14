package com.jeancoder.scm.entry

def f = '/Users/jackielee/Desktop/huacai_goods';

def lineList = new File(f).readLines();
def index = 1;
for(it in lineList) {
	def goods_data = it.trim().split(' ');
	
	def goods_out_no = goods_data[0];
	def goods_name = '';
	def cost_price = null;
	def sale_price = null;
	
	if(index==380) {
		println cost_price;
	}
	
	for(def i=1; i<goods_data.length; i++) {
		def y = goods_data[i];
		if(y=='') {
			continue;
		}
		goods_name = y;
		for(def j=2; j<goods_data.length; j++) {
			def z = goods_data[j];
			if(z=='') {
				continue;
			}
			try {
				def tmp_price = new BigDecimal(z);
				if(cost_price==null) {
					cost_price = tmp_price.toString();
				} else if(sale_price==null) {
					sale_price = tmp_price.toString();
				}
			} catch(any) {
				z = z.substring(1);
				try {
					def tmp_price = new BigDecimal(z);
					if(cost_price==null) {
						cost_price = tmp_price.toString();
					} else if(sale_price==null) {
						sale_price = tmp_price.toString();
					}
				} catch(any2) {
					goods_name = goods_name + ' ' + z;
				}
			}
		}
		break;
	}
	
	
	
	
	try {
		cost_price = new BigDecimal(cost_price);
		cost_price = cost_price.multiply(new BigDecimal(100)).setScale(2);
	} catch(any) {
		println index + ':' + cost_price;
		cost_price = cost_price.toString().substring(1);
		cost_price = new BigDecimal(cost_price);
		cost_price = cost_price.multiply(new BigDecimal(100)).setScale(2);
	}
	
	if(sale_price==null) {
		sale_price = cost_price;
	}
	try {
		sale_price = new BigDecimal(sale_price);
		sale_price = sale_price.multiply(new BigDecimal(100)).setScale(2);
	} catch(any) {
		println index + ':' + sale_price;
		sale_price = sale_price.toString().substring(1);
		sale_price = new BigDecimal(sale_price);
		sale_price = sale_price.multiply(new BigDecimal(100)).setScale(2);
	}
	
	def sql = 'insert into data_goods_info(goods_id, goods_out_no, goods_name, cost_price, goods_original_price, goods_price) values("' + goods_out_no + '" ,"' + goods_out_no + '","' + goods_name + '","' + cost_price + '","' + sale_price + '","' + sale_price + '");';
	
	println sql;
}