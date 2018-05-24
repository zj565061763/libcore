package com.fanwe.library.adapter;

import android.app.Activity;

import com.fanwe.lib.adapter.FSimpleAdapter;
import com.fanwe.lib.selectmanager.SelectManager;

public abstract class FSelectableSimpleAdapter<T> extends FSimpleAdapter<T> implements SelectableAdapter<T>
{
    private SelectManager<T> mSelectManager = new AdapterSelectManager<>(this);

    public FSelectableSimpleAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public SelectManager<T> getSelectManager()
    {
        return mSelectManager;
    }
}
