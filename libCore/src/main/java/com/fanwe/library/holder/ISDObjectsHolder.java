package com.fanwe.library.holder;

import com.fanwe.library.listener.SDIterateCallback;

/**
 * 多对象持有管理类，适用于保存监听对象
 *
 * @param <T>
 */
public interface ISDObjectsHolder<T>
{
    /**
     * 添加对象
     *
     * @param object
     */
    void add(T object);

    /**
     * 移除对象
     *
     * @param object
     * @return
     */
    boolean remove(T object);

    /**
     * 是否已经包含了该对象
     *
     * @param object
     * @return
     */
    boolean contains(T object);

    /**
     * 对象个数
     *
     * @return
     */
    int size();

    /**
     * 清空对象
     */
    void clear();

    /**
     * 遍历对象
     *
     * @param callback
     * @return
     */
    boolean foreach(SDIterateCallback<T> callback);

    /**
     * 倒序遍历对象
     *
     * @param callback
     * @return
     */
    boolean foreachReverse(SDIterateCallback<T> callback);
}
