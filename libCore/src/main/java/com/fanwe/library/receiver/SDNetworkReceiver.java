package com.fanwe.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fanwe.library.holder.ISDObjectsHolder;
import com.fanwe.library.holder.SDObjectsHolder;
import com.fanwe.library.listener.SDIterateCallback;

import java.util.Iterator;

/**
 * 网络变化监听
 */
public class SDNetworkReceiver extends BroadcastReceiver
{

    public final static String FANWE_ANDROID_NET_CHANGE_ACTION = "fanwe.android.net.conn.CONNECTIVITY_CHANGE";

    private static BroadcastReceiver sReceiver;
    private static ISDObjectsHolder<SDNetworkCallback> sCallbackHolder = new SDObjectsHolder<>();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        sReceiver = this;

        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action) || FANWE_ANDROID_NET_CHANGE_ACTION.equals(action))
        {
            final NetworkType type = getNetworkType(context);
            sCallbackHolder.foreach(new SDIterateCallback<SDNetworkCallback>()
            {
                @Override
                public boolean next(int i, SDNetworkCallback item, Iterator<SDNetworkCallback> it)
                {
                    item.onNetworkChanged(type);
                    return false;
                }
            });
        }
    }

    public static BroadcastReceiver getReceiver()
    {
        if (sReceiver == null)
        {
            sReceiver = new SDNetworkReceiver();
        }
        return sReceiver;
    }

    /**
     * 发送网络检测广播
     *
     * @param context
     */
    public static void sendBroadcast(Context context)
    {
        Intent intent = new Intent();
        intent.setAction(FANWE_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    /**
     * 注册广播
     *
     * @param context
     */
    public static void registerReceiver(Context context)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(FANWE_ANDROID_NET_CHANGE_ACTION);
        context.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 取消注册广播
     *
     * @param context
     */
    public static void unregisterReceiver(Context context)
    {
        if (sReceiver != null)
        {
            try
            {
                context.getApplicationContext().unregisterReceiver(sReceiver);
            } catch (Exception e)
            {
            }
        }
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public static void addCallback(SDNetworkCallback callback)
    {
        sCallbackHolder.add(callback);
    }

    /**
     * 移除回调
     *
     * @param callback
     */
    public static void removeCallback(SDNetworkCallback callback)
    {
        sCallbackHolder.remove(callback);
    }

    //----------utils----------

    /**
     * 获得ConnectivityManager对象
     *
     * @param context
     * @return
     */
    public static ConnectivityManager getConnectivityManager(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager;
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null)
        {
            return info.isConnected();
        } else
        {
            return false;
        }
    }

    /**
     * wifi是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return false;
        } else
        {
            return ConnectivityManager.TYPE_WIFI == networkInfo.getType();
        }
    }

    /**
     * 获得网络类型
     *
     * @param context
     * @return
     */
    public static NetworkType getNetworkType(Context context)
    {
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return NetworkType.None;
        } else
        {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_MOBILE)
            {
                return NetworkType.Mobile;
            } else if (type == ConnectivityManager.TYPE_WIFI)
            {
                return NetworkType.Wifi;
            } else
            {
                return NetworkType.None;
            }
        }
    }

    public enum NetworkType
    {
        /**
         * wifi网络
         */
        Wifi,
        /**
         * 移动数据网络
         */
        Mobile,
        /**
         * 无网络
         */
        None
    }

    public interface SDNetworkCallback
    {
        /**
         * 网络变化监听
         *
         * @param type
         */
        void onNetworkChanged(NetworkType type);
    }

}
