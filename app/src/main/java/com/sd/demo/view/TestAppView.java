package com.sd.demo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.sd.libcore.view.FControlView;

public class TestAppView extends FControlView
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
        super.onActivityResumed(activity);
        Log.i(TAG, "onActivityResumed:" + activity);
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        super.onActivityPaused(activity);
        Log.i(TAG, "onActivityPaused:" + activity);
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        super.onActivityStopped(activity);
        Log.i(TAG, "onActivityStopped:" + activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        super.onActivityDestroyed(activity);
        Log.i(TAG, "onActivityDestroyed:" + activity);
    }
}
