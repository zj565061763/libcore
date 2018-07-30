package com.fanwe.library.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络变化监听
 */
public abstract class FNetworkReceiver extends BaseBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        final String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action))
        {
            int type = getNetworkType(context);
            onNetworkChanged(type);
        }
    }

    /**
     * 网络变化监听
     *
     * @param type {@link ConnectivityManager}
     */
    protected abstract void onNetworkChanged(int type);

    @Override
    public void register(Context context)
    {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, filter);
    }

    //----------utils----------

    private static NetworkInfo getActiveNetworkInfo(Context context)
    {
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context)
    {
        final NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null)
            return false;

        return info.isConnected();
    }

    /**
     * wifi是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context)
    {
        final NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null)
            return false;

        return info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获得网络类型
     *
     * @param context
     * @return {@link ConnectivityManager}
     */
    public static int getNetworkType(Context context)
    {
        final NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null)
            return -1;

        return info.getType();
    }
}
