package com.fanwe.library;

import android.content.Context;

import com.fanwe.lib.utils.FContext;
import com.fanwe.lib.utils.extend.FCookieManager;

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

        FCookieManager.getInstance().init(context);
        FContext.set(context);
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
