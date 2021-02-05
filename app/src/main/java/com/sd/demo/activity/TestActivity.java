package com.sd.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.sd.demo.R;
import com.sd.demo.databinding.ActTestBinding;
import com.sd.libcore.activity.FStreamActivity;

public class TestActivity extends FStreamActivity
{
    private ActTestBinding mBinding;
    private static int sCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        mBinding = ActTestBinding.bind(getContentView());
        sCount++;

        mBinding.tvContent.setText(String.valueOf(sCount));
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == mBinding.btnOrder)
        {

        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sCount--;
    }
}
