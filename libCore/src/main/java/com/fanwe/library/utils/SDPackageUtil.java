package com.fanwe.library.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.fanwe.library.SDLibrary;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * package包工具类
 *
 * @author zhengjun
 */
public class SDPackageUtil
{

    /**
     * 获取设备ID
     */
    public static String getDeviceId()
    {
        Context context = SDLibrary.getInstance().getContext().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static void chmod(String permission, String path)
    {
        try
        {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Boolean isPackageExist(String packageName)
    {
        PackageManager manager = SDLibrary.getInstance().getContext().getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (PackageInfo pi : pkgList)
        {
            if (pi.packageName.equalsIgnoreCase(packageName))
            {
                return true;
            }
        }
        return false;
    }

    public static PackageInfo getApkPackageInfo(String apkFilePath)
    {
        PackageManager pm = SDLibrary.getInstance().getContext().getPackageManager();
        PackageInfo apkInfo = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_META_DATA);
        return apkInfo;
    }

    public static PackageInfo getPackageInfo(String packageName)
    {
        PackageInfo apkInfo = null;
        try
        {
            PackageManager pm = SDLibrary.getInstance().getContext().getPackageManager();
            apkInfo = pm.getPackageInfo(packageName, 0);
        } catch (Exception e)
        {
        }
        return apkInfo;
    }

    public static PackageInfo getCurrentPackageInfo()
    {
        return getPackageInfo(getPackageName());
    }

    public static boolean installApkPackage(String path)
    {
        if (path == null)
        {
            return false;
        }

        if (!new File(path).exists())
        {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        SDLibrary.getInstance().getContext().startActivity(intent);
        return true;
    }

    public static Bundle getMetaData(Context context)
    {
        try
        {
            ApplicationInfo info = SDLibrary.getInstance().getContext().getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPackageName()
    {
        return SDLibrary.getInstance().getContext().getPackageName();
    }

    public static void startAPP(String appPackageName)
    {
        try
        {
            Intent intent = SDLibrary.getInstance().getContext().getPackageManager().getLaunchIntentForPackage(appPackageName);
            SDLibrary.getInstance().getContext().startActivity(intent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void startCurrentApp()
    {
        startAPP(SDLibrary.getInstance().getContext().getPackageName());
    }

    public static boolean isBackground()
    {
        ActivityManager activityManager = (ActivityManager) SDLibrary.getInstance().getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses)
        {
            if (appProcess.processName.equals(getPackageName()))
            {
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    return true;
                } else
                {
                    return false;
                }
            }
        }
        return false;
    }

    public static int getVersionCode()
    {
        PackageInfo pi = getCurrentPackageInfo();
        return pi.versionCode;
    }

    public static String getVersionName()
    {
        PackageInfo pi = getCurrentPackageInfo();
        return pi.versionName;
    }

    public static boolean isAppInstalled(Context context, String packagename)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
            return packageInfo != null;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 获得进程名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context)
    {
        int pid = android.os.Process.myPid();

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null)
        {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps)
        {
            if (procInfo.pid == pid)
            {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 是否是主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context)
    {
        return context.getPackageName().equals(getProcessName(context));
    }

}
