package com.fanwe.library.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 可以锁定和解锁view宽高的类
 */
public class SDViewSizeLocker
{
    private View mView;
    /**
     * 锁定前的宽度
     */
    private int mOriginalWidth;
    /**
     * 锁定前的高度
     */
    private int mOriginalHeight;
    /**
     * 锁定前的weight
     */
    private float mOriginalWeight;

    /**
     * 已锁定的宽度
     */
    private int mLockWidth;
    /**
     * 已锁定的高度
     */
    private int mLockHeight;

    /**
     * 宽度是否已经被锁住
     */
    private boolean mHasLockWidth;
    /**
     * 高度是否已经被锁住
     */
    private boolean mHasLockHeight;

    public SDViewSizeLocker(View view)
    {
        this.mView = view;
    }

    /**
     * 设置要处理的view
     *
     * @param view
     */
    public void setView(View view)
    {
        if (this.mView != view)
        {
            reset();
            this.mView = view;
        }
    }

    private void reset()
    {
        mOriginalWidth = 0;
        mOriginalHeight = 0;
        mOriginalWeight = 0;

        mLockWidth = 0;
        mLockHeight = 0;

        mHasLockWidth = false;
        mHasLockHeight = false;
    }

    /**
     * 返回锁定前的宽度
     *
     * @return
     */
    public int getOriginalWidth()
    {
        return mOriginalWidth;
    }

    /**
     * 返回锁定前的高度
     *
     * @return
     */
    public int getOriginalHeight()
    {
        return mOriginalHeight;
    }

    /**
     * 返回锁定前的weight
     *
     * @return
     */
    public float getOriginalWeight()
    {
        return mOriginalWeight;
    }

    /**
     * 返回已锁定的宽度
     *
     * @return
     */
    public int getLockWidth()
    {
        return mLockWidth;
    }

    /**
     * 返回已锁定的高度
     *
     * @return
     */
    public int getLockHeight()
    {
        return mLockHeight;
    }

    /**
     * 宽度是否已经被锁住
     */
    public boolean hasLockWidth()
    {
        return mHasLockWidth;
    }

    /**
     * 高度是否已经被锁住
     *
     * @return
     */
    public boolean hasLockHeight()
    {
        return mHasLockHeight;
    }

    private ViewGroup.LayoutParams getLayoutParams()
    {
        if (getView() == null)
        {
            return null;
        }
        return getView().getLayoutParams();
    }

    private void setLayoutParams(ViewGroup.LayoutParams params)
    {
        if (getView() == null)
        {
            return;
        }
        getView().setLayoutParams(params);
    }

    public View getView()
    {
        return mView;
    }

    /**
     * 锁定宽度
     *
     * @param lockWidth 要锁定的宽度
     */
    public void lockWidth(int lockWidth)
    {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
        {
            return;
        }

        if (mHasLockWidth)
        {
            //如果已经锁了，直接赋值
            params.width = lockWidth;
        } else
        {
            if (params instanceof LinearLayout.LayoutParams)
            {
                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) params;
                mOriginalWeight = lParams.weight;
                lParams.weight = 0;
            }

            mOriginalWidth = params.width;
            params.width = lockWidth;
            mHasLockWidth = true;
        }
        setLayoutParams(params);
    }

    /**
     * 解锁宽度
     */
    public void unlockWidth()
    {
        if (mHasLockWidth)
        {
            ViewGroup.LayoutParams params = getLayoutParams();
            if (params != null)
            {
                if (params instanceof LinearLayout.LayoutParams)
                {
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) params;
                    lParams.weight = mOriginalWeight;
                }
                params.width = mOriginalWidth;
                setLayoutParams(params);
            }
            mHasLockWidth = false;
        }
    }

    /**
     * 锁定高度
     *
     * @param lockHeight 要锁定的高度
     */
    public void lockHeight(int lockHeight)
    {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
        {
            return;
        }

        if (mHasLockHeight)
        {
            //如果已经锁了，直接赋值
            params.height = lockHeight;
        } else
        {
            if (params instanceof LinearLayout.LayoutParams)
            {
                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) params;
                mOriginalWeight = lParams.weight;
                lParams.weight = 0;
            }

            mOriginalHeight = params.height;
            params.height = lockHeight;
            mHasLockHeight = true;
        }
        setLayoutParams(params);

    }

    /**
     * 解锁高度
     */
    public void unlockHeight()
    {
        if (mHasLockHeight)
        {
            ViewGroup.LayoutParams params = getLayoutParams();
            if (params != null)
            {
                if (params instanceof LinearLayout.LayoutParams)
                {
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) params;
                    lParams.weight = mOriginalWeight;
                }
                params.height = mOriginalHeight;
                setLayoutParams(params);
            }
            mHasLockHeight = false;
        }
    }

}
