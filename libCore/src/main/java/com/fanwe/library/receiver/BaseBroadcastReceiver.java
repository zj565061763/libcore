package com.fanwe.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;

public abstract class BaseBroadcastReceiver extends BroadcastReceiver
{
    /**
     * 注册广播
     *
     * @param context
     */
    public abstract void register(Context context);

    /**
     * 取消注册广播
     *
     * @param context
     */
    public void unregister(Context context)
    {
        context.unregisterReceiver(this);
    }

}
