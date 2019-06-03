package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.view.MotionEvent;

import com.sd.lib.stream.FStream;

public interface ActivityTouchEventStream extends FStream
{
    boolean dispatchTouchEvent(Activity activity, MotionEvent event);
}
