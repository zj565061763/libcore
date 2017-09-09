package com.fanwe.demo.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.library.gesture.SDGestureHandler;
import com.fanwe.library.utils.SDViewUtil;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SDPageView extends FrameLayout
{
    public SDPageView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public SDPageView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDPageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private SDGestureHandler mGestureHandler;

    private View mLeftView;
    private View mRightView;
    private View mHorizontalView;
    private boolean mEnableHorizontalScroll;

    private void init()
    {
        mGestureHandler = new SDGestureHandler(getContext());
        mGestureHandler.setGestureListener(new SDGestureHandler.SDGestureListener()
        {
            @Override
            public boolean onScrollHorizontal(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                if (canScrollHorizontal(distanceX))
                {

                }
                return super.onScrollHorizontal(e1, e2, distanceX, distanceY);
            }

            @Override
            public void onActionUp(MotionEvent event, float velocityX, float velocityY)
            {
                super.onActionUp(event, velocityX, velocityY);
            }
        });
    }

    public void setLeftView(View leftView)
    {
        mLeftView = leftView;
    }

    public void setRightView(View rightView)
    {
        mRightView = rightView;
    }

    public void setHorizontalView(View horizontalView)
    {
        this.mHorizontalView = horizontalView;
    }

    public void resetHorizontalView()
    {
        if (mHorizontalView == null)
        {
            return;
        }
        mHorizontalView.scrollTo(0, 0);

        if (mLeftView != null)
        {
            mLeftView.scrollTo(getHorizontalWidth(), 0);
        }
        if (mRightView != null)
        {
            mRightView.scrollTo(-getHorizontalWidth(), 0);
        }
    }

    private void initViews()
    {

    }

    private int getHorizontalWidth()
    {
        if (mHorizontalView != null)
        {
            return SDViewUtil.getWidth(mHorizontalView);
        } else
        {
            return 0;
        }
    }

    /**
     * 是否可以横向滚动
     *
     * @param distanceX
     * @return
     */
    private boolean canScrollHorizontal(float distanceX)
    {
        if (!mEnableHorizontalScroll)
        {
            return false;
        }
        if (mHorizontalView == null)
        {
            return false;
        }
        if (mLeftView == null && mRightView == null)
        {
            return false;
        }

        int rightValue = 0;
        if (mRightView == null)
        {
            rightValue = 0;
        } else
        {
            rightValue = getHorizontalWidth();
        }

        int leftValue = 0;
        if (mLeftView == null)
        {
            leftValue = 0;
        } else
        {
            leftValue = -getHorizontalWidth();
        }

        int scrollX = mHorizontalView.getScrollX();
        if (scrollX >= rightValue)
        {
            //在最右边页面
            if (distanceX > 0)
            {
                //左滑动
                return false;
            }
        } else if (scrollX <= leftValue)
        {
            //在最左边页面
            if (distanceX < 0)
            {
                //右滑动
                return false;
            }
        }
        return true;
    }

}
