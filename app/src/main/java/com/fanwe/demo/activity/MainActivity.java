package com.fanwe.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fanwe.demo.R;
import com.fanwe.demo.appview.TestView;
import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.library.activity.FTitleActivity;

public class MainActivity extends FTitleActivity
{
    Button btn_recyclerview;
    Button btn_listview;
    Button btn_flexbox;
    Button btn_selectmanager;
    Button btn_sdgridviewpageractivity;

    Button btn;
    TestView testView;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_main);

        btn_recyclerview = findViewById(R.id.btn_recyclerview);
        btn_listview = findViewById(R.id.btn_listview);
        btn_flexbox = findViewById(R.id.btn_flexbox);
        btn_selectmanager = findViewById(R.id.btn_selectmanager);
        btn_sdgridviewpageractivity = findViewById(R.id.btn_sdgridviewpageractivity);

        btn_recyclerview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
            }
        });
        btn_listview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), ListViewActivity.class));
            }
        });
        btn_flexbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), FlexboxActivity.class));
            }
        });
        btn_selectmanager.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), SelectManagerActivity.class));
            }
        });
        btn_sdgridviewpageractivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            }
        });

        testAppView();
        initTitle();
    }

    private void testAppView()
    {
        testView = new TestView(this);
        testView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        testView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        FViewUtil.addView((ViewGroup) findViewById(R.id.fl_container_test), testView);
        testView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                testView.removeSelf();
            }
        });
    }

    private void initTitle()
    {
        getTitleView().addItemLeft().setImageLeft(R.drawable.ic_arrow_left_white);
        getTitleView().addItemMiddle().setTextTop("标题栏").setClickable(false);
    }
}
