package com.sd.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sd.demo.R;
import com.sd.demo.databinding.ActTestBinding;
import com.sd.libcore.activity.FStreamActivity;
import com.sd.libcore.utils.FActivityStack;

public class TestActivity extends FStreamActivity
{
    private ActTestBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        mBinding = ActTestBinding.bind(getContentView());
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == mBinding.btnOrder)
        {
            // 压入栈底
            FActivityStack.getInstance().pushToBottom(this);
        }
    }
}
