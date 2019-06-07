package com.sd.libcore.stream.activity;

import android.app.Activity;

import com.sd.lib.stream.FStream;

public interface ActivityStoppedStream extends FStream
{
    void onActivityStopped(Activity activity);
}
