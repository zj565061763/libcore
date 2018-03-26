package com.fanwe.library.listener;

import android.view.View;

@Deprecated
public interface SDItemClickCallback<T>
{
    void onItemClick(int position, T item, View view);
}
