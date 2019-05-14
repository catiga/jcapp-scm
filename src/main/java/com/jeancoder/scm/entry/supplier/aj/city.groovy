package com.jeancoder.scm.entry.supplier.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower

def id = JC.request.param('id');

List<CommunicationParam> params = new ArrayList<CommunicationParam>();
CommunicationParam param = null;
if(id) {
	param = new CommunicationParam('id', id);
	params.add(param);
}
CommunicationPower systemCaller = CommunicationSource.getCommunicator("project");
def ret = systemCaller.doworkAsString("/incall/city", params);

return ret;