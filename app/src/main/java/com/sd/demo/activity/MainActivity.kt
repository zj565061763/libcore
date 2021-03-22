package com.sd.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sd.demo.R;
import com.sd.demo.databinding.ActMainBinding;
import com.sd.libcore.activity.FStreamActivity;

public class MainActivity extends FStreamActivity
{
    private ActMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        mBinding = ActMainBinding.bind(getContentView());
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == mBinding.btn)
        {
            final Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }
    }
}
