package com.fanwe.library.listener;

/**
 * 遍历回调
 */
public interface SDSimpleIterateCallback
{
    /**
     * 返回true，结束遍历
     *
     * @param i
     * @return
     */
    boolean next(int i);
}
