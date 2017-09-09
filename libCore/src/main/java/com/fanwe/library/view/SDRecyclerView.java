package com.fanwe.library.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.fanwe.library.utils.DividerItemDecorationExtend;

/**
 * Created by Administrator on 2016/8/30.
 */
public class SDRecyclerView extends RecyclerView
{
    public SDRecyclerView(Context context)
    {
        super(context);
        init();
    }

    public SDRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        setLinearVertical();
    }

    /**
     * 第一个item是否完全可见
     *
     * @return
     */
    public boolean isFirstItemCompletelyVisible()
    {
        boolean result = false;
        int count = getItemCount();
        if (count > 0)
        {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {
                if (getGridLayoutManager().findFirstCompletelyVisibleItemPosition() == 0)
                {
                    result = true;
                }
            } else if (layoutManager instanceof LinearLayoutManager)
            {
                if (getLinearLayoutManager().findFirstCompletelyVisibleItemPosition() == 0)
                {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 最后一个item是否完全可见
     *
     * @return
     */
    public boolean isLastItemCompletelyVisible()
    {
        boolean result = false;

        int count = getItemCount();
        if (count > 0)
        {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {
                if (getGridLayoutManager().findLastCompletelyVisibleItemPosition() == count - 1)
                {
                    result = true;
                }
            } else if (layoutManager instanceof LinearLayoutManager)
            {
                if (getLinearLayoutManager().findLastCompletelyVisibleItemPosition() == count - 1)
                {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 获得item的数量
     *
     * @return
     */
    public int getItemCount()
    {
        Adapter adapter = getAdapter();
        if (adapter != null)
        {
            return adapter.getItemCount();
        }
        return 0;
    }

    //----------Linear----------

    /**
     * 设置从上往下单列布局
     */
    public void setLinearVertical()
    {
        setLinearOrientation(RecyclerView.VERTICAL);
    }

    /**
     * 设置从左往右单行布局
     */
    public void setLinearHorizontal()
    {
        setLinearOrientation(RecyclerView.HORIZONTAL);
    }

    private void setLinearOrientation(int orientation)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (orientation == RecyclerView.VERTICAL || orientation == RecyclerView.HORIZONTAL)
        {
            layoutManager.setOrientation(orientation);
            setLayoutManager(layoutManager);
        }
    }

    /**
     * 获得线性布局管理器
     *
     * @return
     */
    public LinearLayoutManager getLinearLayoutManager()
    {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager)
        {
            return (LinearLayoutManager) layoutManager;
        }
        return null;
    }

    //----------Grid----------

    /**
     * 设置从上往下多列布局
     *
     * @param spanCount 列数
     */
    public void setGridVertical(int spanCount)
    {
        setGridOrientation(RecyclerView.VERTICAL, spanCount);
    }

    /**
     * 设置从左往右多行布局
     *
     * @param spanCount 行数
     */
    public void setGridHorizontal(int spanCount)
    {
        setGridOrientation(RecyclerView.HORIZONTAL, spanCount);
    }

    private void setGridOrientation(int orientation, int spanCount)
    {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        if (orientation == RecyclerView.VERTICAL || orientation == RecyclerView.HORIZONTAL)
        {
            layoutManager.setOrientation(orientation);
            setLayoutManager(layoutManager);
        }
    }

    /**
     * 获得网格布局管理器
     *
     * @return
     */
    public GridLayoutManager getGridLayoutManager()
    {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            return (GridLayoutManager) layoutManager;
        }
        return null;
    }

    /**
     * 是否处于RecyclerView.SCROLL_STATE_IDLE空闲状态
     *
     * @return
     */
    public boolean isIdle()
    {
        return getScrollState() == RecyclerView.SCROLL_STATE_IDLE;
    }

    //----------scroll----------

    /**
     * 滚动到布局开始的位置
     */
    public void scrollToStart()
    {
        scrollToPosition(0);
    }

    /**
     * 延迟滚动到布局开始的位置
     *
     * @param delay 延迟毫秒
     */
    public void scrollToStartDelayed(long delay)
    {
        if (delay < 0)
        {
            delay = 0;
        }
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                scrollToStart();
            }
        }, delay);
    }

    /**
     * 滚动到布局结束的位置
     */
    public void scrollToEnd()
    {
        int count = getItemCount();
        if (count > 0)
        {
            scrollToPosition(count - 1);
        }
    }

    /**
     * 延迟滚动到布局结束的位置
     *
     * @param delay 延迟毫秒
     */
    public void scrollToEndDelayed(long delay)
    {
        if (delay < 0)
        {
            delay = 0;
        }
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                scrollToEnd();
            }
        }, delay);
    }

    /**
     * 延迟滚动到某个位置
     *
     * @param position 要滚动到的位置
     * @param delay    延迟毫秒
     */
    public void scrollToPositionDelayed(final int position, long delay)
    {
        if (delay < 0)
        {
            delay = 0;
        }
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                scrollToPosition(position);
            }
        }, delay);
    }

    /**
     * 延迟顺滑的滚动到某个位置
     *
     * @param position 要滚动到的位置
     * @param delay    延迟毫秒
     */
    public void smoothScrollToPositionDelayed(final int position, long delay)
    {
        if (delay < 0)
        {
            delay = 0;
        }
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                smoothScrollToPosition(position);
            }
        }, delay);
    }

    //----------divider start----------

    /**
     * 添加横分割线
     *
     * @param drawable
     */
    public void addDividerHorizontal(Drawable drawable)
    {
        addDividerHorizontal(drawable, 0);
    }

    /**
     * 添加横分割线
     *
     * @param drawable
     * @param padding  分割线左右padding
     */
    public void addDividerHorizontal(Drawable drawable, int padding)
    {
        DividerItemDecorationExtend divider = new DividerItemDecorationExtend(DividerItemDecorationExtend.HORIZONTAL);
        divider.setDrawable(drawable);
        divider.setPadding(padding);
        addItemDecoration(divider);
    }

    /**
     * 添加竖分割线
     *
     * @param drawable
     */
    public void addDividerVertical(Drawable drawable)
    {
        addDividerVertical(drawable, 0);
    }

    /**
     * 添加竖分割线
     *
     * @param drawable
     * @param padding  分割线上下padding
     */
    public void addDividerVertical(Drawable drawable, int padding)
    {
        DividerItemDecorationExtend divider = new DividerItemDecorationExtend(DividerItemDecorationExtend.VERTICAL);
        divider.setDrawable(drawable);
        divider.setPadding(padding);
        addItemDecoration(divider);
    }

    //----------divider end----------
}
