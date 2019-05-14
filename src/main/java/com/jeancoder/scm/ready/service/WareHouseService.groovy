package com.jeancoder.scm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.entity.WareHouse
import com.jeancoder.scm.ready.util.GlobalHolder

class WareHouseService {

	private static final WareHouseService __instance__ = new WareHouseService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public static WareHouseService INSTANCE() {
		return __instance__;
	}
	
	public JcPage<WareHouse> find(JcPage<WareHouse> page, def pid = GlobalHolder.proj.id) {
		String sql = 'select * from WareHouse where flag!=?';
		def param = []; param.add(-1);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(now_project.root!=1) {
			sql += ' and pid=?';
			param.add(now_project.id);
		}
		sql += ' order by c_time desc';
		return jc_template.find(WareHouse.class, page, sql, param.toArray());
	}
	
	public List<WareHouse> find(def pid = GlobalHolder.proj.id) {
		String sql = 'select * from WareHouse where flag!=?';
		def param = []; param.add(-1);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(now_project.root!=1) {
			sql += ' and pid=?';
			param.add(now_project.id);
		}
		sql += ' order by c_time desc';
		return jc_template.find(WareHouse.class, sql, param.toArray());
	}
	
	public WareHouse get(def id, def pid = GlobalHolder.proj.id) {
		if(!id) {
			return null;
		}
		String sql = 'select * from WareHouse where flag!=? and id=?';
		def param = []; param.add(-1); param.add(id);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(now_project.root!=1) {
			sql += ' and pid=?';
			param.add(now_project.id);
		}
		return jc_template.get(WareHouse.class, sql, param.toArray());
	}
	
	public void update(WareHouse e) {
		e.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		jc_template.update(e);
	}
	
	public WareHouse save(WareHouse e) {
		e.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		e.pid = GlobalHolder.proj.id;
		e.flag = 0;
		BigInteger id = jc_template.save(e);
		e.id = id;
		return e;
	}
	
	def get_default_warehouse_by_store(def store_id, def pid = GlobalHolder.proj.id) {
		String sql = 'select * from WareHouse where flag!=?';
		def param = [];
		param.add(-1);
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		if(now_project.root!=1) {
			sql += '  and pid=?';
			param.add(now_project.id);
		}
		if(store_id) {
			sql += ' and store_id=?';
			param.add(store_id);
		}
		sql += ' order by sort asc';
		return jc_template.get(WareHouse, sql, param.toArray());
	}
}
