package com.fanwe.library.utils;

import android.os.Handler;
import android.os.Looper;

import com.fanwe.lib.receiver.FNetworkReceiver;
import com.fanwe.lib.utils.context.FContext;

public abstract class RequestRetryer
{
    /**
     * 是否正在重试中
     */
    private boolean mIsInRetry = false;
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

    protected RequestRetryer()
    {
        mNetworkReceiver.register(FContext.get());
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
     * 开始重试
     */
    public final void start()
    {
        if (mIsInRetry)
        {
            return;
        }
        mIsInRetry = true;
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
            if (mIsRetrySuccess)
            {
                stop();
                return;
            }

            if (mRetryCount >= mMaxRetryCount && !mIsRetrySuccess)
            {
                // 达到最大重试次数，并且没有成功
                stop();
                onRetryFailedOnMaxRetryCount();
                return;
            }

            if (!FNetworkReceiver.isNetworkConnected(FContext.get()))
            {
                // 无网络
                LogUtil.i("pause retry net disconnected");
                stop();
                return;
            }

            onRetry();
            mRetryCount++;
            LogUtil.i("retry count:" + mRetryCount);
        }
    };

    /**
     * 结束重试
     */
    public final void stop()
    {
        mIsInRetry = false;
        mHandler.removeCallbacks(mRetryRunnable);
        LogUtil.i("stop retry");
    }

    /**
     * 设置重试结果
     *
     * @param success
     */
    public final void setRetryResult(boolean success)
    {
        mIsRetrySuccess = success;
        if (!success)
        {
            mHandler.removeCallbacks(mRetryRunnable);
            mHandler.postDelayed(mRetryRunnable, mRetryInterval);
        }
    }

    private FNetworkReceiver mNetworkReceiver = new FNetworkReceiver()
    {
        @Override
        protected void onNetworkChanged(int type)
        {
            if (type >= 0)
            {
                if (!mIsRetrySuccess)
                {
                    LogUtil.i("resume retry net connected");
                    start();
                }
            }
        }
    };

    protected abstract void onRetry();

    protected abstract void onRetryFailedOnMaxRetryCount();
}
