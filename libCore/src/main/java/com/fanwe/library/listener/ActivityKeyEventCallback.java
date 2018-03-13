package com.fanwe.library.listener;

import android.app.Activity;
import android.view.KeyEvent;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface ActivityKeyEventCallback
{
    boolean dispatchKeyEvent(Activity activity, KeyEvent event);
}
