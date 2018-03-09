package com.fanwe.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fanwe.demo.R;
import com.fanwe.demo.appview.TestView;
import com.fanwe.library.activity.SDBaseActivity;

public class MainActivity extends SDBaseActivity
{
    Button btn_recyclerview;
    Button btn_listview;
    Button btn_flexbox;
    Button btn_selectmanager;
    Button btn_sdgridviewpageractivity;

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
    }

    private void testAppView()
    {
        final TestView testView = new TestView(this);
        testView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        testView.setContainer(findViewById(R.id.fl_container_test));
        testView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                testView.detach();
            }
        });
        testView.attachToContainer(false);
    }
}
