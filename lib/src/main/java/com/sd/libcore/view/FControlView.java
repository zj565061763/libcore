package com.sd.libcore.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.activity.FActivity;
import com.sd.libcore.stream.activity.ActivityDestroyedStream;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class FControlView extends FViewGroup implements ActivityDestroyedStream
{
    public FControlView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        FStreamManager.getInstance().bindView(this, this);
    }

    public FActivity getFActivity()
    {
        final Activity activity = getActivity();
        return activity instanceof FActivity ? (FActivity) activity : null;
    }

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz)
    {
        return getStreamTagActivity();
    }

    public final String getStreamTagActivity()
    {
        final FActivity fActivity = getFActivity();
        if (fActivity != null)
            return fActivity.getStreamTag();

        final Activity activity = getActivity();
        if (activity != null)
            return activity.toString();

        return getStreamTagView();
    }

    public final String getStreamTagView()
    {
        final String className = getClass().getName();
        final String hashCode = Integer.toHexString(System.identityHashCode(this));
        return className + "@" + hashCode;
    }

    public void showProgressDialog(String msg)
    {
        final FActivity fActivity = getFActivity();
        if (fActivity != null)
            fActivity.showProgressDialog(msg);
    }

    public void dismissProgressDialog()
    {
        final FActivity fActivity = getFActivity();
        if (fActivity != null)
            fActivity.dismissProgressDialog();
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {

    }
}
