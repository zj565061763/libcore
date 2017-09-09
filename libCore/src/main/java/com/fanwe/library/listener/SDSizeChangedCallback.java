package com.fanwe.library.listener;

/**
 * Created by Administrator on 2016/8/13.
 */
public interface SDSizeChangedCallback<T>
{
    void onWidthChanged(int newWidth, int oldWidth, int differ, T target);

    void onHeightChanged(int newHeight, int oldHeight, int differ, T target);
}
