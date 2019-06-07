package com.sd.libcore.stream.activity;

import android.app.Activity;

public interface ActivityResumedStream extends FActivityStream
{
    void onActivityResumed(Activity activity);
}
