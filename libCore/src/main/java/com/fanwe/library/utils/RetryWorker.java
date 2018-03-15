package com.fanwe.library.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 重试帮助类
 */
public abstract class RetryWorker
{
    /**
     * 重试是否已经开始
     */
    private boolean mIsStarted = false;
    /**
     * 是否重试成功
     */
    private boolean mIsRetrySuccess = false;
    /**
     * 已重试次数
     */
    private int mRetryCount = 0;

    /**
     * 最大重试次数
     */
    private int mMaxRetryCount = 60;
    /**
     * 重试间隔
     */
    private long mRetryInterval = 5 * 1000;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    protected RetryWorker()
    {

    }

    /**
     * 设置最大重试次数
     *
     * @param maxRetryCount
     */
    public final void setMaxRetryCount(int maxRetryCount)
    {
        mMaxRetryCount = maxRetryCount;
    }

    /**
     * 设置重试间隔
     *
     * @param retryInterval
     */
    public final void setRetryInterval(long retryInterval)
    {
        mRetryInterval = retryInterval;
    }

    /**
     * 返回已重试次数
     *
     * @return
     */
    public final int getRetryCount()
    {
        return mRetryCount;
    }

    /**
     * 是否重试成功
     *
     * @return
     */
    public synchronized boolean isRetrySuccess()
    {
        return mIsRetrySuccess;
    }

    /**
     * 开始重试
     */
    public synchronized final void start()
    {
        if (mIsStarted)
        {
            return;
        }
        mIsStarted = true;
        mIsRetrySuccess = false;
        mRetryCount = 0;

        mHandler.removeCallbacks(mRetryRunnable);
        mRetryRunnable.run();
    }

    private Runnable mRetryRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            synchronized (RetryWorker.this)
            {
                if (mIsRetrySuccess)
                {
                    stop();
                    return;
                }

                if (mRetryCount >= mMaxRetryCount && !mIsRetrySuccess)
                {
                    // 达到最大重试次数
                    stop();
                    onRetryFailedOnMaxRetryCount();
                    return;
                }

                if (!canRetry())
                {
                    stop();
                    return;
                }

                onRetry();
                mRetryCount++;
            }
        }
    };

    /**
     * 停止重试
     */
    public synchronized final void stop()
    {
        mIsStarted = false;
        mHandler.removeCallbacks(mRetryRunnable);
    }

    /**
     * 重试一次，调用开始后，此方法才有效
     */
    public synchronized void retryAgain()
    {
        if (mIsStarted)
        {
            mHandler.removeCallbacks(mRetryRunnable);
            mHandler.postDelayed(mRetryRunnable, mRetryInterval);
        }
    }

    /**
     * 设置重试成功
     */
    public synchronized final void setRetrySuccess()
    {
        mIsRetrySuccess = true;
        stop();
    }

    /**
     * 每次重试的时候会触发此方法，验证是否可以执行当次重试任务，默认返回true
     *
     * @return
     */
    protected boolean canRetry()
    {
        return true;
    }

    /**
     * 执行重试任务
     */
    protected abstract void onRetry();

    /**
     * 达到最大重试次数，并且重试失败
     */
    protected abstract void onRetryFailedOnMaxRetryCount();
}
