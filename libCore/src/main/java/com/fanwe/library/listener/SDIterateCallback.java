package com.fanwe.library.listener;

import java.util.Iterator;

/**
 * 遍历回调
 *
 * @param <T>
 */
public interface SDIterateCallback<T>
{
    /**
     * 返回true，结束遍历
     *
     * @param i
     * @param item
     * @param it
     * @return
     */
    boolean next(int i, T item, Iterator<T> it);
}
