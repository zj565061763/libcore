package com.fanwe.library.utils;

import com.fanwe.library.model.SDDelayRunnable;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SDRunnableTryer
{
    private static final int DEFAULT_TRY_COUNT = 3;

    /**
     * 最大重试次数
     */
    private int mMaxTryCount = DEFAULT_TRY_COUNT;
    /**
     * 已重试次数
     */
    private int mTryCount = 0;
    /**
     * 重试Runnable
     */
    private Runnable mTryRunnable;

    /**
     * 设置重试次数
     *
     * @param maxTryCount
     * @return
     */
    public synchronized SDRunnableTryer setMaxTryCount(int maxTryCount)
    {
        this.mMaxTryCount = maxTryCount;
        return this;
    }

    /**
     * 返回最大重试次数，默认3次
     *
     * @return
     */
    public synchronized int getMaxTryCount()
    {
        return mMaxTryCount;
    }

    /**
     * 返回已重试次数
     *
     * @return
     */
    public synchronized int getTryCount()
    {
        return mTryCount;
    }

    /**
     * 执行重试Runnable
     *
     * @param runnable
     * @return true-重试Runnable提交成功，false-超过最大重试次数
     */
    public boolean tryRun(Runnable runnable)
    {
        return tryRunDelayed(runnable, 0);
    }

    /**
     * 执行重试Runnable
     *
     * @param delay 延迟多久执行
     * @return true-重试Runnable提交成功，false-超过最大重试次数
     */
    public synchronized boolean tryRunDelayed(Runnable runnable, long delay)
    {
        if (runnable == null)
        {
            return false;
        }
        mTryRunnable = runnable;
        mTryCount++;
        if (mTryCount > mMaxTryCount)
        {
            //超过最大重试次数，不处理
            return false;
        } else
        {
            mDelayRunnable.runDelayOrImmediately(delay);
            return true;
        }
    }

    private SDDelayRunnable mDelayRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            synchronized (SDRunnableTryer.this)
            {
                if (mTryRunnable != null)
                {
                    mTryRunnable.run();
                }
            }
        }
    };

    /**
     * 重置重试次数
     */
    public synchronized void resetTryCount()
    {
        mTryCount = 0;
    }

    /**
     * 销毁
     */
    public void onDestroy()
    {
        mDelayRunnable.removeDelay();
        mTryRunnable = null;
    }
}
