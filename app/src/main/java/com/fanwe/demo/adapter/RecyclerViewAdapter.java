package com.fanwe.demo.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.demo.R;
import com.fanwe.demo.model.DataModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class RecyclerViewAdapter extends SDSimpleRecyclerAdapter<DataModel>
{
    public RecyclerViewAdapter(List<DataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_recyclerview;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<DataModel> holder, int position, DataModel model)
    {
        TextView tv_name = holder.get(R.id.tv_name);
        tv_name.setText(model.getName());

        if (model.isSelected())
        {
            SDViewUtil.setBackgroundColorResId(holder.itemView, R.color.red);
        } else
        {
            SDViewUtil.setBackgroundColorResId(holder.itemView, R.color.green);
        }
    }
}
