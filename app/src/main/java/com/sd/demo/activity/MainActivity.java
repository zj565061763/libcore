package com.sd.demo.activity;

import android.os.Bundle;

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
}
