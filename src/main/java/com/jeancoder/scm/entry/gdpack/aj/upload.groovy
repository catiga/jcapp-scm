package com.jeancoder.scm.entry.gdpack.aj

import java.sql.Timestamp

import org.apache.commons.fileupload.FileItem
import org.apache.commons.io.FileUtils

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.util.MD5Util
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.service.CmpGoodsService
import com.jeancoder.scm.ready.service.GoodsService

def cdn_root_path = '/data/cdn/jc';
def rel_path = 'scm/gdpack';

def g_id = JC.request.param('g_id');

GoodsService g_ser = GoodsService.INSTANCE();

CmpGoodsService cmp_service = CmpGoodsService.INSTANCE();
GoodsPack goods = cmp_service.get_pack(g_id);
if(goods==null) {
	return SimpleAjax.notAvailable('not_found');
}

JCRequest req = JC.request.get();

List<FileItem> items = req.getFormItems();

def result = [];
for(FileItem x in items) {
	String content_type = x.contentType;
	String ct_code = content_type.split('\\/')[0];
	def input_stream = x.inputStream;
	def define_name = MD5Util.getStringMD5(x.name + nextInt(1000, 9999));
	def filename = g_id + '_' + define_name;
	//def up_result = QiniuSource.getQiniuPower().upload(input_stream, filename);
	
	def file_path = cdn_root_path + '/' + rel_path;	// + '/' + filename;
	File file_obj = new File(file_path);
	if(!file_obj.exists()) {
		file_obj.mkdir();
	}
	file_path = file_path + '/' + filename;
	FileUtils.writeByteArrayToFile(new File(file_path), x.get());

	//上传成功
	GoodsImg img = new GoodsImg();
	img.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	img.content_type = x.contentType;
	img.flag = 0;
	img.goods_id = goods.id;
	img.img_type = '0000';
	//img.img_url = filename;
	img.img_url = rel_path + '/' + filename;
	img.tycode = '200';
	img.ct_code = ct_code;
	img = g_ser.save_img(img);
	result.add(img);
}

return SimpleAjax.available('', result);



def nextInt(final int min, final int max){
	Random rand= new Random(); 
    int tmp = Math.abs(rand.nextInt());
    return tmp % (max - min + 1) + min;
}
