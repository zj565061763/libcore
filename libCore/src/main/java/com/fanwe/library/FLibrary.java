package com.fanwe.library;

import android.app.Application;
import android.content.Context;

import com.fanwe.lib.utils.context.FContext;
import com.fanwe.library.utils.FActivityStack;
import com.fanwe.library.utils.FAppBackgroundListener;

public class FLibrary
{
    private static FLibrary sInstance;
    private Context mContext;

    private FLibrary()
    {
    }

    public static FLibrary getInstance()
    {
        if (sInstance == null)
        {
            synchronized (FLibrary.class)
            {
                if (sInstance == null)
                    sInstance = new FLibrary();
            }
        }
        return sInstance;
    }

    public Context getContext()
    {
        return mContext;
    }

    public synchronized void init(Application application)
    {
        if (mContext == null)
        {
            mContext = application;
            FContext.set(application);
            FActivityStack.getInstance().init(application);
            FAppBackgroundListener.getInstance().init(application);
        }
    }
}
