package com.fanwe.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2016/9/21.
 */

public class SDPermissionUtil
{

    public static final int DEFAULT_REQUEST_CODE = 1;

    /**
     * 检查权限
     *
     * @param context
     * @param permission
     * @return true:通过
     */
    public static boolean check(Context context, String permission)
    {
        int result = ContextCompat.checkSelfPermission(context, permission);
        return PackageManager.PERMISSION_GRANTED == result;
    }

    /**
     * 摄像头
     *
     * @param context
     * @return
     */
    public static boolean check_CAMERA(Context context)
    {
        String permission = Manifest.permission.CAMERA;
        return check(context, permission);
    }

    /**
     * 录音
     *
     * @param context
     * @return
     */
    public static boolean check_RECORD_AUDIO(Context context)
    {
        String permission = Manifest.permission.RECORD_AUDIO;
        return check(context, permission);
    }

    /**
     * 悬浮窗
     *
     * @param context
     * @return
     */
    public static boolean check_SYSTEM_ALERT_WINDOW(Context context)
    {
        String permission = Manifest.permission.SYSTEM_ALERT_WINDOW;
        return check(context, permission);
    }

    // request
    public static void request_CAMERA(Activity activity)
    {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(activity, permissions, DEFAULT_REQUEST_CODE);
    }

    public static void request_RECORD_AUDIO(Activity activity)
    {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        ActivityCompat.requestPermissions(activity, permissions, DEFAULT_REQUEST_CODE);
    }

}
