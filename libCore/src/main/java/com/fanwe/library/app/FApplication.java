package com.fanwe.library.app;

import android.app.Application;

import com.fanwe.lib.utils.FOtherUtil;
import com.fanwe.library.FLibrary;

/**
 * Created by zhengjun on 2018/3/28.
 */
public abstract class FApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        final String processName = FOtherUtil.getProcessName(this);
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
