package com.sd.libcore.stream.activity;

import android.app.Activity;

import com.sd.lib.stream.FStream;

public interface ActivityDestroyedStream extends FStream
{
    void onActivityDestroyed(Activity activity);
}
