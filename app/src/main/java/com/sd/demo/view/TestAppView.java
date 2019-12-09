package com.sd.demo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.sd.lib.eventact.observer.ActivityDestroyedObserver;
import com.sd.lib.eventact.observer.ActivityResumedObserver;
import com.sd.libcore.view.FViewGroup;

public class TestAppView extends FViewGroup
{
    public static final String TAG = TestAppView.class.getSimpleName();

    public TestAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        mActivityResumedObserver.register(getActivity());
        mActivityDestroyedObserver.register(getActivity());
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

    private final ActivityResumedObserver mActivityResumedObserver = new ActivityResumedObserver()
    {
        @Override
        public void onActivityResumed(Activity activity)
        {
            Log.i(TAG, "onActivityResumed:" + activity);
        }
    };

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            Log.i(TAG, "onActivityDestroyed:" + activity);
        }
    };
}
