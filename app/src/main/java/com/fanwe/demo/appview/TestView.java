package com.fanwe.demo.appview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.demo.R;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.view.SDAppView;

public class TestView extends SDAppView
{
    public TestView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public TestView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TestView(Context context)
    {
        super(context);
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        setContentView(R.layout.view_test);

        getVisibilityHandler().addVisibilityCallback(new SDViewVisibilityCallback()
        {
            @Override
            public void onViewVisibilityChanged(View view, int visibility)
            {
                LogUtil.i("onViewVisibilityChanged:" + visibility + "," + getClass().getSimpleName());
            }
        });
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        LogUtil.i("onDetachedFromWindow " + getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        super.onActivityCreated(activity, savedInstanceState);
        LogUtil.i("onActivityCreated " + getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        super.onActivityResumed(activity);
        LogUtil.i("onActivityResumed " + getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        super.onActivityStopped(activity);
        LogUtil.i("onActivityStopped " + getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        super.onActivityDestroyed(activity);
        LogUtil.i("onActivityDestroyed " + getClass().getSimpleName());
    }
}
