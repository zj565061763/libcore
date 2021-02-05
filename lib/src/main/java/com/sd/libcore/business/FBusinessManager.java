package com.sd.libcore.business;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 业务类管理
 */
public class FBusinessManager
{
    private static FBusinessManager sInstance;

    public static FBusinessManager getInstance()
    {
        if (sInstance != null)
            return sInstance;

        synchronized (FBusinessManager.class)
        {
            if (sInstance == null)
                sInstance = new FBusinessManager();
            return sInstance;
        }
    }

    private final Map<FBusiness, String> mMapBusiness = new WeakHashMap<>();

    private FBusinessManager()
    {
    }

    /**
     * 保存业务类
     *
     * @param business
     */
    synchronized void addBusiness(FBusiness business)
    {
        mMapBusiness.put(business, "");
    }

    /**
     * 移除业务类
     *
     * @param business
     */
    synchronized void removeBusiness(FBusiness business)
    {
        mMapBusiness.remove(business);
    }

    /**
     * 放回指定tag的业务类
     *
     * @param tag
     * @return
     */
    public List<FBusiness> getBusiness(final String tag)
    {
        final List<FBusiness> listResult = new ArrayList<>();

        findBusiness(new FindBusinessCallback()
        {
            @Override
            public boolean onBusiness(FBusiness business)
            {
                if (TextUtils.equals(business.getTag(), tag))
                    listResult.add(business);
                return false;
            }
        });
        return listResult;
    }

    /**
     * 返回指定类型的业务类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends FBusiness> List<T> getBusiness(final Class<T> clazz)
    {
        final List<T> listResult = new ArrayList<>();

        findBusiness(new FindBusinessCallback()
        {
            @Override
            public boolean onBusiness(FBusiness business)
            {
                if (business.getClass() == clazz)
                    listResult.add((T) business);
                return false;
            }
        });
        return listResult;
    }

    /**
     * 返回指定类型和tag的业务类
     *
     * @param clazz
     * @param tag
     * @param <T>
     * @return
     */
    public <T extends FBusiness> List<T> getBusiness(final Class<T> clazz, final String tag)
    {
        final List<T> listResult = new ArrayList<>();

        findBusiness(new FindBusinessCallback()
        {
            @Override
            public boolean onBusiness(FBusiness business)
            {
                if (business.getClass() == clazz && TextUtils.equals(business.getTag(), tag))
                    listResult.add((T) business);
                return false;
            }
        });
        return listResult;
    }

    /**
     * 查找业务类
     *
     * @param callback
     * @return
     */
    public void findBusiness(FindBusinessCallback callback)
    {
        if (callback == null)
            return;

        final Collection<FBusiness> listCopy = new ArrayList<>(mMapBusiness.keySet());
        for (FBusiness item : listCopy)
        {
            if (callback.onBusiness(item))
                break;
        }
    }

    public interface FindBusinessCallback
    {
        /**
         * 业务类对象回调
         *
         * @param business
         * @return true-停止查找
         */
        boolean onBusiness(FBusiness business);
    }
}
