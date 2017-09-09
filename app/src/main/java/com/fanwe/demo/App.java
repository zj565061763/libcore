package com.fanwe.demo;

import android.app.Application;

import com.fanwe.library.SDLibrary;

/**
 * Created by Administrator on 2017/5/27.
 */

public class App extends Application
{

    private static App sInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;

        SDLibrary.getInstance().init(this);
    }

    public static App getInstance()
    {
        return sInstance;
    }
}
