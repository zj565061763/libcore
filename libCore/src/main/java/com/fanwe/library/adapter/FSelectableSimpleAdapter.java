package com.fanwe.library.adapter;

import android.content.Context;

import com.fanwe.lib.adapter.FSimpleAdapter;
import com.fanwe.lib.selectmanager.SelectManager;

public abstract class FSelectableSimpleAdapter<T> extends FSimpleAdapter<T> implements SelectableAdapter<T>
{
    private final SelectManager<T> mSelectManager = new AdapterSelectManager<>(this);

    public FSelectableSimpleAdapter()
    {
    }

    public FSelectableSimpleAdapter(Context context)
    {
        super(context);
    }

    @Override
    public SelectManager<T> getSelectManager()
    {
        return mSelectManager;
    }
}
