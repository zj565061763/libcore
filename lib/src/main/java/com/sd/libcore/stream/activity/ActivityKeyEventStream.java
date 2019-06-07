package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.view.KeyEvent;

public interface ActivityKeyEventStream extends FActivityStream
{
    boolean dispatchKeyEvent(Activity activity, KeyEvent event);
}
