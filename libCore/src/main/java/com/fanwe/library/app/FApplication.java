package com.fanwe.library.app;

import android.app.Application;

import com.fanwe.library.FLibrary;
import com.fanwe.library.utils.LibCoreUtil;

public abstract class FApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        final String processName = LibCoreUtil.getProcessName(this);
        if (getPackageName().equals(processName))
        {
            FLibrary.getInstance().init(this);
            onCreateMainProcess();
        }
    }

    /**
     * 主进程调用
     */
    protected abstract void onCreateMainProcess();
}
