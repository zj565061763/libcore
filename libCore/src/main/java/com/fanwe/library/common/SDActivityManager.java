package com.fanwe.library.common;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

@Deprecated
public class SDActivityManager
{
    private static SDActivityManager sInstance;
    private Stack<Activity> mStackActivity = new Stack<>();

    private SDActivityManager()
    {
    }

    public static SDActivityManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (SDActivityManager.class)
            {
                if (sInstance == null)
                {
                    sInstance = new SDActivityManager();
                }
            }
        }
        return sInstance;
    }

    // ----------------------------activity life method

    public void onCreate(Activity activity)
    {
        addActivity(activity);
    }

    public void onResume(Activity activity)
    {
        addActivity(activity);
    }

    /**
     * finish()和onDestroy()都要调用
     *
     * @param activity
     */
    public void onDestroy(Activity activity)
    {
        removeActivity(activity);
    }

    private void addActivity(Activity activity)
    {
        if (mStackActivity.contains(activity))
        {
            return;
        }
        mStackActivity.add(activity);
    }

    private void removeActivity(Activity activity)
    {
        try
        {
            if (mStackActivity.contains(activity))
            {
                mStackActivity.remove(activity);
            }
        } catch (Exception e)
        {
        }
    }

    public Activity getActivity(Class<?> clazz)
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity item = it.next();
            if (item.getClass() == clazz)
            {
                return item;
            }
        }
        return null;
    }

    public Activity getLastActivity()
    {
        try
        {
            return mStackActivity.lastElement();
        } catch (Exception e)
        {
            return null;
        }
    }

    public boolean isLastActivity(Activity activity)
    {
        if (activity == null)
        {
            return false;
        }
        return activity == getLastActivity();
    }

    public boolean isEmpty()
    {
        return mStackActivity.isEmpty();
    }

    /**
     * 结束指定类的Activity
     *
     * @param clazz
     */
    public void finishActivity(Class<?> clazz)
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity item = it.next();
            if (item.getClass() == clazz)
            {
                it.remove();
                item.finish();
            }
        }
    }

    public boolean containActivity(Class<?> clazz)
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity item = it.next();
            if (item.getClass() == clazz)
            {
                return true;
            }
        }
        return false;
    }

    public void finishAllActivityExcept(Activity activity)
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity act = it.next();
            if (act != activity)
            {
                it.remove();
                act.finish();
            }
        }
    }

    public void finishAllActivityExcept(Class<?> clazz)
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity item = it.next();
            if (item.getClass() != clazz)
            {
                it.remove();
                item.finish();
            }
        }
    }

    public void finishAllClassActivityExcept(Activity activity)
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity item = it.next();
            if (item.getClass() == activity.getClass() && item != activity)
            {
                it.remove();
                item.finish();
            }
        }
    }

    public void finishAllActivity()
    {
        Iterator<Activity> it = mStackActivity.iterator();
        while (it.hasNext())
        {
            Activity item = it.next();
            it.remove();
            item.finish();
        }
    }
}