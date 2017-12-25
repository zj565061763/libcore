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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private FViewVisibilityHandler mVisibilityhandler = new FViewVisibilityHandler(null);
    private List<Callback> mListCallback = new ArrayList<>();

    private void init()
    {
        mVisibilityhandler.addVisibilityCallback(mDefaultVisibilityCallback);
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
        FViewUtil.replaceView(this, child);
        mContentView = child;
        mVisibilityhandler.setView(child);
        notifyContentReplaced(child);
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
        mVisibilityhandler.setVisible(false);
    }

    /**
     * 隐藏内容view(View.INVISIBLE)
     */
    public void setContentInvisible()
    {
        mVisibilityhandler.setInvisible(false);
    }

    /**
     * 隐藏内容view(View.GONE)
     */
    public void setContentGone()
    {
        mVisibilityhandler.setGone(false);
    }

    /**
     * 切换内容view显示或者隐藏
     */
    public void toggleContentVisibleOrGone()
    {
        mVisibilityhandler.toggleVisibleOrGone(false);
    }

    /**
     * 切换内容view显示或者隐藏
     */
    public void toggleContentVisibleOrInvisible()
    {
        mVisibilityhandler.toggleVisibleOrInvisible(false);
    }

    /**
     * 是否有内容view
     *
     * @return
     */
    public boolean hasContent()
    {
        return mContentView != null;
    }

    /**
     * 内容是否可见
     *
     * @return
     */
    public boolean isContentVisible()
    {
        return mVisibilityhandler.isVisible();
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (child == mContentView)
        {
            mVisibilityhandler.setView(null);
            mContentView = null;
            notifyContentRemoved(child);
        }
    }

    /**
     * 可见状态变化回调
     */
    private FViewVisibilityHandler.VisibilityCallback mDefaultVisibilityCallback = new FViewVisibilityHandler.VisibilityCallback()
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
        Iterator<Callback> it = mListCallback.iterator();
        while (it.hasNext())
        {
            Callback callback = it.next();
            callback.onContentReplaced(view);
        }
    }

    private void notifyContentRemoved(final View view)
    {
        Iterator<Callback> it = mListCallback.iterator();
        while (it.hasNext())
        {
            Callback callback = it.next();
            callback.onContentRemoved(view);
        }
    }

    private void notifyContentVisibilityChanged(final View view, final int visibility)
    {
        Iterator<Callback> it = mListCallback.iterator();
        while (it.hasNext())
        {
            Callback callback = it.next();
            callback.onContentVisibilityChanged(view, visibility);
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
