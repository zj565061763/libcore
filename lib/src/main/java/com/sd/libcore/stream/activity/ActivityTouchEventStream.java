package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.view.MotionEvent;

public interface ActivityTouchEventStream extends FActivityStream
{
    boolean dispatchTouchEvent(Activity activity, MotionEvent event);
}
