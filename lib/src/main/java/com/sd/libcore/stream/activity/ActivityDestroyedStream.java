package com.sd.libcore.stream.activity;

import android.app.Activity;

public interface ActivityDestroyedStream extends FActivityStream
{
    void onActivityDestroyed(Activity activity);
}
