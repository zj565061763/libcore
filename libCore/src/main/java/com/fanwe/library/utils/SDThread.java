package com.fanwe.library.utils;

/**
 * Created by Administrator on 2016/11/16.
 */

public abstract class SDThread extends Thread
{

    private boolean isPaused = false;
    private boolean isStopped = false;

    private boolean isLoop = false;

    public final boolean isLoop()
    {
        return isLoop;
    }

    public final boolean isPaused()
    {
        return isPaused;
    }

    public final boolean isStopped()
    {
        return isStopped || isInterrupted();
    }

    /**
     * 暂停线程
     */
    public final void pauseThread()
    {
        synchronized (this)
        {
            isPaused = true;
        }
    }

    /**
     * 恢复线程
     */
    public final void resumeThread()
    {
        synchronized (this)
        {
            isPaused = false;
            notify();
        }
    }

    /**
     * 恢复线程（所有持有当前锁的线程）
     */
    public final void resumeThreadAll()
    {
        synchronized (this)
        {
            isPaused = false;
            notifyAll();
        }
    }

    /**
     * 暂停线程
     */
    protected final void waitThread()
    {
        synchronized (this)
        {
            try
            {
                wait();
            } catch (Exception e)
            {
                onException(e);
            }
        }
    }

    /**
     * 停止线程
     */
    public final void stopThread()
    {
        synchronized (this)
        {
            isLoop = false;
            isPaused = false;
            isStopped = true;
            interrupt();
            notifyAll();
        }
    }

    /**
     * 开始线程循环（会不断触发runBackground()方法）
     */
    public synchronized void startLoop()
    {
        synchronized (this)
        {
            isLoop = true;
        }
        start();
    }

    @Override
    public final void run()
    {
        try
        {
            do
            {
                if (isPaused)
                {
                    onPaused();
                    waitThread();
                }

                super.run();
                runBackground();

            } while (isLoop && !isStopped());
        } catch (Exception e)
        {
            onException(e);
        }
    }

    protected void onPaused()
    {

    }

    protected abstract void runBackground() throws Exception;

    protected void onException(Exception e)
    {

    }
}
