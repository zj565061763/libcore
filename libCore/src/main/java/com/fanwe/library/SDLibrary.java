package com.fanwe.library;

import android.content.Context;

import com.fanwe.library.common.SDCookieManager;
import com.fanwe.library.config.SDConfig;

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
        mContext = context.getApplicationContext();

        SDConfig.init(context);
        SDCookieManager.getInstance().init(context);
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
