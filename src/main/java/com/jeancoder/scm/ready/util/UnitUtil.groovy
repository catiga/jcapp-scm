package com.jeancoder.scm.ready.util

class UnitUtil {

	private static List<UnitCmp> _unit_ = new ArrayList<?>();
	
	private static List<UnitCmp> _spec_unit_  = new ArrayList<?>();
	
	static {
		//1开头为无意义的个数单位
		_unit_.add(new UnitCmp('101', '件'));
		_unit_.add(new UnitCmp('102', '瓶'));
		_unit_.add(new UnitCmp('103', '袋'));
		_unit_.add(new UnitCmp('104', '盒'));
		_unit_.add(new UnitCmp('105', '箱'));
		_unit_.add(new UnitCmp('106', '个'));
		_unit_.add(new UnitCmp('107', '份'));
		_unit_.add(new UnitCmp('108', '杯'));
		_unit_.add(new UnitCmp('109', '毫升'));
		_unit_.add(new UnitCmp('110', '升'));
		
		//2开头为重量单位
		_unit_.add(new UnitCmp('201', '克'));
		_unit_.add(new UnitCmp('202', '千克'));
		_unit_.add(new UnitCmp('203', '斤'));
		_unit_.add(new UnitCmp('204', '两'));
		
		//3开头为长度单位
		_unit_.add(new UnitCmp('301', '米'))
		
		//9开头为任意自定义单位
		_unit_.add(new UnitCmp('901', '本'))
		_unit_.add(new UnitCmp('902', '部'))
		_unit_.add(new UnitCmp('903', '册'))
		_unit_.add(new UnitCmp('904', '小时'))
		_unit_.add(new UnitCmp('905', '罐'))
		_unit_.add(new UnitCmp('906', '壶'))
		_unit_.add(new UnitCmp('907', '盘'))
		_unit_.add(new UnitCmp('908', '听'))
		_unit_.add(new UnitCmp('909', '桶'))
		_unit_.add(new UnitCmp('910', '筒'))
		_unit_.add(new UnitCmp('911', '碗'))
		_unit_.add(new UnitCmp('912', '盏'))
		_unit_.add(new UnitCmp('913', '板'))
		_unit_.add(new UnitCmp('914', '包'))
		_unit_.add(new UnitCmp('915', '抽'))
		_unit_.add(new UnitCmp('916', '串'))
		_unit_.add(new UnitCmp('917', '对'))
		_unit_.add(new UnitCmp('918', '挂'))
		_unit_.add(new UnitCmp('919', '面'))
		_unit_.add(new UnitCmp('920', '排'))
		_unit_.add(new UnitCmp('921', '束'))
		_unit_.add(new UnitCmp('922', '双'))
		_unit_.add(new UnitCmp('923', '套'))
		_unit_.add(new UnitCmp('924', '提'))
		_unit_.add(new UnitCmp('925', '条'))
		_unit_.add(new UnitCmp('926', '团'))
		_unit_.add(new UnitCmp('927', '扎'))
		_unit_.add(new UnitCmp('928', '组'))
		_unit_.add(new UnitCmp('929', '筐'))
		_unit_.add(new UnitCmp('930', '捆'))
		_unit_.add(new UnitCmp('931', '篮'))
		_unit_.add(new UnitCmp('932', '次'))
		_unit_.add(new UnitCmp('933', '刀'))
		_unit_.add(new UnitCmp('934', '顶'))
		_unit_.add(new UnitCmp('935', '朵'))
		_unit_.add(new UnitCmp('936', '封'))
		_unit_.add(new UnitCmp('937', '副'))
		_unit_.add(new UnitCmp('938', '根'))
		_unit_.add(new UnitCmp('939', '架'))
		_unit_.add(new UnitCmp('940', '节'))
		_unit_.add(new UnitCmp('941', '卷'))
		_unit_.add(new UnitCmp('942', '棵'))
		_unit_.add(new UnitCmp('943', '客'))
		_unit_.add(new UnitCmp('944', '块'))
		_unit_.add(new UnitCmp('945', '粒'))
		_unit_.add(new UnitCmp('946', '辆'))
		_unit_.add(new UnitCmp('947', '枚'))
		_unit_.add(new UnitCmp('948', '片'))
		_unit_.add(new UnitCmp('949', '台'))
		_unit_.add(new UnitCmp('950', '头'))
		_unit_.add(new UnitCmp('951', '张'))
		_unit_.add(new UnitCmp('952', '支'))
		_unit_.add(new UnitCmp('953', '只'))
	}
	
	public static List<UnitCmp> toList() {
		List<UnitCmp> d = new ArrayList<>();
		for(UnitCmp uc : _unit_) {
			d.add(uc);
		}
		return d;
	}
	
}
