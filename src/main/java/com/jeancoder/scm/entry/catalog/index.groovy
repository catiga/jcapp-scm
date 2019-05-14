package com.jeancoder.scm.entry.catalog

import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.scm.ready.entity.Dsc
import com.jeancoder.scm.ready.util.GlobalHolder

Result view = new Result();
view.setView("catalog/index");

//获取当前pid的可用渠道列表
def result = JcTemplate.INSTANCE().find(Dsc, 'select * from Dsc where pid=? and flag!=? order by c_time desc', GlobalHolder.proj.id, -1);

view.addObject('dsc_result', result);

//def img_root = 'https://cdn.iplaysky.com'
def img_root = GlobalHolder.INSTANCE.img_path();
view.addObject('root_path', img_root);

return view;