package com.fanwe.library;

import android.content.Context;

import com.fanwe.library.common.SDCookieManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.SDCache;

public class SDLibrary
{
    private static SDLibrary sInstance;

    private Context mContext;

    private SDLibrary()
    {
    }

    public Context getContext()
    {
        return mContext;
    }

    public void init(Context context)
    {
        this.mContext = context;
        SDConfig.getInstance().init(context);
        SDCookieManager.getInstance().init(context);
        SDCache.init(context);
    }

    public static SDLibrary getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new SDLibrary();
        }
        return sInstance;
    }

}
