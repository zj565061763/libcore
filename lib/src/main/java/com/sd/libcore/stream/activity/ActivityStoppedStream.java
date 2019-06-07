package com.sd.libcore.stream.activity;

import android.app.Activity;

public interface ActivityStoppedStream extends FActivityStream
{
    void onActivityStopped(Activity activity);
}
