package com.fanwe.demo.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;


public class RecyclerHelpView extends LinearLayout implements NestedScrollingParent
{
    private static final String TAG = "RecyclerHelpView";

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes)
    {
        Log.e(TAG, "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes)
    {
        Log.e(TAG, "onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(View target)
    {
        Log.e(TAG, "onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    {
        Log.e(TAG, "onNestedPreScroll");
        boolean isInHiddenTop = dy > 0 && getScrollY() < heightTopView;
        boolean isInShowTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (isInHiddenTop || isInShowTop)
        {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed)
    {
        Log.e(TAG, "onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
    {
        Log.e(TAG, "onNestedPreFling");
        if (getScrollY() >= heightTopView)
        {
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes()
    {
        Log.e(TAG, "getNestedScrollAxes");
        return 0;
    }

    private View topView;
    private View contentView;
    private int heightTopView;
    private OverScroller scroller;

    public RecyclerHelpView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setOrientation(LinearLayout.VERTICAL);
        scroller = new OverScroller(getContext());
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        topView = getChildAt(0);
        contentView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = topView.getHeight() + getMeasuredHeight();

        setMeasuredDimension(getMeasuredWidth(), height);

        Log.e(TAG, "onMeasure:" + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        heightTopView = topView.getMeasuredHeight();
    }

    public void fling(int velocityY)
    {
        scroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, heightTopView);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > heightTopView)
        {
            y = heightTopView;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll()
    {
        if (scroller.computeScrollOffset())
        {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

}
