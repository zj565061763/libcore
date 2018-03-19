package com.fanwe.library.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDRecyclerAdapter;

@Deprecated
public abstract class SDRecyclerViewHolder<T> extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnLongClickListener
{
    private T mModel;
    private SDRecyclerAdapter mAdapter;
    private SparseArray<View> mArrayView;

    public SDRecyclerViewHolder(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public SDRecyclerViewHolder(ViewGroup parent, int layoutId)
    {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public void setAdapter(SDRecyclerAdapter adapter)
    {
        this.mAdapter = adapter;
    }

    public SDRecyclerAdapter getAdapter()
    {
        return mAdapter;
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

    /**
     * 绑定数据
     *
     * @param position
     * @param model
     */
    public abstract void onBindData(int position, T model);

    /**
     * 刷新item的时候触发，默认整个item重新绑定数据
     *
     * @param position
     * @param model
     */
    public void onUpdateData(int position, T model)
    {
        onBindData(position, model);
    }
}
