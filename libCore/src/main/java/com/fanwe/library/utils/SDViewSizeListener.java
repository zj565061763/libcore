package com.fanwe.library.utils;

import android.view.View;
import android.view.ViewTreeObserver;

import com.fanwe.library.listener.SDSizeChangedCallback;

/**
 * 可以监听view宽高变化的类
 */
public class SDViewSizeListener
{
    private View mView;
    private int mWidth;
    private int mHeight;

    private int mFirstWidth;
    private int mFirstHeight;

    private SDSizeChangedCallback<View> mCallback;

    /**
     * 获得view第一次监听到的宽度
     *
     * @return
     */
    public int getFirstWidth()
    {
        return mFirstWidth;
    }

    /**
     * 获得view第一次监听到的高度
     *
     * @return
     */
    public int getFirstHeight()
    {
        return mFirstHeight;
    }

    private void reset()
    {
        mWidth = 0;
        mHeight = 0;
    }

    /**
     * 设置要监听的view
     *
     * @param view
     * @param callback
     * @return
     */
    public SDViewSizeListener listen(View view, SDSizeChangedCallback<View> callback)
    {
        mCallback = callback;
        if (mView != view)
        {
            releaseView();
            mView = view;
            initView();
        }
        return this;
    }

    /**
     * 释放view
     */
    private void releaseView()
    {
        if (mView != null)
        {
            mView.getViewTreeObserver().removeGlobalOnLayoutListener(defaultListener);
            mView = null;
        }
        reset();
    }

    /**
     * 初始化view
     */
    private void initView()
    {
        if (mView != null)
        {
            mView.getViewTreeObserver().addOnGlobalLayoutListener(defaultListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener defaultListener = new ViewTreeObserver.OnGlobalLayoutListener()
    {
        @Override
        public void onGlobalLayout()
        {
            process();
        }
    };

    private void process()
    {
        if (mView == null)
        {
            return;
        }

        int oldWidth = mWidth;
        int oldHeight = mHeight;

        int newWidth = mView.getWidth();
        int newHeight = mView.getHeight();

        int differWidth = newWidth - oldWidth;
        int differHeight = newHeight - oldHeight;

        if (newWidth != oldWidth)
        {
            mWidth = newWidth;

            //保存第一次测量到的宽度
            if (mFirstWidth <= 0)
            {
                if (newWidth > 0)
                {
                    mFirstWidth = newWidth;
                }
            }

            if (mCallback != null)
            {
                mCallback.onWidthChanged(newWidth, oldWidth, differWidth, mView);
            }
        }

        if (newHeight != oldHeight)
        {
            mHeight = newHeight;

            //保存第一次测量到的高度
            if (mFirstHeight <= 0)
            {
                if (newHeight > 0)
                {
                    mFirstHeight = newHeight;
                }
            }

            if (mCallback != null)
            {
                mCallback.onHeightChanged(newHeight, oldHeight, differHeight, mView);
            }
        }
    }

}
