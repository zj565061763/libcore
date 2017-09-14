package com.fanwe.library.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/3/23.
 */

public class SDDragRelativeLayout extends RelativeLayout
{
    private ViewDragHelper mDragHelper;

    public SDDragRelativeLayout(Context context)
    {
        super(context);
        init();
    }

    public SDDragRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDDragRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        mDragHelper = ViewDragHelper.create(this, 1.0f, mDragcallback);
    }

    private ViewDragHelper.Callback mDragcallback = new ViewDragHelper.Callback()
    {
        @Override
        public boolean tryCaptureView(View child, int pointerId)
        {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx)
        {
            int leftBound = getPaddingLeft();
            int rightBound = getWidth() - child.getWidth() - getPaddingRight();

            int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy)
        {
            int topBound = getPaddingTop();
            int botBound = getHeight() - child.getHeight() - getPaddingBottom();

            int newTop = Math.min(Math.max(top, topBound), botBound);
            return newTop;
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child)
        {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
