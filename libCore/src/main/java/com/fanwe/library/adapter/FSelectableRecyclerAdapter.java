package com.fanwe.library.adapter;

import android.content.Context;

import com.fanwe.lib.adapter.FRecyclerAdapter;
import com.fanwe.lib.selectmanager.SelectManager;

public abstract class FSelectableRecyclerAdapter<T> extends FRecyclerAdapter<T> implements SelectableAdapter<T>
{
    private final SelectManager<T> mSelectManager = new AdapterSelectManager<>(this);

    public FSelectableRecyclerAdapter()
    {
    }

    public FSelectableRecyclerAdapter(Context context)
    {
        super(context);
    }

    @Override
    public SelectManager<T> getSelectManager()
    {
        return mSelectManager;
    }
}
