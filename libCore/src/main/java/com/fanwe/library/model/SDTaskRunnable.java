package com.fanwe.library.model;


import android.os.Handler;
import android.os.Looper;

@Deprecated
public abstract class SDTaskRunnable<T> implements Runnable
{
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public final void run()
    {
        try
        {
            T result = onBackground();
            notifyMainThread(result);
        } catch (Exception e)
        {
            notifyError(e);
        }
    }

    private void notifyMainThread(final T result)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                onMainThread(result);
            }
        });
    }

    private void notifyError(final Exception e)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                onError(e);
            }
        });
    }

    public abstract T onBackground();

    public abstract void onMainThread(T result);

    public void onError(Exception e)
    {

    }
}
