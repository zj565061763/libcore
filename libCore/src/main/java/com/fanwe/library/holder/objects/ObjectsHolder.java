package com.fanwe.library.holder.objects;

/**
 * 对象holder
 *
 * @param <T>
 */
public interface ObjectsHolder<T>
{
    /**
     * 添加对象
     *
     * @param object
     * @return true-调用此方法后对象被添加了
     */
    boolean add(T object);

    /**
     * 移除对象
     *
     * @param object
     * @return true-调用此方法后对象被移除了
     */
    boolean remove(Object object);

    /**
     * 是否包含某个对象
     *
     * @param object
     * @return true-包含
     */
    boolean contains(T object);

    /**
     * 当前保存对象的个数
     *
     * @return
     */
    int size();

    /**
     * 清空
     */
    void clear();

    /**
     * 正序遍历
     *
     * @param callback
     * @return 返回callback中的data {@link ForeachCallback#getData()}
     */
    Object foreach(ForeachCallback<T> callback);

    /**
     * 倒序遍历
     *
     * @param callback
     * @return 返回callback中的data {@link ForeachCallback#getData()}
     */
    Object foreachReverse(ForeachCallback<T> callback);

    /**
     * 返回保存的所有对象信息，常用来输出日志调试
     *
     * @return
     */
    String getObjectsString();
}
