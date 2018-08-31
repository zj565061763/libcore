package com.sd.library.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sd.library.utils.DividerItemDecorationExtend;

@Deprecated
public class SDRecyclerView extends RecyclerView
{
    public SDRecyclerView(Context context)
    {
        super(context);
        init();
    }

    public SDRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        setLinearLayoutManager(RecyclerView.VERTICAL);
    }

    //----------Linear----------

    /**
     * 设置线性布局管理器
     *
     * @param orientation {@link RecyclerView#VERTICAL} 或者 {@link RecyclerView#HORIZONTAL}
     */
    public void setLinearLayoutManager(int orientation)
    {
        if (orientation == RecyclerView.VERTICAL || orientation == RecyclerView.HORIZONTAL)
        {
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(orientation);
            setLayoutManager(manager);
        } else
        {
            throw new IllegalArgumentException("orientation must be RecyclerView.VERTICAL or RecyclerView.HORIZONTAL");
        }
    }

    /**
     * 返回线性布局管理器
     *
     * @return
     */
    public LinearLayoutManager getLinearLayoutManager()
    {
        final LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager)
        {
            return (LinearLayoutManager) manager;
        }
        return null;
    }

    //----------Grid----------

    /**
     * 设置网格布局管理器
     *
     * @param orientation {@link RecyclerView#VERTICAL} 或者 {@link RecyclerView#HORIZONTAL}
     * @param spanCount   单行或者单列的网格数量
     */
    public void setGridLayoutManager(int orientation, int spanCount)
    {
        if (orientation == RecyclerView.VERTICAL || orientation == RecyclerView.HORIZONTAL)
        {
            final GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);
            manager.setOrientation(orientation);
            setLayoutManager(manager);
        } else
        {
            throw new IllegalArgumentException("orientation must be RecyclerView.VERTICAL or RecyclerView.HORIZONTAL");
        }
    }

    /**
     * 返回网格布局管理器
     *
     * @return
     */
    public GridLayoutManager getGridLayoutManager()
    {
        final LayoutManager manager = getLayoutManager();
        if (manager instanceof GridLayoutManager)
        {
            return (GridLayoutManager) manager;
        }
        return null;
    }

    //----------scroll----------

    /**
     * 获得item的数量
     *
     * @return
     */
    public int getItemCount()
    {
        final Adapter adapter = getAdapter();
        if (adapter != null)
        {
            return adapter.getItemCount();
        }
        return 0;
    }

    /**
     * 滚动到布局开始的位置
     */
    public void scrollToStart()
    {
        scrollToPosition(0);
    }

    /**
     * 滚动到布局结束的位置
     */
    public void scrollToEnd()
    {
        final int count = getItemCount();
        if (count > 0)
        {
            scrollToPosition(count - 1);
        }
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
