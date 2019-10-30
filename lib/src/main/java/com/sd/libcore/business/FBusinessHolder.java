package com.sd.libcore.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FBusinessHolder
{
    private FBusinessHolder()
    {
    }

    private static final Map<Class<? extends FBusiness>, Map<String, FBusiness>> MAP_BUSINESS = new ConcurrentHashMap<>();

    /**
     * 保存业务类到全局容器
     *
     * @param business
     */
    public static void add(FBusiness business)
    {
        if (business == null)
            return;

        synchronized (FBusinessHolder.class)
        {
            final Class<? extends FBusiness> clazz = business.getClass();
            final String tag = business.getTag();

            Map<String, FBusiness> map = MAP_BUSINESS.get(clazz);
            if (map == null)
            {
                map = new ConcurrentHashMap<>();
                MAP_BUSINESS.put(clazz, map);
            }

            map.put(tag, business);
        }
    }

    /**
     * 从全局容器中移除业务类
     *
     * @param business
     */
    public static void remove(FBusiness business)
    {
        if (business == null)
            return;

        synchronized (FBusinessHolder.class)
        {
            final Class<? extends FBusiness> clazz = business.getClass();
            final String tag = business.getTag();

            final Map<String, FBusiness> map = MAP_BUSINESS.get(clazz);
            if (map != null)
            {
                map.remove(tag);
                if (map.isEmpty())
                    MAP_BUSINESS.remove(clazz);
            }
        }
    }

    /**
     * 从容器中获得业务类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends FBusiness> List<T> get(Class<T> clazz)
    {
        if (clazz == null)
            return null;

        synchronized (FBusinessHolder.class)
        {
            final Map<String, FBusiness> map = MAP_BUSINESS.get(clazz);
            if (map == null)
                return null;

            final List<T> list = new ArrayList<>(map.size());
            for (FBusiness item : map.values())
            {
                list.add((T) item);
            }
            return list;
        }
    }

    /**
     * 从全局容器中获得业务类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends FBusiness> T get(Class<T> clazz, String tag)
    {
        if (clazz == null)
            return null;

        synchronized (FBusinessHolder.class)
        {
            final Map<String, FBusiness> map = MAP_BUSINESS.get(clazz);
            if (map == null)
                return null;

            return (T) map.get(tag);
        }
    }
}
