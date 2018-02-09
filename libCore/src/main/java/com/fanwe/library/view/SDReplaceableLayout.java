package com.fanwe.library.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.lib.utils.extend.FViewVisibilityHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 可替换view的容器
 */
public class SDReplaceableLayout extends FrameLayout
{
    public SDReplaceableLayout(@NonNull Context context)
    {
        super(context);
        init();
    }

    public SDReplaceableLayout(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDReplaceableLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private View mContentView;
    private List<Callback> mListCallback = new CopyOnWriteArrayList<>();

    private void init()
    {
    }

    private FViewVisibilityHandler getContentVisibilityhandler()
    {
        return FViewVisibilityHandler.get(mContentView);
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void addCallback(Callback callback)
    {
        if (callback == null || mListCallback.contains(callback))
        {
            return;
        }
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
     * 清空回调
     */
    public void clearCallback()
    {
        mListCallback.clear();
    }

    /**
     * 把当前容器的内容替换为child
     *
     * @param child
     */
    public void replaceContent(View child)
    {
        if (child == null)
        {
            return;
        }
        if (child.getParent() == this)
        {
            return;
        }

        mContentView = child;
        FViewUtil.replaceView(this, child);
    }

    /**
     * 移除内容view
     */
    public void removeContent()
    {
        if (mContentView != null)
        {
            removeView(mContentView);
        }
    }

    /**
     * 获得内容view
     *
     * @return
     */
    public View getContent()
    {
        return mContentView;
    }

    /**
     * 显示内容view(View.VISIBLE)
     */
    public void setContentVisible()
    {
        if (getContent() == null)
        {
            return;
        }
        getContentVisibilityhandler().setVisible(false);
    }

    /**
     * 隐藏内容view(View.INVISIBLE)
     */
    public void setContentInvisible()
    {
        if (getContent() == null)
        {
            return;
        }
        getContentVisibilityhandler().setInvisible(false);
    }

    /**
     * 隐藏内容view(View.GONE)
     */
    public void setContentGone()
    {
        if (getContent() == null)
        {
            return;
        }
        getContentVisibilityhandler().setGone(false);
    }

    /**
     * 切换内容view显示或者隐藏
     */
    public void toggleContentVisibleOrGone()
    {
        if (getContent() == null)
        {
            return;
        }
        getContentVisibilityhandler().toggleVisibleOrGone(false);
    }

    /**
     * 切换内容view显示或者隐藏
     */
    public void toggleContentVisibleOrInvisible()
    {
        if (getContent() == null)
        {
            return;
        }
        getContentVisibilityhandler().toggleVisibleOrInvisible(false);
    }

    /**
     * 内容是否可见
     *
     * @return
     */
    public boolean isContentVisible()
    {
        if (getContent() == null)
        {
            return false;
        }
        return getContentVisibilityhandler().isVisible();
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);
        if (child == mContentView)
        {
            getContentVisibilityhandler().addVisibilityChangeCallback(mContentVisibilityChangeCallback);
            notifyContentReplaced(child);
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (child == mContentView)
        {
            getContentVisibilityhandler().removeVisibilityChangeCallback(mContentVisibilityChangeCallback);
            notifyContentRemoved(child);
            mContentView = null;
        }
    }

    /**
     * 可见状态变化回调
     */
    private FViewVisibilityHandler.VisibilityChangeCallback mContentVisibilityChangeCallback = new FViewVisibilityHandler.VisibilityChangeCallback()
    {
        @Override
        public void onViewVisibilityChanged(View view, int visibility)
        {
            notifyContentVisibilityChanged(view, visibility);
        }
    };

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

    private void notifyContentVisibilityChanged(final View view, final int visibility)
    {
        for (Callback item : mListCallback)
        {
            item.onContentVisibilityChanged(view, visibility);
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

        /**
         * 内容view可见状态发生变化
         *
         * @param view
         * @param visibility
         */
        void onContentVisibilityChanged(View view, int visibility);
    }
}
