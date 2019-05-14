package com.jeancoder.scm.entry.gdpack.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.ajax.RetObj
import com.jeancoder.scm.ready.ajax.SimpleAjax
import com.jeancoder.scm.ready.entity.GoodsPack
import com.jeancoder.scm.ready.entity.GoodsPackItem
import com.jeancoder.scm.ready.entity.GoodsPackItemVert
import com.jeancoder.scm.ready.service.CmpGoodsService

CmpGoodsService cmp_g_s = CmpGoodsService.INSTANCE();

def pack_id = JC.request.param('pack_id');
GoodsPack pack = cmp_g_s.get_pack(pack_id);
if(!pack) {
	return SimpleAjax.notAvailable('obj_not_found,套餐未找到');
}
List<GoodsPackItemVert> items = cmp_g_s.find_pack_all_verts(pack)

return SimpleAjax.available('', items);




