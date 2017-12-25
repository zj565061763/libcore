package com.fanwe.demo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.demo.R;
import com.fanwe.demo.model.DataModel;
import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.library.adapter.SDSimpleAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ListViewAdapter extends SDSimpleAdapter<DataModel>
{
    public ListViewAdapter(List<DataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_recyclerview;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, DataModel model)
    {
        TextView tv_name = get(R.id.tv_name, convertView);
        tv_name.setText(model.getName());

        if (model.isSelected())
        {
            FViewUtil.setBackgroundColorResId(convertView, R.color.red, getActivity());
        } else
        {
            FViewUtil.setBackgroundColorResId(convertView, R.color.green, getActivity());
        }

        convertView.setOnClickListener(this);
    }
}
