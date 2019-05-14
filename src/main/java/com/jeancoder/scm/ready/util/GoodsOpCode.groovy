package com.jeancoder.scm.ready.util

import java.util.Arrays
import java.util.LinkedHashMap
import java.util.LinkedList
import java.util.List
import java.util.Map

class GoodsOpCode {
	
	static List<GoodsOpCode> all_codes = null;
	static {
		GoodsOpCode op_1_1000 = new GoodsOpCode(1, "1000", "进货入库", true, false, false);
		GoodsOpCode op_1_1001 = new GoodsOpCode(1, "1001", "调拨入库", true, true, false);
		GoodsOpCode op_1_2001 = new GoodsOpCode(1, "2001", "销售退单", false, false, false);
		GoodsOpCode op_1_3001 = new GoodsOpCode(1, "3001", "报溢登记", false, false, false);
		GoodsOpCode op_1_9001 = new GoodsOpCode(1, "9001", "其他入库", true, false, false);
		
		GoodsOpCode op_2_2000 = new GoodsOpCode(2, "2000", "销售出库", false, false, false);
		GoodsOpCode op_2_3000 = new GoodsOpCode(2, "3001", "调拨出库", true, false, true);
		GoodsOpCode op_2_4000 = new GoodsOpCode(2, "4000", "报损出库", true, false, false);
		GoodsOpCode op_2_5000 = new GoodsOpCode(2, "5000", "领用出库", true, false, false);
		GoodsOpCode op_2_9001 = new GoodsOpCode(2, "9001", "其他出库", true, false, false);
		
		all_codes = Arrays.asList(op_1_1000, op_1_1001, op_1_2001, op_1_3001, op_1_9001, op_2_2000, op_2_3000, op_2_4000, op_2_9001, op_2_5000);
	}

	/**
	 * 10*：入库类
	 * 20*：销售类
	 * 30*：出入库类
	 * 1000:进货入库
	 * 1001:调拨入库
	 * 
	 * 2000:销售出库
	 * 2001:销售退单
	 * 
	 * 3001:调拨出库
	 * 
	 * 4000:丢失出库
	 */
	private Integer inHouse;
	
	private String code;
	
	private String name;
	
	private boolean allowManual = true;
	
	private boolean needSource = false;
	
	private boolean needAim = false;
	
	public GoodsOpCode(Integer inHouse, String code, String name, boolean allowManual, boolean needSource, boolean needAim) {
		super();
		this.inHouse = inHouse;
		this.code = code;
		this.name = name;
		this.allowManual = allowManual;
		this.needSource = needSource;
		this.needAim = needAim;
	}

	public Integer getInHouse() {
		return inHouse;
	}

	public void setInHouse(Integer inHouse) {
		this.inHouse = inHouse;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAllowManual() {
		return allowManual;
	}

	public void setAllowManual(boolean allowManual) {
		this.allowManual = allowManual;
	}

	public boolean isNeedSource() {
		return needSource;
	}

	public void setNeedSource(boolean needSource) {
		this.needSource = needSource;
	}

	public boolean isNeedAim() {
		return needAim;
	}

	public void setNeedAim(boolean needAim) {
		this.needAim = needAim;
	}

	public static List<GoodsOpCode> to_list() {
		return all_codes;
	}
	
	public static Map<Integer, String> op_code_cat() {
		Map<Integer, String> ret_map = new LinkedHashMap<>();
		ret_map.put(1, "入库");
		ret_map.put(2, "出库");
		return ret_map;
	}
	
	
	public static Map<Integer, String> op_code_cat_in() {
		Map<Integer, String> ret_map = new LinkedHashMap<>();
		ret_map.put(1, "入库");
		return ret_map;
	}
	
	public static List<GoodsOpCode> in_to_list() {
		List<GoodsOpCode> ret_list = new LinkedList<GoodsOpCode>();
		for(GoodsOpCode goc : all_codes) {
			if(goc.getInHouse().equals(1)) {
				ret_list.add(goc);
			}
		}
		
		return ret_list;
	}
	
	
	public static Map<Integer, String> op_code_cat_out() {
		Map<Integer, String> ret_map = new LinkedHashMap<>();
		ret_map.put(2, "出库");
		return ret_map;
	}
	
	public static List<GoodsOpCode> out_to_list() {
		List<GoodsOpCode> ret_list = new LinkedList<GoodsOpCode>();
		for(GoodsOpCode goc : all_codes) {
			if(goc.getInHouse().equals(2)) {
				ret_list.add(goc);
			}
		}
		
		return ret_list;
	}
	
	public static GoodsOpCode get_by_code(String code) {
		for(GoodsOpCode goc : all_codes) {
			if(goc.getCode().equals(code)) {
				return goc;
			}
		}
		return null;
	}
}