package com.sd.libcore.stream.activity;

import android.app.Activity;

import com.sd.lib.stream.FStream;

public interface ActivityStartedStream extends FStream
{
    void onActivityStarted(Activity activity);
}
