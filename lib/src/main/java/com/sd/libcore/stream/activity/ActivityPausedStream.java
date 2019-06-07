package com.sd.libcore.stream.activity;

import android.app.Activity;

public interface ActivityPausedStream extends FActivityStream
{
    void onActivityPaused(Activity activity);
}
