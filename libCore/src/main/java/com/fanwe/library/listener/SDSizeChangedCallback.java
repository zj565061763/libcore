package com.fanwe.library.listener;

/**
 * Created by Administrator on 2016/8/13.
 */
public interface SDSizeChangedCallback<T>
{
    void onWidthChanged(int newWidth, int oldWidth, T target);

    void onHeightChanged(int newHeight, int oldHeight, T target);
}
