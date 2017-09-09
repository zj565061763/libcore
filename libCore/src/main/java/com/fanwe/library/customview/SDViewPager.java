package com.fanwe.library.customview;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fanwe.library.model.Recter;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SDViewPager extends ViewPager
{
    public SDViewPager(Context context)
    {
        this(context, null);
    }

    public SDViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private MeasureMode mMeasureMode = MeasureMode.NORMAL;
    private List<Recter> mListIgnoreRect = new ArrayList<>();
    private boolean mIsLocked = false;
    private DataSetObserver mDataSetObserver;

    public void setDataSetObserver(DataSetObserver dataSetObserver)
    {
        mDataSetObserver = dataSetObserver;
    }

    public void setMeasureMode(MeasureMode measureMode)
    {
        if (measureMode != null)
        {
            this.mMeasureMode = measureMode;
            requestLayout();
        }
    }

    private boolean isTouchIgnoreRect(MotionEvent ev)
    {
        for (Recter rect : mListIgnoreRect)
        {
            if (rect.getRect().contains((int) ev.getRawX(), (int) ev.getRawY()))
            {
                return true;
            }
        }
        return false;
    }

    public void addIgnoreRecter(Recter recter)
    {
        if (recter != null)
        {
            if (!mListIgnoreRect.contains(recter))
            {
                mListIgnoreRect.add(recter);
            }
        }
    }

    public void removeIgnoreRecter(Recter recter)
    {
        if (recter != null)
        {
            mListIgnoreRect.remove(recter);
        }
    }

    public void clearIgnoreRect()
    {
        mListIgnoreRect.clear();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (!mIsLocked)
        {
            try
            {
                if (isTouchIgnoreRect(ev))
                {
                    return false;
                } else
                {
                    return super.onInterceptTouchEvent(ev);
                }
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (!mIsLocked)
        {
            try
            {
                if (isTouchIgnoreRect(event))
                {
                    return false;
                } else
                {
                    return super.onTouchEvent(event);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void setLocked(boolean isLocked)
    {
        this.mIsLocked = isLocked;
    }

    public boolean isLocked()
    {
        return mIsLocked;
    }

    @Override
    public void setAdapter(PagerAdapter adapter)
    {
        if (getAdapter() != null)
        {
            getAdapter().unregisterDataSetObserver(mDataSetObserverDefault);
        }
        super.setAdapter(adapter);
        if (adapter != null)
        {
            adapter.registerDataSetObserver(mDataSetObserverDefault);
            mDataSetObserverDefault.onChanged(); //设置Adapter的时候手动触发回调
        }
    }

    private DataSetObserver mDataSetObserverDefault = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            if (mDataSetObserver != null)
            {
                mDataSetObserver.onChanged();
            }
        }

        @Override
        public void onInvalidated()
        {
            super.onInvalidated();
            if (mDataSetObserver != null)
            {
                mDataSetObserver.onInvalidated();
            }
        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        switch (mMeasureMode)
        {
            case NORMAL:
                measureNormal(widthMeasureSpec, heightMeasureSpec);
                break;
            case FIRST_CHILD:
                measureByFirstHeight(widthMeasureSpec, heightMeasureSpec);
                break;
            case MAX_CHILD:
                measureByMaxHeight(widthMeasureSpec, heightMeasureSpec);
                break;
            default:
                measureNormal(widthMeasureSpec, heightMeasureSpec);
                break;
        }
    }

    protected void measureNormal(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void measureByMaxHeight(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++)
        {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
            {
                height = h;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void measureByFirstHeight(int widthMeasureSpec, int heightMeasureSpec)
    {
        View view = getChildAt(0);
        if (view != null)
        {
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
    }

    protected int measureHeight(int measureSpec, View view)
    {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        } else
        {
            if (view != null)
            {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public enum MeasureMode
    {
        /**
         * 正常模式
         */
        NORMAL,
        /**
         * 按最大页面的高度来设置高度
         */
        MAX_CHILD,
        /**
         * 按第一个页面的高度来设置高度
         */
        FIRST_CHILD;
    }

}
