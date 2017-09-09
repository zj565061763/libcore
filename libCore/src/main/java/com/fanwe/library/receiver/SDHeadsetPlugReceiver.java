package com.fanwe.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.fanwe.library.holder.ISDObjectsHolder;
import com.fanwe.library.holder.SDObjectsHolder;
import com.fanwe.library.listener.SDIterateCallback;

import java.util.Iterator;

/**
 * 耳机插拔监听
 */
public class SDHeadsetPlugReceiver extends BroadcastReceiver
{

    private static BroadcastReceiver sReceiver;
    private static ISDObjectsHolder<HeadsetPlugCallback> sCallbackHolder = new SDObjectsHolder<>();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        sReceiver = this;

        if (intent.hasExtra("state"))
        {
            int state = intent.getIntExtra("state", 0);

            final boolean isHeadsetPlug = state == 1;

            sCallbackHolder.foreach(new SDIterateCallback<HeadsetPlugCallback>()
            {
                @Override
                public boolean next(int i, HeadsetPlugCallback item, Iterator<HeadsetPlugCallback> it)
                {
                    item.onHeadsetPlugChange(isHeadsetPlug);
                    return false;
                }
            });
        }
    }

    public static BroadcastReceiver getReceiver()
    {
        if (sReceiver == null)
        {
            sReceiver = new SDHeadsetPlugReceiver();
        }
        return sReceiver;
    }

    /**
     * 注册广播
     *
     * @param context
     */
    public static void registerReceiver(Context context)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
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
    public static void addCallback(HeadsetPlugCallback callback)
    {
        sCallbackHolder.add(callback);
    }

    /**
     * 移除回调
     *
     * @param callback
     */
    public static void removeCallback(HeadsetPlugCallback callback)
    {
        sCallbackHolder.remove(callback);
    }

    public interface HeadsetPlugCallback
    {
        /**
         * 耳机插拔回调
         *
         * @param plug true-耳机插入
         */
        void onHeadsetPlugChange(boolean plug);
    }

}
