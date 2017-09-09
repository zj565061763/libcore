package com.fanwe.library.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class SDGestureHandler
{
    private ViewConfiguration mViewConfiguration;
    private VelocityTracker mVelocityTracker;
    private GestureDetector mGestureDetector;
    private SDScroller mScroller;
    private boolean mIsScrollVertical = false;
    private boolean mIsScrollHorizontal = false;
    private SDGestureListener mGestureListener;

    public SDGestureHandler(Context context)
    {
        mViewConfiguration = ViewConfiguration.get(context);
        mScroller = new SDScroller(context);
        mGestureDetector = new GestureDetector(context, mDefaultGestureListener);
    }

    private GestureDetector.SimpleOnGestureListener mDefaultGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        @Override
        public boolean onDown(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                if (mGestureListener.onDown(e))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                if (mGestureListener.onSingleTapUp(e))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                if (mGestureListener.onSingleTapConfirmed(e))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                if (mGestureListener.onDoubleTap(e))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                if (mGestureListener.onDoubleTapEvent(e))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (mGestureListener != null)
            {
                if (mGestureListener.onFling(e1, e2, velocityX, velocityY))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            if (mGestureListener != null)
            {
                getVelocityTracker().addMovement(e2);
                getVelocityTracker().computeCurrentVelocity(1000);

                if (mGestureListener.onScroll(e1, e2, distanceX, distanceY))
                {
                    setScrollVertical(false);
                    setScrollHorizontal(false);
                    return true;
                }

                if (mIsScrollVertical)
                {
                    if (mGestureListener.onScrollVertical(e1, e2, distanceX, distanceY))
                    {
                        return true;
                    }
                } else if (mIsScrollHorizontal)
                {
                    if (mGestureListener.onScrollHorizontal(e1, e2, distanceX, distanceY))
                    {
                        return true;
                    }
                } else
                {
                    float dx = Math.abs(e1.getRawX() - e2.getRawX());
                    float dy = Math.abs(e1.getRawY() - e2.getRawY());
                    int touchSlop = getScaledTouchSlop();

                    if (dx > dy)
                    {
                        // 水平方向
                        if (dx > touchSlop)
                        {
                            setScrollHorizontal(true);
                        }
                    } else
                    {
                        // 竖直方向
                        if (dy > touchSlop)
                        {
                            setScrollVertical(true);
                        }
                    }
                }
            } else
            {
                cancel();
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                mGestureListener.onLongPress(e);
            }
        }

        @Override
        public void onShowPress(MotionEvent e)
        {
            if (mGestureListener != null)
            {
                mGestureListener.onShowPress(e);
            }
        }
    };

    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if (mGestureListener != null)
            {
                float velocityX = getVelocityTracker().getXVelocity();
                float velocityY = getVelocityTracker().getYVelocity();
                mGestureListener.onActionUp(event, velocityX, velocityY);
            }
            cancel();
        }

        return mGestureDetector.onTouchEvent(event);
    }

    public SDScroller getScroller()
    {
        return mScroller;
    }

    private void setScrollHorizontal(boolean isScrollHorizontal)
    {
        this.mIsScrollHorizontal = isScrollHorizontal;
        if (isScrollHorizontal)
        {
            mIsScrollVertical = false;
        }
    }

    private void setScrollVertical(boolean isScrollVertical)
    {
        this.mIsScrollVertical = isScrollVertical;
        if (isScrollVertical)
        {
            mIsScrollHorizontal = false;
        }
    }

    public void cancel()
    {
        mIsScrollVertical = false;
        mIsScrollHorizontal = false;
        recyleVelocityTracker();
    }

    public boolean isScrollHorizontal()
    {
        return mIsScrollHorizontal;
    }

    public boolean isScrollVertical()
    {
        return mIsScrollVertical;
    }

    public static int getDurationPercent(float distance, float maxDistance, long maxDuration)
    {
        int result = 0;

        float percent = Math.abs(distance) / Math.abs(maxDistance);
        float duration = percent * (float) maxDuration;

        result = (int) duration;
        return result;
    }

    public int getScaledTouchSlop()
    {
        return mViewConfiguration.getScaledTouchSlop();
    }

    public int getScaledMinimumFlingVelocityCommon()
    {
        return mViewConfiguration.getScaledMinimumFlingVelocity() * 21;
    }

    public VelocityTracker getVelocityTracker()
    {
        if (mVelocityTracker == null)
        {
            mVelocityTracker = VelocityTracker.obtain();
        }
        return mVelocityTracker;
    }

    public void recyleVelocityTracker()
    {
        if (mVelocityTracker != null)
        {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public ViewConfiguration getViewConfiguration()
    {
        return mViewConfiguration;
    }

    public void setGestureListener(SDGestureListener gestureListener)
    {
        this.mGestureListener = gestureListener;
    }

    public static class SDGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        public boolean onScrollVertical(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            return false;
        }

        public boolean onScrollHorizontal(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            return false;
        }

        public void onActionUp(MotionEvent event, float velocityX, float velocityY)
        {

        }
    }

}
