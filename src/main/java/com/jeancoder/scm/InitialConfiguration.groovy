package com.jeancoder.scm

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.scm.ready.util.FuncUtil
import com.jeancoder.scm.ready.util.NativeUtil

JC.interceptor.add("project/PreInterceptor", "project/PostInterceptor");
JC.interceptor.add("token/PreInterceptor", null);
JC.interceptor.add("mod/PreInterceptor", null);
JC.interceptor.add('general/PreInterceptor', null);



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
NativeUtil.connect('project', '/incall/mod/mods', [app_code:'scm', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);
