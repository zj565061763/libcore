package com.fanwe.library.adapter;

import android.app.Activity;

import com.fanwe.lib.adapter.FRecyclerAdapter;
import com.fanwe.lib.selectmanager.SelectManager;

public abstract class FSelectableRecyclerAdapter<T> extends FRecyclerAdapter<T> implements SelectableAdapter<T>
{
    private SelectManager<T> mSelectManager = new AdapterSelectManager<>(this);

    public FSelectableRecyclerAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public SelectManager<T> getSelectManager()
    {
        return mSelectManager;
    }
}
