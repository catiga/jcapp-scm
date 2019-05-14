package com.jeancoder.scm.entry

import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.MemPower


MemPower mp = MemSource.getMemPower();
def test_key = mp.delete('test_key');

return 'delete_success=' + test_key;



