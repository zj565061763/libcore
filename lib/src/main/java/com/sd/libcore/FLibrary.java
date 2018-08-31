package com.sd.libcore;

import android.app.Application;
import android.content.Context;

import com.sd.libcore.utils.FActivityStack;
import com.sd.libcore.utils.FAppBackgroundListener;

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
            FActivityStack.getInstance().init(application);
            FAppBackgroundListener.getInstance().init(application);
        }
    }
}
