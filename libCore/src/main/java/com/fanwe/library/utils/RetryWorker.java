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
     * 延迟多少毫秒后重试，调用开始后，此方法才有效
     */
    public synchronized void retryDelayed(long delayMillis)
    {
        if (mIsStarted)
        {
            mHandler.removeCallbacks(mRetryRunnable);
            mHandler.postDelayed(mRetryRunnable, delayMillis);
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
     * 执行重试任务
     */
    protected abstract void onRetry();

    /**
     * 达到最大重试次数，并且重试失败
     */
    protected abstract void onRetryFailedOnMaxRetryCount();
}
