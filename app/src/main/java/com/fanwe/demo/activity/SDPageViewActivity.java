package com.fanwe.demo.activity;

import android.os.Bundle;

import com.fanwe.demo.R;
import com.fanwe.demo.view.SDPageView;
import com.fanwe.library.activity.SDBaseActivity;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SDPageViewActivity extends SDBaseActivity
{
    private SDPageView mPageView;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_sdpageview);

        mPageView = find(R.id.view_page);
    }
}
