package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.lib.utils.extend.FViewVisibilityListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 可替换view的容器
 */
public class SDReplaceableLayout extends FrameLayout
{
    public SDReplaceableLayout(Context context)
    {
        super(context);
        init();
    }

    public SDReplaceableLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDReplaceableLayout(Context context, AttributeSet attrs, int defStyleAttr)
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
            removeAllViews();
        } else
        {
            mContentView = child;
            if (child.getParent() != this)
            {
                FViewUtil.replaceView(this, child);
            }
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

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);
        if (child == mContentView)
        {
            mContentVisibilityListener.setView(child);
            notifyContentReplaced(child);
            mContentVisibilityListener.notifyVisiblityChanged(); //默认设置View后通知一次可见状态
        }
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);

        if (child == mContentView)
        {
            mContentVisibilityListener.setView(null);
            notifyContentRemoved(child);
            mContentView = null;
        }
    }

    private FViewVisibilityListener mContentVisibilityListener = new FViewVisibilityListener()
    {
        @Override
        protected void onViewVisibilityChanged(View view, int visibility)
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
