package com.fanwe.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.fanwe.demo.R;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.select.SDSelectViewGroup;

/**
 * Created by Administrator on 2017/6/22.
 */

public class SelectManagerActivity extends SDBaseActivity
{
    private SDSelectViewGroup svg_item;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_select_manager);

        svg_item = (SDSelectViewGroup) findViewById(R.id.svg_item);
        svg_item.getSelectViewManager().addSelectCallback(new SDSelectManager.SelectCallback<View>()
        {
            @Override
            public void onNormal(int index, View item)
            {

            }

            @Override
            public void onSelected(int index, View item)
            {
                SDToast.showToast("" + index);
            }
        });
        svg_item.getSelectViewManager().performClick(0);
    }
}
