package com.jeancoder.scm.ready.dto

import com.jeancoder.scm.ready.entity.Catalog

class CatalogDto {
	
	String data;
	
	CatalogAttr attr;

	String text;
	
	String id;
	
	String parent;
	
	String state = "opened";
	
	boolean children = true;
	
	CatalogDto() {}
	
	CatalogDto(Catalog e) {
		this.text = e.cat_name_cn;
		this.id = e.id.toString();
		this.parent = e.parent_id.toString();
		if(e.parent_id==0||e.parent_id==null) {
			this.parent = "#";
		}
		
		this.attr = new CatalogAttr();
		this.attr.id = e.id.toString();
		this.data = e.cat_name_en;
	}
}
