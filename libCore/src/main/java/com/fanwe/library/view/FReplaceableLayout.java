package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 可替换view的容器
 */
public class FReplaceableLayout extends FrameLayout
{
    public FReplaceableLayout(Context context)
    {
        super(context);
        init();
    }

    public FReplaceableLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FReplaceableLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private View mContentView;
    private final List<Callback> mListCallback = new CopyOnWriteArrayList<>();

    private void init()
    {
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void addCallback(Callback callback)
    {
        if (callback == null || mListCallback.contains(callback))
            return;

        mListCallback.add(callback);
    }

    /**
     * 移除回调
     *
     * @param callback
     */
    public void removeCallback(Callback callback)
    {
        mListCallback.remove(callback);
    }

    /**
     * 把当前容器的内容替换为child
     *
     * @param child
     */
    public final void setContentView(View child)
    {
        final View old = mContentView;
        if (old != child)
        {
            if (old != null)
                removeView(old);

            mContentView = child;

            if (child != null && child.getParent() != this)
                addView(child);
        }
    }

    /**
     * 获得内容view
     *
     * @return
     */
    public final View getContentView()
    {
        return mContentView;
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);

        if (child == mContentView)
        {
            notifyContentReplaced(child);
        } else
        {
            throw new RuntimeException("Illegal child:" + child);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (child == mContentView)
        {
            mContentView = null;
            notifyContentRemoved(child);
        } else
        {
            throw new RuntimeException("Illegal child:" + child);
        }
    }

    //----------notify start----------

    private void notifyContentReplaced(View view)
    {
        for (Callback item : mListCallback)
        {
            item.onContentReplaced(view);
        }
    }

    private void notifyContentRemoved(final View view)
    {
        for (Callback item : mListCallback)
        {
            item.onContentRemoved(view);
        }
    }

    //----------notify end----------

    public interface Callback
    {
        /**
         * 内容view被替换到容器
         *
         * @param view
         */
        void onContentReplaced(View view);

        /**
         * 内容view被移除
         *
         * @param view
         */
        void onContentRemoved(View view);
    }
}
