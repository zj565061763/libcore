package com.fanwe.library;

import android.app.Application;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.fanwe.lib.eventbus.FEventBus;
import com.fanwe.lib.utils.context.FContext;
import com.fanwe.lib.utils.extend.FActivityStack;
import com.fanwe.lib.utils.extend.FAppBackgroundListener;
import com.fanwe.library.event.EAppBackground;
import com.fanwe.library.event.EAppResumeFromBackground;
import com.fanwe.library.event.ECallStateChanged;

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

    public synchronized void init(Application application)
    {
        if (mContext == null)
        {
            mContext = application;

            FContext.set(application);
            FActivityStack.getInstance().init(application);
            FAppBackgroundListener.getInstance().init(application);

            initInternal();
        }
    }

    private void initInternal()
    {
        FAppBackgroundListener.getInstance().addCallback(new FAppBackgroundListener.Callback()
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
        });

        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new PhoneStateListener()
        {
            @Override
            public void onCallStateChanged(int state, String incomingNumber)
            {
                ECallStateChanged event = new ECallStateChanged();
                event.state = state;
                event.incomingNumber = incomingNumber;
                FEventBus.getDefault().post(event);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
