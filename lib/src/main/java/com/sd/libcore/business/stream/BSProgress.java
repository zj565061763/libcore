package com.sd.libcore.business.stream;

import androidx.annotation.Nullable;

import com.sd.lib.stream.FStream;

public interface BSProgress extends FStream
{
    void bsShowProgress(@Nullable String msg);

    void bsHideProgress();
}
