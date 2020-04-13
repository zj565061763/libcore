package com.sd.libcore.business;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FBusinessHolder
{
    private static final String BUSINESS_EMPTY_TAG = "business_empty_tag";

    private final Map<Class<? extends FBusiness>, Map<String, FBusiness>> mMapBusiness = new ConcurrentHashMap<>();

    private FBusinessHolder()
    {
    }

    /**
     * 添加业务类
     *
     * @param business
     */
    public synchronized void add(FBusiness business)
    {
        if (business == null)
            return;

        final Class<? extends FBusiness> clazz = business.getClass();
        final String tag = getBusinessTag(business);

        Map<String, FBusiness> map = mMapBusiness.get(clazz);
        if (map == null)
        {
            map = new ConcurrentHashMap<>();
            mMapBusiness.put(clazz, map);
        }

        map.put(tag, business);
    }

    /**
     * 移除业务类
     *
     * @param business
     */
    public synchronized void remove(FBusiness business)
    {
        if (business == null)
            return;

        final Class<? extends FBusiness> clazz = business.getClass();
        final String tag = getBusinessTag(business);

        final Map<String, FBusiness> map = mMapBusiness.get(clazz);
        if (map != null)
        {
            map.remove(tag);
            if (map.isEmpty())
                mMapBusiness.remove(clazz);
        }
    }

    /**
     * 返回指定class的业务类对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized <T extends FBusiness> List<T> get(Class<T> clazz)
    {
        if (clazz == null)
            return null;

        final Map<String, FBusiness> map = mMapBusiness.get(clazz);
        if (map == null || map.isEmpty())
            return Collections.emptyList();

        final List<T> list = new ArrayList<>(map.size());
        for (FBusiness item : map.values())
        {
            list.add((T) item);
        }
        return list;
    }

    /**
     * 返回指定class的业务类对象，该class有对应多个业务类对象，则抛异常
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized <T extends FBusiness> T getOne(Class<T> clazz)
    {
        if (clazz == null)
            return null;

        final Map<String, FBusiness> map = mMapBusiness.get(clazz);
        if (map == null || map.isEmpty())
            return null;

        final int size = map.size();
        if (size != 1)
            throw new RuntimeException(size + " business instance was found for class:" + clazz);

        return (T) map.values().iterator().next();
    }

    /**
     * 返回指定class和tag的业务类对象
     *
     * @param clazz
     * @param tag
     * @param <T>
     * @return
     */
    public synchronized <T extends FBusiness> T get(Class<T> clazz, String tag)
    {
        if (clazz == null)
            return null;

        final Map<String, FBusiness> map = mMapBusiness.get(clazz);
        if (map == null || map.isEmpty())
            return null;

        if (tag == null)
            tag = BUSINESS_EMPTY_TAG;

        return (T) map.get(tag);
    }

    /**
     * 销毁并清空所有业务类
     */
    public synchronized void destroy()
    {
        for (Map<String, FBusiness> item : mMapBusiness.values())
        {
            for (FBusiness business : item.values())
            {
                business.onDestroy();
            }
        }
        mMapBusiness.clear();
    }

    private static String getBusinessTag(FBusiness business)
    {
        final String tag = business.getTag();
        if (tag == null)
            return BUSINESS_EMPTY_TAG;
        return tag;
    }

    //---------- static ----------

    private static boolean sHasInit = false;
    private static final Map<Activity, FBusinessHolder> MAP_HOLDER = new WeakHashMap<>();

    private static synchronized void init(Context context)
    {
        if (sHasInit)
            return;

        sHasInit = true;
        final Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks()
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
                synchronized (FBusinessHolder.class)
                {
                    final FBusinessHolder holder = MAP_HOLDER.remove(activity);
                    if (holder != null)
                        holder.destroy();
                }
            }
        });
    }

    public static synchronized FBusinessHolder with(Activity activity)
    {
        if (activity == null)
            return null;

        init(activity);

        FBusinessHolder holder = MAP_HOLDER.get(activity);
        if (holder == null)
        {
            holder = new FBusinessHolder();
            if (!activity.isFinishing())
                MAP_HOLDER.put(activity, holder);
        }
        return holder;
    }
}
