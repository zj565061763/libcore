package com.fanwe.library.model;

import com.fanwe.library.common.SDHandlerManager;

/**
 * Created by Administrator on 2016/7/18.
 */
public abstract class SDTaskRunnable<T> implements Runnable
{

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
        SDHandlerManager.post(new Runnable()
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
        SDHandlerManager.post(new Runnable()
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
