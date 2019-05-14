package com.jeancoder.scm.ready.service

import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CommonJcDaoTemplate
import com.jeancoder.scm.ready.entity.Provider
import com.jeancoder.scm.ready.util.GlobalHolder

class SupplierService {
	
	private static final SupplierService __instance__ = new SupplierService();
	
	public static SupplierService INSTANCE() {
		return __instance__;
	}

	public JcPage<Provider> find(JcPage<Provider> page) {
		JcTemplate jc_template = JcTemplate.INSTANCE();
		def pid = GlobalHolder.proj.id;
		page = jc_template.find(Provider.class, page, 'select * from Provider where pid=?', pid);
		return page;
	}
	
	public Provider get(def pid = GlobalHolder.proj.id, def id) {
		JcTemplate jc_template = JcTemplate.INSTANCE();
		return jc_template.get(Provider.class, 'select * from Provider where pid=? and id=?', pid, id);
	}
	
	public void update(Provider entity) {
		JcTemplate jc_template = JcTemplate.INSTANCE();
		jc_template.update(entity);
	}
	
	public void save(Provider entity) {
		JcTemplate jc_template = JcTemplate.INSTANCE();
		jc_template.save(entity);
	}
}
