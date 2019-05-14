package com.jeancoder.scm.ready.util

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class JsonUtil {

	static JCLogger LOGGER = LoggerSource.getLogger(JsonUtil.class.getName());
	private static ObjectMapper m = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private static JsonFactory jf = new JsonFactory();

	
	static def camle(String s){
		return s;
	}
	
	public static<T, X> X convert(Class<T> mapped, def json) {
		if(mapped.isArray()) {
			return convertArray(mapped, json);
		} else {
			return convertObj(mapped, json);
		}
	}
	
	public static <T> T convertObj(Class<T> mapped, def json) {
		try {
			def gdsObj = mapped.newInstance();
			def gdsJson = new JsonSlurper().parseText(json);
			gdsJson.each {
				Map.Entry entry ->
				String propName = camle(entry.key);
				if(gdsObj.metaClass.hasProperty(gdsObj,propName)){
					gdsObj[propName] = entry.value
				}
			}
			return gdsObj;
		} catch(Exception e) {
			LOGGER.error("convert json error:" + json);
			return null;
		}
	}
	
	public static <T> T[] convertArray(Class<T> mapped, def json) {
		try {
			JsonSlurper js = new JsonSlurper();
			JsonOutput jo = new JsonOutput();
			def array = js.parseText(json);
			def retarry = [];
			for(x in array) {
				T obj = convertObj(mapped, jo.toJson(x));
				retarry.add(obj);
			}
			return retarry;
		} catch(Exception e) {
			LOGGER.error("convert json error:" + json);
			return null;
		}
	}
	
	public static String toJson(Object pojo){
		return toJson(pojo, false);
	}
	public static String toJson(Object pojo, boolean prettyPrint) {
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator jg = jf.createGenerator(sw);
			if (prettyPrint) {
				jg.useDefaultPrettyPrinter();
			}
			m.writeValue(jg, pojo);
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
}
