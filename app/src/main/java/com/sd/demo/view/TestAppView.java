package com.sd.demo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.stream.activity.ActivityDestroyedStream;
import com.sd.libcore.stream.activity.ActivityPausedStream;
import com.sd.libcore.stream.activity.ActivityResumedStream;
import com.sd.libcore.stream.activity.ActivityStoppedStream;
import com.sd.libcore.view.FControlView;

public class TestAppView extends FControlView
{
    public static final String TAG = TestAppView.class.getSimpleName();

    public TestAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        FStreamManager.getInstance().bindStream(mActivityResumedStream, this);
        FStreamManager.getInstance().bindStream(mActivityPausedStream, this);
        FStreamManager.getInstance().bindStream(mActivityStoppedStream, this);
        FStreamManager.getInstance().bindStream(mActivityDestroyedStream, this);
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

    private final ActivityResumedStream mActivityResumedStream = new ActivityResumedStream()
    {
        @Override
        public void onActivityResumed(Activity activity)
        {
            Log.i(TAG, "Stream onActivityResumed:" + activity);
        }

        @Override
        public Object getTagForStream(Class<? extends FStream> clazz)
        {
            return getStreamTagActivity();
        }
    };

    private final ActivityPausedStream mActivityPausedStream = new ActivityPausedStream()
    {
        @Override
        public void onActivityPaused(Activity activity)
        {
            Log.i(TAG, "Stream onActivityPaused:" + activity);
        }

        @Override
        public Object getTagForStream(Class<? extends FStream> clazz)
        {
            return getStreamTagActivity();
        }
    };

    private final ActivityStoppedStream mActivityStoppedStream = new ActivityStoppedStream()
    {
        @Override
        public void onActivityStopped(Activity activity)
        {
            Log.i(TAG, "Stream onActivityStopped:" + activity);
        }

        @Override
        public Object getTagForStream(Class<? extends FStream> clazz)
        {
            return getStreamTagActivity();
        }
    };

    private final ActivityDestroyedStream mActivityDestroyedStream = new ActivityDestroyedStream()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            Log.i(TAG, "Stream onActivityDestroyed:" + activity);
        }

        @Override
        public Object getTagForStream(Class<? extends FStream> clazz)
        {
            return getStreamTagActivity();
        }
    };

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
