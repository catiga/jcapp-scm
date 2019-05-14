package com.jeancoder.scm.ready.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

	public static Timestamp getTimestamp(Date time) {
		return new Timestamp(time.getTime());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}

	public static boolean isEmail(String line) {
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(line);
		return m.find();
	}
	
	public static Date getDateByStr(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}
	
	public static Integer compute_data_diff(String a, String b) {
		try {
			return Integer.valueOf(a) - Integer.valueOf(b);
		} catch(Exception e){
			return null;
		}
	}
	
	public static String compute_add(String a, String b) {
		BigDecimal bd = new BigDecimal(a);
		BigDecimal bd_2 = new BigDecimal(b);
		bd = bd.add(bd_2);
		return bd.toString();
	}
	
	public static String compute_subtract(String a, String b) {
		BigDecimal bd = new BigDecimal(a);
		BigDecimal bd_2 = new BigDecimal(b);
		bd = bd.subtract(bd_2);
		if(bd.floatValue()<=0) {
			return "0";
		} else {
			return bd.toString();
		}
	}
	
	public static String compute_multiply(String a, String b) {
		BigDecimal bd = new BigDecimal(a);
		BigDecimal bd_2 = new BigDecimal(b);
		bd = bd.multiply(bd_2);
		return bd.toString();
	}
	
	public static boolean isNumber(String string) {
		if(string==null||string.trim().equals("")) {
			return false;
		}
		int point_num = 0;
	    for (int i = 0; i < string.length(); i++) {
	    	String c = string.charAt(i).toString();
	    	if(i==0) {
	    		if(c.equals("-")||c.equals("+")) {
	    			continue;
	    		}
	    	}
	    	if(c.equals(".")) {
	    		point_num++;
	    		continue;
	    	} else {
	    		if (!Character.isDigit(string.charAt(i))) {
		            return false;
		        }
	    	}
	    }
	    if(string.startsWith(".")||string.endsWith(".")) {
	    	return false;
	    } else {
	    	if(point_num>1) {
	    		return false;
	    	}
	    }
	    return true;
	}
	
	public static void main(String[] argc) {
		String a = "89000";
		boolean c = isNumber(a);
		System.out.println(c);
	}

}
