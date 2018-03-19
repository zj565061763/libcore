package com.fanwe.library.adapter.viewholder;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDAdapter;

@Deprecated
public abstract class SDViewHolder<T> implements
        View.OnClickListener,
        View.OnLongClickListener
{
    public View itemView;
    private T mModel;
    private SDAdapter mAdapter;
    private SparseArray<View> mArrayView;

    public void setAdapter(SDAdapter adapter)
    {
        this.mAdapter = adapter;
    }

    public SDAdapter getAdapter()
    {
        return mAdapter;
    }

    public void setItemView(View itemView)
    {
        this.itemView = itemView;
        this.itemView.setOnClickListener(this);
        this.itemView.setOnLongClickListener(this);
    }

    public T getModel()
    {
        return mModel;
    }

    public void setModel(T model)
    {
        this.mModel = model;
    }

    public int getModelPosition()
    {
        if (mAdapter != null)
        {
            return mAdapter.indexOf(mModel);
        } else
        {
            return -1;
        }
    }

    public Activity getActivity()
    {
        if (mAdapter != null)
        {
            return mAdapter.getActivity();
        } else
        {
            return null;
        }
    }

    /**
     * 用findViewById(id)替代
     */
    @Deprecated
    public <V extends View> V find(int id)
    {
        return (V) itemView.findViewById(id);
    }

    public View findViewById(int id)
    {
        return itemView.findViewById(id);
    }

    /**
     * 在itemView中通过id查找view，如果需要频繁的通过id查找view，调用此方法查找效率较高
     *
     * @param id
     * @param <V>
     * @return
     */
    public <V extends View> V get(int id)
    {
        if (mArrayView == null)
        {
            mArrayView = new SparseArray<>();
        }
        View view = mArrayView.get(id);
        if (view == null)
        {
            view = itemView.findViewById(id);
            if (view != null)
            {
                mArrayView.put(id, view);
            }
        }
        return (V) view;
    }

    @Override
    public void onClick(View view)
    {
        if (view == itemView)
        {
            if (mAdapter != null)
            {
                mAdapter.notifyItemClickCallback(getModelPosition(), getModel(), view);
            }
        }
    }

    @Override
    public boolean onLongClick(View view)
    {
        if (view == itemView)
        {
            if (mAdapter != null)
            {
                mAdapter.notifyItemLongClickCallback(getModelPosition(), getModel(), view);
                return true;
            }
        }
        return false;
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public abstract void onInit(int position, View convertView, ViewGroup parent);

    public abstract void onBindData(int position, View convertView, ViewGroup parent, T model);

    public void onUpdateData(int position, View convertView, ViewGroup parent, T model)
    {
        onBindData(position, convertView, parent, model);
    }

}
