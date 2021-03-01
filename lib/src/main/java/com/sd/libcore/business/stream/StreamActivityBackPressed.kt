package com.sd.libcore.business.stream;

import com.sd.lib.stream.FStream;

public interface StreamActivityBackPressed extends FStream
{
    boolean onActivityBackPressed();
}
