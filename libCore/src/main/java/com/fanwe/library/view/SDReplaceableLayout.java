package com.fanwe.library.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.library.holder.ISDObjectsHolder;
import com.fanwe.library.holder.SDObjectsHolder;
import com.fanwe.library.listener.SDIterateCallback;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.SDViewVisibilityHandler;

import java.util.Iterator;

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
    private SDViewVisibilityHandler mVisibilityhandler = new SDViewVisibilityHandler(null);
    private ISDObjectsHolder<SDReplaceableLayoutCallback> mCallbackHolder = new SDObjectsHolder<>();

    private void init()
    {
        mVisibilityhandler.addVisibilityCallback(mDefaultVisibilityCallback);
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void addCallback(SDReplaceableLayoutCallback callback)
    {
        mCallbackHolder.add(callback);
    }

    /**
     * 移除回调
     *
     * @param callback
     */
    public void removeCallback(SDReplaceableLayoutCallback callback)
    {
        mCallbackHolder.remove(callback);
    }

    /**
     * 清空回调
     */
    public void clearCallback()
    {
        mCallbackHolder.clear();
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
        SDViewUtil.replaceView(this, child);
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
    private SDViewVisibilityCallback mDefaultVisibilityCallback = new SDViewVisibilityCallback()
    {
        @Override
        public void onViewVisibilityChanged(View view, int visibility)
        {
            notifyContentVisibilityChanged(view, visibility);
        }
    };

    //----------notify start----------

    private void notifyContentReplaced(final View view)
    {
        mCallbackHolder.foreach(new SDIterateCallback<SDReplaceableLayoutCallback>()
        {
            @Override
            public boolean next(int i, SDReplaceableLayoutCallback item, Iterator<SDReplaceableLayoutCallback> it)
            {
                item.onContentReplaced(view);
                return false;
            }
        });
    }

    private void notifyContentRemoved(final View view)
    {
        mCallbackHolder.foreach(new SDIterateCallback<SDReplaceableLayoutCallback>()
        {
            @Override
            public boolean next(int i, SDReplaceableLayoutCallback item, Iterator<SDReplaceableLayoutCallback> it)
            {
                item.onContentRemoved(view);
                return false;
            }
        });
    }

    private void notifyContentVisibilityChanged(final View view, final int visibility)
    {
        mCallbackHolder.foreach(new SDIterateCallback<SDReplaceableLayoutCallback>()
        {
            @Override
            public boolean next(int i, SDReplaceableLayoutCallback item, Iterator<SDReplaceableLayoutCallback> it)
            {
                item.onContentVisibilityChanged(view, visibility);
                return false;
            }
        });
    }

    //----------notify end----------

    public interface SDReplaceableLayoutCallback
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
