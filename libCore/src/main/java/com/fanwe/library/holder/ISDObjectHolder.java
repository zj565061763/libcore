package com.fanwe.library.holder;

/**
 * 对象持有者
 *
 * @param <T>
 */
public interface ISDObjectHolder<T>
{
    /**
     * 获取对象
     *
     * @return
     */
    T get();

    /**
     * 设置对象
     *
     * @param object
     */
    void set(T object);

    /**
     * 对象是否为空
     *
     * @return
     */
    boolean isEmpty();
}
