package com.sd.libcore.business.stream;

import com.sd.lib.stream.FStream;

public interface BSProgress extends FStream
{
    void onBsShowProgress(String msg);

    void onBsHideProgress();
}
