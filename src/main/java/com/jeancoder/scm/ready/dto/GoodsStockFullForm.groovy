package com.jeancoder.scm.ready.dto

import com.jeancoder.scm.ready.entity.GoodsStock
import com.jeancoder.scm.ready.entity.GsForm

class GoodsStockFullForm {

	GsForm form;
	
	List<GoodsStock> stocks;
	
	GoodsStockFullForm(GsForm form, List<GoodsStock> stocks) {
		this.form = form;
		this.stocks = stocks;
	}
	
}
