package com.sd.libcore.app;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

import com.sd.libcore.FLibrary;
import com.sd.libcore.utils.LibCoreUtil;

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
        } else
        {
            onCreateOtherProcess(processName);
        }
    }

    /**
     * 主进程调用
     */
    protected abstract void onCreateMainProcess();

    /**
     * 其他进程回调
     *
     * @param processName
     */
    protected void onCreateOtherProcess(String processName)
    {
        if (Build.VERSION.SDK_INT >= 28)
        {
            WebView.setDataDirectorySuffix(processName);
        }
    }
}
