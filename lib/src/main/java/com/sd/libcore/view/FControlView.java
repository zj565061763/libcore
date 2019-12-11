package com.sd.libcore.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityPausedCallback;
import com.sd.lib.eventact.callback.ActivityResumedCallback;
import com.sd.lib.eventact.callback.ActivityStoppedCallback;
import com.sd.lib.eventact.observer.ActivityDestroyedObserver;
import com.sd.lib.eventact.observer.ActivityPausedObserver;
import com.sd.lib.eventact.observer.ActivityResumedObserver;
import com.sd.lib.eventact.observer.ActivityStoppedObserver;
import com.sd.libcore.activity.FActivity;
import com.sd.libcore.activity.FStreamActivity;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class FControlView extends FViewGroup implements
        ActivityResumedCallback,
        ActivityPausedCallback,
        ActivityStoppedCallback,
        ActivityDestroyedCallback
{
    public FControlView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public final String getStreamTagActivity()
    {
        final Activity activity = getActivity();
        if (activity == null)
            return getStreamTagView();

        if (activity instanceof FStreamActivity)
            return ((FStreamActivity) activity).getStreamTag();

        return activity.toString();
    }

    public final String getStreamTagView()
    {
        final String className = getClass().getName();
        final String hashCode = Integer.toHexString(System.identityHashCode(this));
        return className + "@" + hashCode;
    }

    public void showProgressDialog(String msg)
    {
        final Activity activity = getActivity();
        if (activity instanceof FActivity)
            ((FActivity) activity).showProgressDialog(msg);
    }

    public void dismissProgressDialog()
    {
        final Activity activity = getActivity();
        if (activity instanceof FActivity)
            ((FActivity) activity).dismissProgressDialog();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        final Activity activity = getActivity();
        mActivityResumedObserver.register(activity);
        mActivityPausedObserver.register(activity);
        mActivityStoppedObserver.register(activity);
        mActivityDestroyedObserver.register(activity);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mActivityResumedObserver.unregister();
        mActivityPausedObserver.unregister();
        mActivityStoppedObserver.unregister();
        mActivityDestroyedObserver.unregister();
    }

    private final ActivityResumedObserver mActivityResumedObserver = new ActivityResumedObserver()
    {
        @Override
        public void onActivityResumed(Activity activity)
        {
            FControlView.this.onActivityResumed(activity);
        }
    };

    private final ActivityPausedObserver mActivityPausedObserver = new ActivityPausedObserver()
    {
        @Override
        public void onActivityPaused(Activity activity)
        {
            FControlView.this.onActivityPaused(activity);
        }
    };

    private final ActivityStoppedObserver mActivityStoppedObserver = new ActivityStoppedObserver()
    {
        @Override
        public void onActivityStopped(Activity activity)
        {
            FControlView.this.onActivityStopped(activity);
        }
    };

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            FControlView.this.onActivityDestroyed(activity);
        }
    };

    @Override
    public void onActivityResumed(Activity activity)
    {
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
    }
}
