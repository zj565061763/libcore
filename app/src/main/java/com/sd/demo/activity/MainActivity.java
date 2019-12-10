package com.sd.demo.activity;

import android.os.Bundle;

import com.sd.demo.R;
import com.sd.libcore.activity.FStreamActivity;

public class MainActivity extends FStreamActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }
}
