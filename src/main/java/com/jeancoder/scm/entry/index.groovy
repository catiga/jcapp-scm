package com.jeancoder.scm.entry

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.power.MemPower
import com.jeancoder.jdbc.sql.SqlFragment
import com.jeancoder.jdbc.sql.SqlParser



String sql = "select * from sys_project_info where id=1 group by id on 3=4 having ui>0 limit 1, 100";

//sql = "select * from SysFunction where flag!=? and level=? order by sort asc";

sql = "select * from SysFunction where flag!=? and id in (select mod_id from ProjectMod where flag!=? and proj_id=?)";
sql = "select * from SysFunction where flag!=? and id=? order by seq asc, o_time asc";

sql = "select s.*, g.id as A from GoodsSku s join GoodsInfo g where id in (select mod_id from GoodsSku where flag!=? and proj_id=?)";
System.out.println(sql);

SqlParser par = new SqlParser(sql);

for(SqlFragment sf : par.frags) {
	System.out.println(sf.action + " " + sf.target);
	//System.out.println("---" + sf.target);
}
System.out.println(par.clearSql());
System.out.println(par.toCountSql());

MemPower mp = MemSource.getMemPower();
mp.set('test_key', 'tengteng welcome');

return 'success';



