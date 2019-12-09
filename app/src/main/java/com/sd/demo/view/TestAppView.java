package com.sd.demo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.sd.lib.eventact.observer.ActivityPausedObserver;
import com.sd.lib.eventact.observer.ActivityResumedObserver;
import com.sd.libcore.view.FControlView;

public class TestAppView extends FControlView
{
    public static final String TAG = TestAppView.class.getSimpleName();

    public TestAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mActivityResumedObserver.register(getActivity());
        mActivityPausedObserver.register(getActivity());
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

    private final ActivityPausedObserver mActivityPausedObserver = new ActivityPausedObserver()
    {
        @Override
        public void onActivityPaused(Activity activity)
        {
            Log.i(TAG, "onActivityPaused:" + activity);
        }
    };

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        super.onActivityDestroyed(activity);
        Log.i(TAG, "onActivityDestroyed:" + activity);
    }
}
