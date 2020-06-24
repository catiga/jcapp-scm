package com.jeancoder.scm.entry.incall.ampi.goods

import com.jeancoder.core.util.JackSonBeanMapper

def all_sku_props = [];
def d1 = JackSonBeanMapper.jsonToMap('{"甜度":"中","大小":"小"}');
def d2 = JackSonBeanMapper.jsonToMap('{"甜度":"高","大小":"大"}');

all_sku_props.add(d1);
all_sku_props.add(d2);


for(x in all_sku_props) {
	x.each{
		entry->
		println entry.key + '0000' + entry.value;
	}
}
