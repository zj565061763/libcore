package com.sd.libcore.stream.activity;

import android.app.Activity;

import com.sd.lib.stream.FStream;

@Deprecated
public interface ActivityPausedStream extends FStream
{
    void onActivityPaused(Activity activity);
}
