package com.jeancoder.scm.entry.catalog.aj

import org.apache.commons.fileupload.FileItem
import org.apache.commons.io.FileUtils

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.MD5Util
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.CatalogDsc
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

def cdn_root_path = '/data/cdn/jc';
def rel_path = 'scm/catlog';

SimpleAjax sys_conf = JC.internal.call(SimpleAjax, 'project', '/sys/get_root_path', null);
if(sys_conf && sys_conf.available) {
	cdn_root_path = sys_conf.data;
}

CatalogService service = CatalogService.INSTANCE();

Catalog catalog = null;

def name = JC.request.param('name');
def info = JC.request.param('info');
def code = JC.request.param('code');
def id = JC.request.param('id');
def parent_id = JC.request.param('parent_id');
def select_dsc = JC.request.param('select_dsc')?.trim();

def icon_path = null;

List<FileItem> items = JC.request.get().getFormItems();
if(items) {
	FileItem x = items.get(0);
	String content_type = x.contentType;
	String ct_code = content_type.split('\\/')[0];
	def input_stream = x.inputStream;
	def define_name = MD5Util.getStringMD5(x.name + nextInt(1000, 9999));
	def filename = GlobalHolder.proj.id + '_' + define_name;
	
	def file_path = cdn_root_path + '/' + rel_path;	// + '/' + filename;
	File file_obj = new File(file_path);
	if(!file_obj.exists()) {
		file_obj.mkdir();
	}
	file_path = file_path + '/' + filename;
	FileUtils.writeByteArrayToFile(new File(file_path), x.get());
	icon_path = rel_path + '/' + filename;
}


if(id) {
	catalog = service.get(id);
	if(catalog==null) {
		return SimpleAjax.notAvailable('obj_not_found,分组数据未找到');
	}
} else {
	catalog = new Catalog();
	if(parent_id) {
		Catalog parent = service.get(parent_id);
		if(parent!=null) {
			catalog.parent_id = parent.id;
		}
	}
}

catalog.cat_name_cn = name;
catalog.cat_name_en = code;
catalog.cat_info = info;
if(icon_path) {
	catalog.cat_icon = icon_path;
}
if(select_dsc) {
	catalog.show_dsc = select_dsc;
}

if(id) {
	service.update(catalog);
} else {
	id = service.save(catalog);
	catalog.id = id;
}
//更新显示渠道
def sql = 'delete from CatalogDsc where cat_id=?';
JcTemplate.INSTANCE().batchExecute(sql, catalog.id);
if(catalog.show_dsc&&catalog.show_dsc!='-1'&&catalog.show_dsc!='0') {
	def arr_dsc = catalog.show_dsc.split(",");
	for(x in arr_dsc) {
		try {
			CatalogDsc cat_dsc = new CatalogDsc();
			cat_dsc.cat_id = catalog.id;
			cat_dsc.dsc_id = new BigInteger(x);
			cat_dsc.flag = 0;
			JcTemplate.INSTANCE().save(cat_dsc);
		} catch(any) {}
	}
}

return SimpleAjax.available('', catalog);



def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}



