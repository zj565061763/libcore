package com.fanwe.library.listener;

import android.view.View;

@Deprecated
public interface SDItemLongClickCallback<T>
{
    void onItemLongClick(int position, T item, View view);
}
