package com.fanwe.library.utils;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;

/**
 * Created by Administrator on 2016/10/27.
 */

public class SDSystemUtil
{


    /**
     * 获得系统亮度
     *
     * @param context
     * @return 0-255
     */
    public static int getScreenBrightness(Context context)
    {
        int value = -1;
        try
        {
            value = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 设置系统亮度
     *
     * @param context
     * @param value   0-255
     * @return true-设置成功
     */
    public static boolean setScreenBrightness(Context context, int value)
    {
        return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
    }

    /**
     * 获得亮度模式
     *
     * @param context
     * @return 0-正常模式，1-自动模式
     */
    public static int getScreenBrightnessMode(Context context)
    {
        int value = -1;
        try
        {
            value = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    public static int getStreamVoiceCall(Context context)
    {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int value = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        return value;
    }

    public static int getStreamSystem(Context context)
    {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int value = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        return value;
    }

}
