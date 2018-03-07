package com.fanwe.library;

import android.content.Context;

import com.fanwe.lib.eventbus.FEventBus;
import com.fanwe.lib.utils.context.FContext;
import com.fanwe.lib.utils.extend.FActivityStack;
import com.fanwe.lib.utils.extend.FAppBackgroundListener;
import com.fanwe.library.event.EAppBackground;
import com.fanwe.library.event.EAppResumeFromBackground;

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

    public Context getContext()
    {
        return mContext;
    }

    public synchronized void init(Context context)
    {
        if (mContext != null)
        {
            return;
        }
        mContext = context.getApplicationContext();

        FContext.set(context);
        FActivityStack.getInstance().init(context);
        FAppBackgroundListener.getInstance().init(context);

        initInternal();
    }

    private void initInternal()
    {
        FAppBackgroundListener.getInstance().addCallback(mAppBackgroundCallback);
    }

    /**
     * App前后台切换回调
     */
    private FAppBackgroundListener.Callback mAppBackgroundCallback = new FAppBackgroundListener.Callback()
    {
        @Override
        public void onBackground()
        {
            EAppBackground event = new EAppBackground();
            FEventBus.getDefault().post(event);
        }

        @Override
        public void onResumeFromBackground()
        {
            EAppResumeFromBackground event = new EAppResumeFromBackground();
            FEventBus.getDefault().post(event);
        }
    };
}
