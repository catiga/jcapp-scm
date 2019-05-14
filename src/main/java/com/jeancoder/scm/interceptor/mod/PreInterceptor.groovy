package com.jeancoder.scm.interceptor.mod

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.dto.AppFunction
import com.jeancoder.scm.ready.dto.SysProjectInfo
import com.jeancoder.scm.ready.util.FuncUtil
import com.jeancoder.scm.ready.util.GlobalHolder
import com.jeancoder.scm.ready.util.NativeUtil

@urlmapped("/")
@urlpassed(['/incall', '/outcall'])


def mod_g = FuncUtil.build(1, '货品管理', null, 'goods', 'fa-shopping-cart');
def mod_g_ = FuncUtil.build(101, '货品库', 1, 'goods/index', '', 2);
def mod_g_5 = FuncUtil.build(105, '套餐组合', 1, 'gdpack/index', '', 2);
def mod_g_6 = FuncUtil.build(106, '合成品管理', 1, 'gdmerge/index', '', 2);
def mod_g_model = FuncUtil.build(103, 'SKU模型', 1, 'gm/index', '', 2);

def mod_g_cats = FuncUtil.build(104, '分组管理', null, 'catalog', 'fa-pie-chart');
def mod_g_cats_1 = FuncUtil.build(1041, '分组设置', 104, 'catalog/index', 'fa-pie-chart', 2);
def mod_g_cats_2 = FuncUtil.build(1042, '渠道设置', 104, 'dsc/index', 'fa-pie-chart', 2);

def mod_g_online_ = FuncUtil.build(7, '在售商品管理', null, 'forsale', 'fa-joomla');
def mod_g_online_goods = FuncUtil.build(701, '商品', 7, 'forsale/index', 'fa-joomla', 2);
def mod_g_online_pack = FuncUtil.build(702, '套餐组合', 7, 'forsale/pack', 'fa-joomla', 2);
def mod_g_online_merge = FuncUtil.build(703, '合成品', 7, 'forsale/merge', 'fa-joomla', 2);

def mod_inv = FuncUtil.build(2, '库存管理', null, 'invbatch', 'fa-empire');
def mod_inv_bat = FuncUtil.build(201, '批次管理', 2, 'invbatch/index', '', 2);
def mod_inv_whs = FuncUtil.build(202, '仓库信息', 2, 'wh/list', '', 2);
def mod_stock_take = FuncUtil.build(203, '库存盘点', 2, 'stocktake/index', '', 2);
def mod_stock_table = FuncUtil.build(204, '库存状态表', 2, 'stocksst/index', '', 2);
def mod_stock_gs_form = FuncUtil.build(205, '库存变动查询', 2, 'stockio/index', '', 2);
def mod_stock_warning = FuncUtil.build(206, '库存预警', 2, 'sw/index', '', 2);

def mod_supp = FuncUtil.build(3, '供应商', null, 'supplier/list', 'fa-support');

def mod_stock = FuncUtil.build(4, '出库管理', null, 'stockout', 'fa-ioxhost');
def mod_stock_1 = FuncUtil.build(401, '领用登记', 4, 'stockout/upub/index', '', 2);
def mod_stock_2 = FuncUtil.build(402, '报损登记', 4, 'stockout/udam/index', '', 2);
def mod_stock_3 = FuncUtil.build(403, '调拨出库', 4, 'stockout/alloc/index', '', 2);

def mod_stock_in = FuncUtil.build(5, '入库管理', null, 'stockin', 'fa-pagelines');
def mod_stock_in_1 = FuncUtil.build(501, '进货入库', 5, 'stockin/buyin/index', '', 2);
def mod_stock_in_2 = FuncUtil.build(502, '调拨入库', 5, 'stockin/alloc/index', '', 2);
def mod_stock_in_3 = FuncUtil.build(503, '报溢登记', 5, 'stockin/spillover/index', '', 2);

def mod_my_store = FuncUtil.build(6, '门店信息', null, 'store/list', 'fa-support');

def mod_order_ = FuncUtil.build(8, '销售管理', null, 'order', 'fa-ge');
def mod_order_list_ = FuncUtil.build(801, '销售订单列表', 8, 'order/index', '', 2);

def result = [mod_g, mod_g_, mod_g_5, mod_g_6, mod_g_model];
result.addAll([mod_g_cats, mod_g_cats_1, mod_g_cats_2]);
result.addAll([mod_g_online_,mod_g_online_goods, mod_g_online_pack, mod_g_online_merge]);
result.addAll([mod_supp]);
result.addAll([mod_stock, mod_stock_1, mod_stock_2, mod_stock_3]);
result.addAll([mod_stock_in, mod_stock_in_1, mod_stock_in_2, mod_stock_in_3]);
result.addAll([mod_inv, mod_inv_bat, mod_inv_whs, mod_stock_take, mod_stock_table, mod_stock_gs_form, mod_stock_warning]);
result.add(mod_my_store);

result.addAll([mod_order_, mod_order_list_]);

JCRequest request = JC.request.get();
def uri = request.getRequestURI();
def context = request.getContextPath();

def uri_without_code = uri[context.length()+1..-1];
if(uri_without_code.endsWith("/"))
	uri_without_code = uri_without_code[0..-2];
request.setAttribute("__now_uri_", uri_without_code);

List<AppFunction> functions = NativeUtil.connectAsArray(AppFunction, 'project', '/incall/mod/mods', [user_id:GlobalHolder.token?.user?.id, pid:GlobalHolder.proj?.id, app_code:'scm', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);
Map<AppFunction, List<AppFunction>> my_funcs = my_funcs(functions);
request.setAttribute("user_roles_functions", my_funcs);


return true;




def get_by_id(def id, List<AppFunction> functions) {
	for(AppFunction f : functions) {
		if(f.id==id) {
			return f;
		}
	}
	return null;
}



def Map<AppFunction, List<AppFunction>> my_funcs(List<AppFunction> functions) {
	Map<AppFunction, List<AppFunction>> parent_functions = new LinkedHashMap<AppFunction, List<AppFunction>>();
	SysProjectInfo project = GlobalHolder.getProj();
	if(functions!=null&&!functions.isEmpty()) {
		for(AppFunction f : functions) {
			AppFunction parent_f = null;
			List<AppFunction> result_f = new ArrayList<AppFunction>();
			
			//只取两级的判断
			if(f.getLevel().equals(1)) {
				//表示当前这个为一级模块
				parent_f = f;
				for(AppFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			} else if(f.getLevel().equals(2)) {
				//表示当前这个为二级模块
				parent_f = get_by_id(f.getParent_id(), functions);
				if(parent_f==null) {
					continue;
				}
				for(AppFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			}
			parent_functions.put(parent_f, result_f);
		}
	}
	return parent_functions;
}