package com.fanwe.library.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.viewholder.SDViewHolder;

import java.util.List;

@Deprecated
public abstract class SDViewHolderAdapter<T> extends SDAdapter<T>
{

    public SDViewHolderAdapter(List<T> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    protected final void onUpdateView(int position, View convertView, ViewGroup parent, T model)
    {
        SDViewHolder<T> holder = (SDViewHolder<T>) convertView.getTag();
        holder.onUpdateData(position, convertView, parent, model);
        onUpdateData(position, convertView, parent, model, holder);
    }

    /**
     * 当调用item刷新的时候会触发此方法
     *
     * @param position
     * @param convertView
     * @param parent
     * @param model
     * @param holder
     */
    protected void onUpdateData(int position, View convertView, ViewGroup parent, T model, SDViewHolder<T> holder)
    {
        onBindData(position, convertView, parent, model, holder);
    }

    @Override
    public final View onGetView(int position, View convertView, ViewGroup parent)
    {
        SDViewHolder<T> holder = null;
        if (convertView == null)
        {
            holder = onCreateVHolder(position, convertView, parent);
            holder.setAdapter(this);

            int layoutId = holder.getLayoutId(position, convertView, parent);
            convertView = inflate(layoutId, parent);
            holder.setItemView(convertView);
            holder.onInit(position, convertView, parent);
            convertView.setTag(holder);
        } else
        {
            holder = (SDViewHolder<T>) convertView.getTag();
        }
        T model = getData(position);
        holder.setModel(model);

        holder.onBindData(position, convertView, parent, model);
        onBindData(position, convertView, parent, model, holder);
        return convertView;
    }

    public abstract SDViewHolder<T> onCreateVHolder(int position, View convertView, ViewGroup parent);

    public abstract void onBindData(int position, View convertView, ViewGroup parent, T model, SDViewHolder<T> holder);

}
