package com.sd.libcore.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.observer.ActivityDestroyedObserver;
import com.sd.libcore.activity.FActivity;
import com.sd.libcore.activity.FStreamActivity;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class FControlView extends FViewGroup implements ActivityDestroyedCallback
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

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            FControlView.this.onActivityDestroyed(activity);
        }
    };

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mActivityDestroyedObserver.register(getActivity());
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mActivityDestroyedObserver.unregister();
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {

    }
}
