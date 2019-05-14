package com.jeancoder.scm.entry.dsc

import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Catalog
import com.jeancoder.scm.ready.entity.Dsc
import com.jeancoder.scm.ready.service.CatalogService
import com.jeancoder.scm.ready.util.GlobalHolder

def result = JcTemplate.INSTANCE().find(Dsc, 'select * from Dsc where pid=? and flag!=? order by c_time desc', GlobalHolder.proj.id, -1);

Result view = new Result();
view.setView("dsc/index");
view.addObject('result', result);
return view;