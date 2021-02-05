package com.sd.libcore.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FActivityStack
{
    private static FActivityStack sInstance;

    private Application mApplication;
    private final List<Activity> mActivityHolder = new CopyOnWriteArrayList<>();
    private final Map<Activity, String> mMapActivity = new ConcurrentHashMap<>();

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
        if (mApplication == null)
        {
            mApplication = application;
            mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
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
            final Activity lastActivity = getLastActivity();
            if (lastActivity != activity)
            {
                if (mIsDebug)
                    Log.e(FActivityStack.class.getSimpleName(), "order start " + activity);

                removeActivity(activity);
                addActivity(activity);

                if (mIsDebug)
                    Log.e(FActivityStack.class.getSimpleName(), "order finish " + activity);
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
        if (activity == null)
            return;

        final String put = mMapActivity.put(activity, "");
        if (put == null)
        {
            mActivityHolder.add(activity);

            if (mIsDebug)
            {
                Log.i(FActivityStack.class.getSimpleName(), "+++++ " + activity + " " + mActivityHolder.size()
                        + "\r\n" + getCurrentStack());
            }
        }
    }

    /**
     * 移除对象
     *
     * @param activity
     */
    private void removeActivity(Activity activity)
    {
        if (activity == null)
            return;

        if (mActivityHolder.remove(activity))
        {
            mMapActivity.remove(activity);
            if (mIsDebug)
            {
                Log.e(FActivityStack.class.getSimpleName(), "----- " + activity + " " + mActivityHolder.size()
                        + "\r\n" + getCurrentStack());
            }
        }
    }

    /**
     * 返回栈中保存的对象个数
     *
     * @return
     */
    public int size()
    {
        return mActivityHolder.size();
    }

    /**
     * 返回栈中指定位置的对象
     *
     * @param index
     * @return
     */
    public Activity getActivity(int index)
    {
        try
        {
            return mActivityHolder.get(index);
        } catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 返回栈中最后一个对象
     *
     * @return
     */
    public Activity getLastActivity()
    {
        if (mActivityHolder.isEmpty())
            return null;

        return getActivity(mActivityHolder.size() - 1);
    }

    /**
     * 是否包含指定对象
     *
     * @param activity
     * @return
     */
    public boolean containsActivity(Activity activity)
    {
        if (activity == null)
            return false;

        return mMapActivity.containsKey(activity);
    }

    /**
     * 返回栈中指定类型的所有对象
     *
     * @param clazz
     * @return
     */
    public List<Activity> getActivity(Class<? extends Activity> clazz)
    {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        final List<Activity> list = new ArrayList<>(1);
        for (Activity item : mActivityHolder)
        {
            if (item.getClass() == clazz)
                list.add(item);
        }
        return list;
    }

    /**
     * 返回栈中指定类型的第一个对象
     *
     * @param clazz
     * @return
     */
    public Activity getFirstActivity(Class<? extends Activity> clazz)
    {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        for (Activity item : mActivityHolder)
        {
            if (item.getClass() == clazz)
                return item;
        }
        return null;
    }

    /**
     * 栈中是否包含指定类型的对象
     *
     * @param clazz
     * @return
     */
    public boolean containsActivity(Class<? extends Activity> clazz)
    {
        return getFirstActivity(clazz) != null;
    }

    /**
     * 结束栈中指定类型的对象
     *
     * @param clazz
     */
    public void finishActivity(Class<? extends Activity> clazz)
    {
        final List<Activity> list = getActivity(clazz);
        for (Activity item : list)
        {
            item.finish();
        }
    }

    /**
     * 结束栈中除了activity外的所有对象
     *
     * @param activity
     */
    public void finishActivityExcept(Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");

        for (Activity item : mActivityHolder)
        {
            if (item != activity)
                item.finish();
        }
    }

    /**
     * 结束栈中除了指定类型外的所有对象
     *
     * @param clazz
     */
    public void finishActivityExcept(Class<? extends Activity> clazz)
    {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        for (Activity item : mActivityHolder)
        {
            if (item.getClass() != clazz)
                item.finish();
        }
    }

    /**
     * 结束栈中除了activity外的所有activity类型的对象
     *
     * @param activity
     */
    public void finishSameClassActivityExcept(Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");

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

    /**
     * 结束指定activity对象之上的所有activity对象
     *
     * @param activity
     */
    public void finishActivityAbove(Activity activity)
    {
        if (activity == null || mActivityHolder.isEmpty())
            return;

        final List<Activity> listCopy = new ArrayList<>(mActivityHolder);
        final ListIterator<Activity> it = listCopy.listIterator(listCopy.size());
        while (it.hasPrevious())
        {
            if (!containsActivity(activity))
                break;

            final Activity item = it.previous();
            if (containsActivity(item))
            {
                if (item == activity)
                    break;

                item.finish();
            }
        }
    }

    /**
     * 压入栈底
     *
     * @param activity
     */
    public void pushToBottom(Activity activity)
    {
        if (!containsActivity(activity))
            return;

        for (Activity item : mActivityHolder)
        {
            if (item == activity)
                break;

            final Intent intent = new Intent(activity, item.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
        }
    }
}