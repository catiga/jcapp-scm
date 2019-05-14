package com.jeancoder.scm.ready.util

import com.jeancoder.scm.ready.dto.AuthToken
import com.jeancoder.scm.ready.dto.DataTrans
import com.jeancoder.scm.ready.dto.SysProjectInfo

class GlobalHolder {
	
	//def static _img_path_prefix_ = 'https://huacai.mt.92youpin.com/img_server';
	
	def _img_path_prefix_ = getProj()?'//' + getProj().domain + '/img_server':'//';
	
	static GlobalHolder INSTANCE = new GlobalHolder();
	
	def img_path() {
		return getProj()?'//' + getProj().domain + '/img_server':'//';
	}

	private static ThreadLocal<SysProjectInfo> _sys_project_ = new ThreadLocal<SysProjectInfo>();
	
	private static ThreadLocal<AuthToken> _token_ = new ThreadLocal<AuthToken>();
	
	private static final ThreadLocal<DataTrans> _data_trans_ = new ThreadLocal<DataTrans>();
	
	public static void setProj(SysProjectInfo e) {
		_sys_project_.set(e);
	}
	
	public static SysProjectInfo getProj() {
		return _sys_project_?.get();
	}
	
	public static void setToken(AuthToken token) {
		_token_.set(token);
	}
	
	public static AuthToken getToken() {
		return _token_.get();
	}
	
	static setDt(DataTrans dt) {
		_data_trans_.set(dt);
	}
	static getDt() {
		_data_trans_.get();
	}
	
	public static void remove() {
		_sys_project_.remove();
		_token_.remove();
		_data_trans_.remove();
	}
}
