package com.fanwe.library.adapter;

import android.app.Activity;

import com.fanwe.lib.adapter.FSimpleRecyclerAdapter;
import com.fanwe.lib.selectmanager.SelectManager;

public abstract class FSelectableSimpleRecyclerAdapter<T> extends FSimpleRecyclerAdapter<T> implements SelectableAdapter<T>
{
    private SelectManager<T> mSelectManager = new AdapterSelectManager<>(this);

    public FSelectableSimpleRecyclerAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public SelectManager<T> getSelectManager()
    {
        return mSelectManager;
    }
}
