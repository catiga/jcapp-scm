package com.jeancoder.scm.ready.util

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.power.CommunicationMethod
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.dto.AuthUser

class RemoteUtil {
	
	static JCLogger LOGGER = JCLoggerFactory.getLogger(RemoteUtil.class.name);
	
	public static def connect(def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params, CommunicationMethod.POST);
		return ret;
	}

	public static <T> T connect(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		
		try {
			T obj = JackSonBeanMapper.fromJson(ret, mapclass);
			return obj;
		}catch(any){
			LOGGER.error('', any)
			return null;
		}
	}
	
	public static <T> T connectAsArray(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		
		
		try {
			T obj = JsonUtil.convertArray(mapclass, ret);
			return obj;
		}catch(any){
			LOGGER.error('', any)
			return null;
		}
	}
	
}
