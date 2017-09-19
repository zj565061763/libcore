package com.fanwe.library.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.fanwe.library.listener.SDSizeChangedCallback;

public class SDWindowSizeListener
{

    private View view;
    private int width;
    private int height;
    private Rect rect = new Rect();

    private SDSizeChangedCallback<View> callback;


    public void listen(Activity activity, SDSizeChangedCallback<View> callback)
    {
        listen(activity.findViewById(android.R.id.content), callback);
    }

    private void listen(View view, SDSizeChangedCallback<View> callback)
    {
        this.view = view;
        this.callback = callback;

        start();
    }

    /**
     * 开始监听
     */
    public void start()
    {
        if (view == null)
        {
            return;
        }
        stop();
        view.getViewTreeObserver().addOnGlobalLayoutListener(defaultLayoutListener);
    }

    /**
     * 停止监听
     */
    public void stop()
    {
        if (view == null)
        {
            return;
        }
        view.getViewTreeObserver().removeGlobalOnLayoutListener(defaultLayoutListener);
        reset();
    }

    private void reset()
    {
        width = 0;
        height = 0;
    }

    private OnGlobalLayoutListener defaultLayoutListener = new OnGlobalLayoutListener()
    {
        @Override
        public void onGlobalLayout()
        {
            process();
        }
    };

    private void process()
    {
        final int oldWidth = width;
        final int oldHeight = height;

        view.getWindowVisibleDisplayFrame(rect);
        final int newWidth = rect.width();
        final int newHeight = rect.height();

        if (newWidth != oldWidth)
        {
            width = newWidth;
            onWidthChanged(newWidth, oldWidth, view);
        }

        if (newHeight != oldHeight)
        {
            height = newHeight;
            onHeightChanged(newHeight, oldHeight, view);
        }
    }

    protected void onWidthChanged(int newWidth, int oldWidth, View target)
    {
        if (callback != null)
        {
            callback.onWidthChanged(newWidth, oldWidth, view);
        }
    }

    protected void onHeightChanged(int newHeight, int oldHeight, View target)
    {
        if (callback != null)
        {
            callback.onHeightChanged(newHeight, oldHeight, view);
        }
    }
}
