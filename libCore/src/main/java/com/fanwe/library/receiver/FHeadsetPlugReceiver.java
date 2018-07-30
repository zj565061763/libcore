package com.fanwe.library.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

/**
 * 耳机插拔监听
 */
public abstract class FHeadsetPlugReceiver extends BaseBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.hasExtra("state"))
        {
            final int state = intent.getIntExtra("state", 0);
            onHeadsetPlugChange(state == 1);
        }
    }

    /**
     * 耳机插拔回调
     *
     * @param plug true-耳机插入
     */
    protected abstract void onHeadsetPlugChange(boolean plug);

    @Override
    public void register(Context context)
    {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.registerReceiver(this, filter);
    }

    /**
     * 耳机是否已经插入
     *
     * @param context
     * @return
     */
    public static boolean isHeadsetPlug(Context context)
    {
        final AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return manager.isWiredHeadsetOn();
    }
}
