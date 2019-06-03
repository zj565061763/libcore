package com.sd.demo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.sd.libcore.stream.activity.ActivityDestroyedStream;
import com.sd.libcore.stream.activity.ActivityResumedStream;
import com.sd.libcore.stream.activity.ActivityStoppedStream;
import com.sd.libcore.view.FAppView;

public class TestAppView extends FAppView implements ActivityResumedStream, ActivityStoppedStream, ActivityDestroyedStream
{
    public static final String TAG = TestAppView.class.getSimpleName();

    public TestAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow");
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        Log.i(TAG, "onActivityResumed:" + activity);
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        Log.i(TAG, "onActivityStopped:" + activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        Log.i(TAG, "onActivityDestroyed:" + activity);
    }
}
