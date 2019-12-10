package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.view.KeyEvent;

import com.sd.lib.stream.FStream;

@Deprecated
public interface ActivityKeyEventStream extends FStream
{
    boolean dispatchKeyEvent(Activity activity, KeyEvent event);
}
