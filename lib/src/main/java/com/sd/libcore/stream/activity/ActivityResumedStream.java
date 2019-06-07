package com.sd.libcore.stream.activity;

import android.app.Activity;

import com.sd.lib.stream.FStream;

public interface ActivityResumedStream extends FStream
{
    void onActivityResumed(Activity activity);
}
