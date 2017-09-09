package com.fanwe.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fanwe.demo.R;
import com.fanwe.demo.appview.TestView;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.utils.SDDurationTrigger;

public class MainActivity extends SDBaseActivity
{
    Button btn_recyclerview;
    Button btn_listview;
    Button btn_flexbox;
    Button btn_selectmanager;
    Button btn_sdgridviewpageractivity;

    Button btn;
    TestView testView;

    SDDurationTrigger mDurationTrigger = new SDDurationTrigger();

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_main);

        btn_recyclerview = find(R.id.btn_recyclerview);
        btn_listview = find(R.id.btn_listview);
        btn_flexbox = find(R.id.btn_flexbox);
        btn_selectmanager = find(R.id.btn_selectmanager);
        btn_sdgridviewpageractivity = find(R.id.btn_sdgridviewpageractivity);

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

    @Override
    protected int onCreateTitleViewResId()
    {
        return R.layout.view_title;
    }

    private void testAppView()
    {
        testView = new TestView(this);
        testView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        testView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        addView(R.id.fl_container_test, testView);
        testView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                testView.removeSelf();
            }
        });
    }

    Toast mExitToast;

    @Override
    public void onBackPressed()
    {
        if (mDurationTrigger.trigger())
        {
            super.onBackPressed();
        } else
        {
            if (mExitToast != null)
            {
                mExitToast.cancel();
            }
            mExitToast = Toast.makeText(this, "再按" + mDurationTrigger.getLeftTriggerCount() + "次退出", Toast.LENGTH_SHORT);
            mExitToast.show();
        }
    }
}
