package com.fanwe.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.fanwe.demo.R;
import com.fanwe.demo.adapter.RecyclerViewAdapter;
import com.fanwe.demo.model.DataModel;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.view.SDRecyclerView;

/**
 * Created by Administrator on 2017/4/21.
 */

public class RecyclerViewActivity extends SDBaseActivity
{

    SDRecyclerView rv_content;
    RecyclerViewAdapter adapter;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_recyclerview);
        rv_content = find(R.id.rv_content);
        rv_content.setGridVertical(3);

        SDDrawable drawable = new SDDrawable().color(SDResourcesUtil.getColor(R.color.red)).size(5);
        rv_content.addDividerHorizontal(drawable);
        rv_content.addDividerVertical(drawable);

        adapter = new RecyclerViewAdapter(DataModel.getListModel(100), this);
        adapter.setItemClickCallback(new SDItemClickCallback<DataModel>()
        {
            @Override
            public void onItemClick(int position, DataModel item, View view)
            {
                LogUtil.i("onItemClick:" + position + "," + item);
                adapter.getSelectManager().performClick(item);
            }
        });
        rv_content.setAdapter(adapter);
    }
}
