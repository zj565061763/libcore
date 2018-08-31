package com.fanwe.library.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class LibCoreUtil
{
    /**
     * 返回当前进程的名称
     *
     * @return
     */
    public static String getProcessName(Context context)
    {
        final ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        if (list == null || list.isEmpty())
            return null;

        final int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo item : list)
        {
            if (item.pid == pid)
                return item.processName;
        }
        return null;
    }
}
