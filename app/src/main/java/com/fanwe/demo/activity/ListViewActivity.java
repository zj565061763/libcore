package com.fanwe.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fanwe.demo.R;
import com.fanwe.demo.adapter.ListViewAdapter;
import com.fanwe.demo.model.DataModel;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.listener.SDItemClickCallback;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ListViewActivity extends SDBaseActivity
{
    private ListView lv_content;
    private ListViewAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_listview);
        lv_content = find(R.id.lv_content);


        mAdapter = new ListViewAdapter(DataModel.getListModel(50), this);
        mAdapter.setItemClickCallback(new SDItemClickCallback<DataModel>()
        {
            @Override
            public void onItemClick(int position, DataModel item, View view)
            {
                mAdapter.getSelectManager().performClick(item);
            }
        });
        lv_content.setAdapter(mAdapter);

    }

}
