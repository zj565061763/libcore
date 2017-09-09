package com.fanwe.library.listener;

import android.app.Activity;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface SDActivityDispatchTouchEventCallback
{
    boolean dispatchTouchEvent(Activity activity, MotionEvent ev);
}
