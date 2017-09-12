package com.fanwe.library.utils;

/**
 * Created by zhengjun on 2017/9/9.
 */
public class SDDurationTrigger
{
    /**
     * 两次触发有效的时间间隔
     */
    private long mDuration = 2000;
    /**
     * 最大触发次数
     */
    private int mMaxTriggerCount = 2;
    private int mCurrentTriggerCount;

    private long mLastTriggerTime;

    /**
     * 设置两次触发有效的时间间隔
     *
     * @param duration
     * @return
     */
    public synchronized SDDurationTrigger setDuration(long duration)
    {
        mDuration = duration;
        return this;
    }

    /**
     * 设置最大触发次数
     *
     * @param maxTriggerCount
     * @return
     */
    public synchronized SDDurationTrigger setMaxTriggerCount(int maxTriggerCount)
    {
        mMaxTriggerCount = maxTriggerCount;
        return this;
    }

    /**
     * 返回两次触发有效的时间间隔
     *
     * @return
     */
    public long getDuration()
    {
        return mDuration;
    }

    /**
     * 返回当前触发次数
     *
     * @return
     */
    public int getCurrentTriggerCount()
    {
        return mCurrentTriggerCount;
    }

    /**
     * 返回限制的最大触发次数
     *
     * @return
     */
    public int getMaxTriggerCount()
    {
        return mMaxTriggerCount;
    }

    /**
     * 返回还要多少次有效触发才达到最大触发次数
     *
     * @return
     */
    public int getLeftTriggerCount()
    {
        int count = mMaxTriggerCount - mCurrentTriggerCount;
        if (count < 0)
        {
            count = 0;
        }
        return count;
    }

    /**
     * 重置触发次数
     */
    public synchronized void resetTriggerCount()
    {
        mCurrentTriggerCount = 0;
    }

    /**
     * 触发逻辑
     *
     * @return true-成功触发，false-还未超过最大触发次数
     */
    public synchronized boolean trigger()
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTriggerTime > mDuration)
        {
            mCurrentTriggerCount = 1;
        } else
        {
            mCurrentTriggerCount++;
        }
        mLastTriggerTime = currentTime;
        return mCurrentTriggerCount >= mMaxTriggerCount;
    }
}
