package com.fanwe.library.adapter;

import com.fanwe.lib.selectmanager.SelectManager;

public interface SelectableAdapter<T>
{
    SelectManager<T> getSelectManager();
}
