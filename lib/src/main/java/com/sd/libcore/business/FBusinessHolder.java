package com.sd.libcore.business;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FBusinessHolder
{
    private static final String BUSINESS_EMPTY_TAG = "business_empty_tag";

    private final WeakReference<Activity> mActivity;
    private final Map<Class<? extends FBusiness>, Map<String, FBusiness>> mMapBusiness = new ConcurrentHashMap<>();

    private FBusinessHolder(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");
        mActivity = new WeakReference<>(activity);
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
     * 销毁所有业务类
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

        final Activity activity = mActivity.get();
        if (activity != null)
        {
            synchronized (FBusinessHolder.class)
            {
                MAP_HOLDER.remove(activity);
            }
        }
    }

    private static String getBusinessTag(FBusiness business)
    {
        final String tag = business.getTag();
        if (tag == null)
            return BUSINESS_EMPTY_TAG;
        return tag;
    }

    //---------- static ----------

    private static final Map<Activity, FBusinessHolder> MAP_HOLDER = new WeakHashMap<>();

    public static synchronized FBusinessHolder with(Activity activity)
    {
        if (activity == null)
            return null;

        FBusinessHolder holder = MAP_HOLDER.get(activity);
        if (holder == null)
        {
            holder = new FBusinessHolder(activity);
            if (!activity.isFinishing())
                MAP_HOLDER.put(activity, holder);
        }
        return holder;
    }
}
