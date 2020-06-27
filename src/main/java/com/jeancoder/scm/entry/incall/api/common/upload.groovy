package com.jeancoder.scm.entry.incall.api.common

import java.sql.Timestamp

import org.apache.commons.fileupload.FileItem
import org.apache.commons.io.FileUtils

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.MD5Util
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsImg
import com.jeancoder.scm.ready.incall.api.ProtObj

JCLogger logger = JCLoggerFactory.getLogger('');

//def cdn_root_path = '/data/cdn/jc';
def cdn_root_path = '/Users/jackielee/Desktop';
def rel_path = 'scm';

def upload_cat = JC.request.param('uc');
if(!upload_cat) {
	return ProtObj.fail(110001, '请确认上传分类参数');
}
rel_path = rel_path + '/' + upload_cat;

logger.info('cdn_root_path1=' + cdn_root_path);

SimpleAjax sys_conf = JC.internal.call(SimpleAjax, 'project', '/sys/get_root_path', null);
if(sys_conf && sys_conf.available) {
	cdn_root_path = sys_conf.data;
}
logger.info('cdn_root_path2=' + cdn_root_path);

List<FileItem> items = JC.request.get().getFormItems();

def result = [];
for(FileItem x in items) {
	String content_type = x.contentType;
	String ct_code = content_type.split('\\/')[0];
	def input_stream = x.inputStream;
	def define_name = MD5Util.getStringMD5(x.name + nextInt(1000, 9999));
	def filename = this.nextInt(100, 999) + '_' + define_name;
	//def up_result = QiniuSource.getQiniuPower().upload(input_stream, filename);
	
	def file_path = cdn_root_path + '/' + rel_path;	// + '/' + filename;
	File file_obj = new File(file_path);
	if(!file_obj.exists()) {
		file_obj.mkdir();
	}
	file_path = file_path + '/' + filename;
	try {
		FileUtils.writeByteArrayToFile(new File(file_path), x.get());
	}catch(any) {
		any.printStackTrace();
	}

	//上传成功
	result.add(rel_path + '/' + filename);
}

return ProtObj.success(result);

def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}


