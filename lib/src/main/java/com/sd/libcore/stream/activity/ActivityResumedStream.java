package com.sd.libcore.stream.activity;

import android.app.Activity;

import com.sd.lib.stream.FStream;

@Deprecated
public interface ActivityResumedStream extends FStream
{
    void onActivityResumed(Activity activity);
}
