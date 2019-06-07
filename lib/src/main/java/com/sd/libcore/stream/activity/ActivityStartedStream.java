package com.sd.libcore.stream.activity;

import android.app.Activity;

public interface ActivityStartedStream extends FActivityStream
{
    void onActivityStarted(Activity activity);
}
