package com.fanwe.library.adapter;

import android.content.Context;

import com.fanwe.lib.adapter.FBaseAdapter;
import com.fanwe.lib.selectmanager.SelectManager;

public abstract class FSelectableBaseAdapter<T> extends FBaseAdapter<T> implements SelectableAdapter<T>
{
    private final SelectManager<T> mSelectManager = new AdapterSelectManager<>(this);

    public FSelectableBaseAdapter()
    {
    }

    public FSelectableBaseAdapter(Context context)
    {
        super(context);
    }

    @Override
    public SelectManager<T> getSelectManager()
    {
        return mSelectManager;
    }
}
