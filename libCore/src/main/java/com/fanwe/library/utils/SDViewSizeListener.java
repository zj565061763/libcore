package com.fanwe.library.utils;

import android.view.View;
import android.view.ViewTreeObserver;

import com.fanwe.library.listener.SDSizeChangedCallback;

import java.lang.ref.WeakReference;

/**
 * 可以监听view宽高变化的类
 */
public class SDViewSizeListener
{
    private WeakReference<View> mView;
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

    public View getView()
    {
        if (mView != null)
        {
            return mView.get();
        }
        return null;
    }

    /**
     * 设置回调
     *
     * @param callback
     * @return
     */
    public SDViewSizeListener setCallback(SDSizeChangedCallback<View> callback)
    {
        mCallback = callback;
        return this;
    }

    /**
     * 设置要监听的view
     *
     * @param view
     * @return
     */
    public SDViewSizeListener setView(View view)
    {
        final View oldView = getView();
        if (oldView != view)
        {
            if (oldView != null)
            {
                oldView.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
                reset();
            }

            if (view != null)
            {
                mView = new WeakReference<>(view);

                view.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
            } else
            {
                mView = null;
            }
        }
        return this;
    }

    private void reset()
    {
        mWidth = 0;
        mHeight = 0;
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
    {
        @Override
        public void onGlobalLayout()
        {
            onProcess();
        }
    };

    private void onProcess()
    {
        final View view = getView();
        if (view == null)
        {
            return;
        }

        final int oldWidth = mWidth;
        final int oldHeight = mHeight;

        final int newWidth = onGetWidth(view);
        final int newHeight = onGetHeight(view);

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
            onWidthChanged(newWidth, oldWidth, view);
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
            onHeightChanged(newHeight, oldHeight, view);
        }
    }

    protected int onGetWidth(View view)
    {
        return view.getWidth();
    }

    protected int onGetHeight(View view)
    {
        return view.getHeight();
    }

    protected void onWidthChanged(int newWidth, int oldWidth, View view)
    {
        if (mCallback != null)
        {
            mCallback.onWidthChanged(newWidth, oldWidth, view);
        }
    }

    protected void onHeightChanged(int newHeight, int oldHeight, View view)
    {
        if (mCallback != null)
        {
            mCallback.onHeightChanged(newHeight, oldHeight, view);
        }
    }
}
