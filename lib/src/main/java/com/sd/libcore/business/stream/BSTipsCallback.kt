package com.sd.libcore.business.stream;

import androidx.annotation.Nullable;

import com.sd.lib.stream.FStream;

public interface BSTipsCallback extends FStream
{
    void bsShowTips(@Nullable String tips);
}
