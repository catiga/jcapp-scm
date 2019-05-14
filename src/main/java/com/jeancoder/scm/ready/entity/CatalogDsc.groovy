package com.jeancoder.scm.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'catalog_dsc')
class CatalogDsc {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger cat_id;
	
	@JCForeign
	BigInteger dsc_id;
	
	Integer flag = 0;
	
}
