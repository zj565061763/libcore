package com.fanwe.library.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FActivityStack
{
    private static FActivityStack sInstance;

    private Application mApplication;
    private final List<Activity> mActivityHolder = new CopyOnWriteArrayList<>();

    private boolean mIsDebug;

    private FActivityStack()
    {
    }

    public static FActivityStack getInstance()
    {
        if (sInstance == null)
        {
            synchronized (FActivityStack.class)
            {
                if (sInstance == null)
                    sInstance = new FActivityStack();
            }
        }
        return sInstance;
    }

    public void setDebug(boolean debug)
    {
        mIsDebug = debug;
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

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks()
    {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState)
        {
            addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity)
        {
        }

        @Override
        public void onActivityResumed(Activity activity)
        {
            final int index = mActivityHolder.indexOf(activity);
            if (index < 0)
                return;

            if (index != (mActivityHolder.size() - 1))
            {
                if (mIsDebug)
                    Log.e(FActivityStack.class.getSimpleName(), "start order activity " + activity + " old index " + index);

                removeActivity(activity);
                addActivity(activity);

                if (mIsDebug)
                    Log.e(FActivityStack.class.getSimpleName(), "end order activity " + activity + " new index " + mActivityHolder.indexOf(activity));
            }
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
        public void onActivitySaveInstanceState(Activity activity, Bundle outState)
        {
        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            removeActivity(activity);
        }
    };

    private String getCurrentStack()
    {
        Object[] arrActivity = mActivityHolder.toArray();
        if (arrActivity != null)
        {
            return Arrays.toString(arrActivity);
        } else
        {
            return "";
        }
    }

    /**
     * 添加对象
     *
     * @param activity
     */
    private void addActivity(Activity activity)
    {
        if (mActivityHolder.contains(activity))
            return;

        mActivityHolder.add(activity);
        if (mIsDebug)
            Log.i(FActivityStack.class.getSimpleName(), "+++++ " + activity + "\n" + getCurrentStack());
    }

    /**
     * 移除对象
     *
     * @param activity
     */
    private void removeActivity(Activity activity)
    {
        if (mActivityHolder.remove(activity))
        {
            if (mIsDebug)
                Log.e(FActivityStack.class.getSimpleName(), "----- " + activity + "\n" + getCurrentStack());
        }
    }

    /**
     * 返回最后一个对象
     *
     * @return
     */
    public Activity getLastActivity()
    {
        try
        {
            return mActivityHolder.get(mActivityHolder.size() - 1);
        } catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 返回指定位置的对象
     *
     * @param index
     * @return
     */
    public Activity getActivity(int index)
    {
        if (index >= 0 && index < size())
        {
            return mActivityHolder.get(index);
        } else
        {
            return null;
        }
    }

    /**
     * 返回当前保存的对象个数
     *
     * @return
     */
    public int size()
    {
        return mActivityHolder.size();
    }

    /**
     * 结束指定类的对象
     *
     * @param clazz
     */
    public void finishActivity(Class<?> clazz)
    {
        for (Activity item : mActivityHolder)
        {
            if (item.getClass() == clazz)
                item.finish();
        }
    }

    /**
     * 是否包含指定类的对象
     *
     * @param clazz
     * @return
     */
    public boolean containActivity(Class<?> clazz)
    {
        for (Activity item : mActivityHolder)
        {
            if (item.getClass() == clazz)
                return true;
        }
        return false;
    }

    /**
     * 是否包含指定对象
     *
     * @param activity
     * @return
     */
    public boolean containActivity(Activity activity)
    {
        return mActivityHolder.contains(activity);
    }

    /**
     * 结束除了activity外的所有对象
     *
     * @param activity
     */
    public void finishAllActivityExcept(Activity activity)
    {
        for (Activity item : mActivityHolder)
        {
            if (item != activity)
                item.finish();
        }
    }

    /**
     * 结束除了指定类外的所有对象
     *
     * @param clazz
     */
    public void finishAllActivityExcept(Class<?> clazz)
    {
        for (Activity item : mActivityHolder)
        {
            if (item.getClass() != clazz)
                item.finish();
        }
    }

    /**
     * 结束除了activity外的所有activity类的对象
     *
     * @param activity
     */
    public void finishAllClassActivityExcept(Activity activity)
    {
        for (Activity item : mActivityHolder)
        {
            if (item.getClass() == activity.getClass())
            {
                if (item != activity)
                    item.finish();
            }
        }
    }

    /**
     * 结束所有对象
     */
    public void finishAllActivity()
    {
        for (Activity item : mActivityHolder)
        {
            item.finish();
        }
    }
}