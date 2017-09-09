package com.fanwe.library.utils;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 基于DividerItemDecoration修改
 */
public class DividerItemDecorationExtend extends RecyclerView.ItemDecoration
{
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private Drawable mDivider;
    /**
     * 分割线的方向
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();

    /**
     * 分割线padding
     */
    private int mPadding;

    /**
     * @param orientation 分割线的方向
     */
    public DividerItemDecorationExtend(int orientation)
    {
        mOrientation = orientation;
    }

    /**
     * 设置分割线drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable)
    {
        mDivider = drawable;
    }

    /**
     * 设置分割线padding
     *
     * @param padding
     */
    public void setPadding(int padding)
    {
        mPadding = padding;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        if (parent.getLayoutManager() == null)
        {
            return;
        }
        if (mOrientation == VERTICAL)
        {
            drawRight(c, parent);
        } else if (mOrientation == HORIZONTAL)
        {
            drawBottom(c, parent);
        }
    }

    /**
     * item底部画分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawBottom(Canvas canvas, RecyclerView parent)
    {
        canvas.save();
        final int left = 0;
        final int right = parent.getWidth();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            if (!isLastRow(parent, parent.getChildAdapterPosition(child)))
            {
                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
                final int top = bottom - mDivider.getIntrinsicHeight();
                mDivider.setBounds(left + mPadding, top, right - mPadding, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    /**
     * item右边画分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawRight(Canvas canvas, RecyclerView parent)
    {
        canvas.save();
        final int top = 0;
        final int bottom = parent.getHeight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            if (!isLastColum(parent, parent.getChildAdapterPosition(child)))
            {
                parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
                final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
                final int left = right - mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top + mPadding, right, bottom - mPadding);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        int position = parent.getChildAdapterPosition(view);
        if (mOrientation == VERTICAL)
        {
            if (isLastColum(parent, position))
            {
                outRect.set(0, 0, 0, 0);
            } else
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        } else if (mOrientation == HORIZONTAL)
        {
            if (isLastRow(parent, position))
            {
                outRect.set(0, 0, 0, 0);
            } else
            {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }
    }

    /**
     * position是否是最后一列
     *
     * @param parent
     * @param position
     * @return
     */
    private boolean isLastColum(RecyclerView parent, int position)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int spanCount = manager.getSpanCount();
            int orientation = manager.getOrientation();

            if (orientation == VERTICAL)
            {
                if ((position + 1) % spanCount == 0)
                {
                    return true;
                }
            } else
            {
                int remainder = childCount % spanCount;
                if (remainder == 0)
                {
                    return position >= childCount - spanCount;
                } else
                {
                    return position >= childCount - remainder;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager)
        {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int orientation = manager.getOrientation();
            if (orientation == VERTICAL)
            {
                return true;
            } else
            {
                return position == childCount - 1;
            }
        }
        return false;
    }

    /**
     * position是否是最后一行
     *
     * @param parent
     * @param position
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int position)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int spanCount = manager.getSpanCount();
            int orientation = manager.getOrientation();

            if (orientation == VERTICAL)
            {
                int remainder = childCount % spanCount;
                if (remainder == 0)
                {
                    return position >= childCount - spanCount;
                } else
                {
                    return position >= childCount - remainder;
                }
            } else
            {
                if ((position + 1) % spanCount == 0)
                {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager)
        {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int orientation = manager.getOrientation();
            if (orientation == VERTICAL)
            {
                return position == childCount - 1;
            } else
            {
                return true;
            }
        }
        return false;
    }
}
