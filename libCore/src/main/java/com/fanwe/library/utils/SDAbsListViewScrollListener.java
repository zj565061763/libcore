package com.fanwe.library.utils;

import android.widget.AbsListView;

/**
 * Created by Administrator on 2016/9/9.
 */
public class SDAbsListViewScrollListener<T extends AbsListView>
{

    private T listView;
    private OnScrollListener onScrollListener;

    public SDAbsListViewScrollListener setOnScrollListener(OnScrollListener onScrollListener)
    {
        this.onScrollListener = onScrollListener;
        return this;
    }

    public SDAbsListViewScrollListener listen(T listView)
    {
        if (listView != null)
        {
            this.listView = listView;
            listView.setOnScrollListener(defaultScrollListener);
        }
        return this;
    }

    private AbsListView.OnScrollListener defaultScrollListener = new AbsListView.OnScrollListener()
    {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState)
        {
            listView.getLastVisiblePosition();
            LogUtil.i("onScrollStateChanged:" + scrollState);
            if (onScrollListener != null)
            {
                onScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            LogUtil.i("onScroll:" + firstVisibleItem + "," + visibleItemCount + "," + totalItemCount);
            if (onScrollListener != null)
            {
                onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    };

    public interface OnScrollListener<T extends AbsListView> extends AbsListView.OnScrollListener
    {
        void onScrollToEnd(T view);

        void onScrollToStart(T view);
    }

}
