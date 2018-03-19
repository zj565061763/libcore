package com.fanwe.library.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;

import java.util.List;

@Deprecated
public abstract class SDSimpleRecyclerAdapter<T> extends SDRecyclerAdapter<T>
{
    public SDSimpleRecyclerAdapter(Activity activity)
    {
        super(activity);
    }

    public SDSimpleRecyclerAdapter(List<T> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public SDRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType)
    {
        int layoutId = getLayoutId(parent, viewType);
        View itemView = inflate(layoutId, parent);
        SDRecyclerViewHolder<T> holder = new SDRecyclerViewHolder<T>(itemView)
        {
            @Override
            public void onBindData(int position, T model)
            {
            }
        };
        return holder;
    }

    /**
     * 返回布局id
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(ViewGroup parent, int viewType);
}
