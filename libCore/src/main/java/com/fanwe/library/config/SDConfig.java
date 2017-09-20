package com.fanwe.library.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类
 */
public class SDConfig
{
    private static Context mContext;

    public static void init(Context context)
    {
        mContext = context.getApplicationContext();
    }

    public static SharedPreferences getSharedPreferences()
    {
        if (mContext == null)
        {
            throw new NullPointerException("please invoke init() first");
        }
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    // put
    public static void putBoolean(String key, boolean value)
    {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static void putFloat(String key, float value)
    {
        getSharedPreferences().edit().putFloat(key, value).commit();
    }

    public static void putInt(String key, int value)
    {
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static void putLong(String key, long value)
    {
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static void putString(String key, String value)
    {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static void putDouble(String key, double value)
    {
        putString(key, String.valueOf(value));
    }

    // get
    public static String getString(String key, String defaultValue)
    {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue)
    {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue)
    {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue)
    {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue)
    {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public static double getDouble(String key, double defaultValue)
    {
        try
        {
            return Double.valueOf(getString(key, ""));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static void remove(String key)
    {
        getSharedPreferences().edit().remove(key).commit();
    }

    public static void clear()
    {
        getSharedPreferences().edit().clear().commit();
    }
}
