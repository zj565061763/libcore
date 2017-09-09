package com.fanwe.library.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences管理类
 */
public class SDConfig
{

    private static SDConfig sInstance;

    private Context mContext;

    private SDConfig()
    {
    }

    public static SDConfig getInstance()
    {
        if (sInstance == null)
        {
            synchronized (SDConfig.class)
            {
                if (sInstance == null)
                {
                    sInstance = new SDConfig();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context)
    {
        this.mContext = context.getApplicationContext();
    }

    private void checkInit()
    {
        if (mContext == null)
        {
            throw new NullPointerException("please invoke init() first");
        }
    }

    private SharedPreferences getSharedPreferences()
    {
        checkInit();
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setBoolean(String key, boolean value)
    {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public void setFloat(String key, float value)
    {
        getSharedPreferences().edit().putFloat(key, value).commit();
    }

    public void setInt(String key, int value)
    {
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public void setLong(String key, long value)
    {
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public void setString(String key, String value)
    {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    // extend
    public void setDouble(String key, double value)
    {
        setString(key, String.valueOf(value));
    }

    public void setShort(String key, short value)
    {
        setString(key, String.valueOf(value));
    }

    private String resIdToString(int resId)
    {
        checkInit();
        return mContext.getString(resId);
    }

    public void setString(int resId, String value)
    {
        setString(resIdToString(resId), value);
    }

    public void setInt(int resId, int value)
    {
        setInt(resIdToString(resId), value);
    }

    public void setBoolean(int resId, boolean value)
    {
        setBoolean(resIdToString(resId), value);
    }

    public void setShort(int resId, short value)
    {
        setShort(resIdToString(resId), value);
    }

    public void setLong(int resId, long value)
    {
        setLong(resIdToString(resId), value);
    }

    public void setFloat(int resId, float value)
    {
        setFloat(resIdToString(resId), value);
    }

    public void setDouble(int resId, double value)
    {
        setDouble(resIdToString(resId), value);
    }

    public String getString(String key, String defaultValue)
    {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue)
    {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue)
    {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public short getShort(String key, short defaultValue)
    {
        try
        {
            return Short.valueOf(getString(key, ""));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public long getLong(String key, long defaultValue)
    {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue)
    {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue)
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

    public String getString(int resId, String defaultValue)
    {
        return getString(resIdToString(resId), defaultValue);
    }

    public int getInt(int resId, int defaultValue)
    {
        return getInt(resIdToString(resId), defaultValue);
    }

    public boolean getBoolean(int resId, boolean defaultValue)
    {
        return getBoolean(resIdToString(resId), defaultValue);
    }

    public short getShort(int resId, short defaultValue)
    {
        return getShort(resIdToString(resId), defaultValue);
    }

    public long getLong(int resId, long defaultValue)
    {
        return getLong(resIdToString(resId), defaultValue);
    }

    public float getFloat(int resId, float defaultValue)
    {
        return getFloat(resIdToString(resId), defaultValue);
    }

    public double getDouble(int resId, double defaultValue)
    {
        return getDouble(resIdToString(resId), defaultValue);
    }

    public void remove(String key)
    {
        getSharedPreferences().edit().remove(key).commit();
    }

    public void remove(int resId)
    {
        remove(resIdToString(resId));
    }

    public void clear()
    {
        getSharedPreferences().edit().clear().commit();
    }
}
