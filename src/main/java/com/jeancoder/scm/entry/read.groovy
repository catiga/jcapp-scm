package com.jeancoder.scm.entry

import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.MemPower


MemPower mp = MemSource.getMemPower();
def test_key = mp.get('test_key');

return 'read_success=' + test_key;



