package com.jeancoder.scm.ready.util;

import java.sql.Timestamp
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat _sdf_yyyyMMdd_ = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat _sdf_yyyyMMddHHmmss_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat _sdf_HHmmss_ = new SimpleDateFormat("HH:mm:ss");
	
	public static void main(String[] argc) {
		String c_date = "2017-01-24";
		String s_date = "2017-01-19";
		String e_date = "2017-01-23";
		boolean ret = is_date_in(c_date, s_date, e_date);
		System.out.println(ret);
	}
	
	public static boolean is_date_in(String aim_date, String start, String end) {
		Date d_aim_date = parse_yyyyMMdd(aim_date);
		Date d_start = parse_yyyyMMdd(start);
		Date d_end = parse_yyyyMMdd(end);
		
		int start_match = d_start.compareTo(d_aim_date);
		int end_match = d_end.compareTo(d_aim_date);
		//if(start_match==-1&&end_match==1) {
		boolean ret = (start_match<=0&&end_match>=0);
		return ret;
	}
	
	public static Date parse_(String format, String time_str) {
		SimpleDateFormat _sdf = new SimpleDateFormat(format);
		try {
			return _sdf.parse(time_str);
		}catch(Exception e){
			return null;
		}
	}
	
	public static String format_(String format, Date time_str) {
		SimpleDateFormat _sdf = new SimpleDateFormat(format);
		return _sdf.format(time_str);
	}
	
	public static Date parse_yyyyMMdd(String yyyyMMdd) {
		if(yyyyMMdd==null) {
			return null;
		}
		Date result = null;
		try {
			result = _sdf_yyyyMMdd_.parse(yyyyMMdd);
		} catch(Exception e) {
		}
		return result;
	}
	
	public static Timestamp generate(String date) {
		Date d = parse_yyyyMMdd(date);
		if(d==null) {
			d = parse_yyyyMMddHHmmss(date);
		}
		if(d==null) return null;
		return new Timestamp(d.getTime());
	}
	
	public static Date parse_yyyyMMddHHmmss(String yyyyMMddHHmmss) {
		if(yyyyMMddHHmmss==null) {
			return null;
		}
		Date result = null;
		try {
			result = _sdf_yyyyMMddHHmmss_.parse(yyyyMMddHHmmss);
		} catch(Exception e) {
		}
		return result;
	}
	
	public static String format_yyyyMMdd(Date yyyyMMdd) {
		if(yyyyMMdd==null) {
			return null;
		}
		return _sdf_yyyyMMdd_.format(yyyyMMdd);
	}
	
	public static String format_yyyyMMddHHmmss(Date yyyyMMddHHmmss) {
		if(yyyyMMddHHmmss==null) {
			return null;
		}
		return _sdf_yyyyMMddHHmmss_.format(yyyyMMddHHmmss);
	}
	
	public static String extract_time(String yyyyMMddHHmmss) {
		Date d = parse_yyyyMMddHHmmss(yyyyMMddHHmmss);
		if(d!=null) {
			return _sdf_HHmmss_.format(d);
		}
		return null;
	}
}
