package com.fanwe.library.app;

import android.app.Application;

import com.fanwe.library.SDLibrary;

public class FApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        SDLibrary.getInstance().init(this);
    }
}
