package com.fanwe.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * app前后台切换监听
 */
public class FAppBackgroundListener
{
    private static FAppBackgroundListener sInstance;

    private Application mApplication;
    private boolean mIsBackground;
    private long mBackgroundTime;

    private final List<Callback> mListCallback = new CopyOnWriteArrayList<>();

    private FAppBackgroundListener()
    {
    }

    public static FAppBackgroundListener getInstance()
    {
        if (sInstance == null)
        {
            synchronized (FAppBackgroundListener.class)
            {
                if (sInstance == null)
                    sInstance = new FAppBackgroundListener();
            }
        }
        return sInstance;
    }

    public synchronized void init(Application application)
    {
        if (application == null)
            throw new NullPointerException("application is null");

        if (mApplication == null)
        {
            mApplication = application;

            application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
            application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
    }

    /**
     * 返回最后一次App进入后台的时间点
     *
     * @return
     */
    public long getBackgroundTime()
    {
        return mBackgroundTime;
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void addCallback(Callback callback)
    {
        if (callback == null || mListCallback.contains(callback))
            return;

        if (mApplication == null)
            throw new NullPointerException("you must invoke init(Application) method before this");

        mListCallback.add(callback);
    }

    /**
     * 移除回调
     *
     * @param callback
     */
    public void removeCallback(Callback callback)
    {
        mListCallback.remove(callback);
    }

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks()
    {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState)
        {
        }

        @Override
        public void onActivityStarted(Activity activity)
        {
        }

        @Override
        public void onActivityResumed(Activity activity)
        {
            if (mIsBackground)
            {
                mIsBackground = false;

                for (Callback item : mListCallback)
                {
                    item.onResumeFromBackground();
                }
            }
        }

        @Override
        public void onActivityPaused(Activity activity)
        {
        }

        @Override
        public void onActivityStopped(Activity activity)
        {
            if (!mIsBackground)
            {
                if (isAppBackground(mApplication))
                {
                    mIsBackground = true;
                    mBackgroundTime = System.currentTimeMillis();

                    for (Callback item : mListCallback)
                    {
                        item.onBackground();
                    }
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState)
        {

        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {

        }
    };

    public static boolean isAppBackground(Context context)
    {
        final ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();

        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo item : list)
        {
            if (item.processName.equals(packageName))
            {
                return item.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }

    public interface Callback
    {
        void onBackground();

        void onResumeFromBackground();
    }
}
