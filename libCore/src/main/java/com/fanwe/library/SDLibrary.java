package com.fanwe.library;

import android.content.Context;

import com.fanwe.lib.utils.context.FContext;
import com.fanwe.lib.utils.extend.FActivityStack;
import com.fanwe.lib.utils.extend.FAppBackgroundListener;

public class SDLibrary
{
    private static SDLibrary sInstance;
    private Context mContext;

    private SDLibrary()
    {
    }

    public static SDLibrary getInstance()
    {
        if (sInstance == null)
        {
            synchronized (SDLibrary.class)
            {
                sInstance = new SDLibrary();
            }
        }
        return sInstance;
    }

    public void init(Context context)
    {
        mContext = context.getApplicationContext();

        FContext.set(context);
        FActivityStack.getInstance().init(context);
        FAppBackgroundListener.getInstance().init(context);
    }

    public Context getContext()
    {
        return mContext;
    }
}
