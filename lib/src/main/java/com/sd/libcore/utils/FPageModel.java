package com.sd.libcore.utils;

import java.util.List;

/**
 * 分页逻辑封装
 */
@Deprecated
public class FPageModel
{
    private int mCurrentPage;
    private int mCurrentCount;
    private boolean mHasNextPage;

    public FPageModel()
    {
        reset();
    }

    /**
     * 是否有下一页数据
     *
     * @return
     */
    public boolean hasNextPage()
    {
        return mHasNextPage;
    }

    /**
     * 返回当前的页数
     *
     * @return
     */
    public int getCurrentPage()
    {
        return mCurrentPage;
    }

    /**
     * 返回当前数据的数量
     *
     * @return
     */
    public int getCurrentCount()
    {
        return mCurrentCount;
    }

    /**
     * 返回当前请求需要传入的page
     *
     * @param isLoadMore 是否加载更多
     * @return
     */
    public int getPageForRequest(boolean isLoadMore)
    {
        if (isLoadMore)
        {
            return mCurrentPage + 1;
        } else
        {
            return 1;
        }
    }

    /**
     * 接口请求成功更新当前分页
     *
     * @param isLoadMore 是否加载更多
     * @param list       本次请求的数量
     * @param totalCount 总数量
     */
    public void updatePageOnSuccess(boolean isLoadMore, List<? extends Object> list, int totalCount)
    {
        final int newCount = list == null ? 0 : list.size();
        updatePageOnSuccess(isLoadMore, newCount, totalCount);
    }

    /**
     * 接口请求成功更新当前分页
     *
     * @param isLoadMore 是否加载更多
     * @param newCount   本次请求的数量
     * @param totalCount 总数量
     */
    public synchronized void updatePageOnSuccess(boolean isLoadMore, int newCount, int totalCount)
    {
        if (newCount < 0)
            throw new IllegalArgumentException("newCount < 0");

        if (totalCount < 0)
            throw new IllegalArgumentException("totalCount < 0");

        if (isLoadMore)
        {
            mCurrentCount += newCount;
        } else
        {
            mCurrentCount = newCount;
        }

        final boolean hasNext = totalCount > mCurrentCount;
        updatePageOnSuccess(isLoadMore, hasNext);
    }

    /**
     * 接口请求成功后，更新当前分页
     *
     * @param isLoadMore  是否加载更多
     * @param hasNextPage 是否有下一页数据
     */
    public synchronized void updatePageOnSuccess(boolean isLoadMore, boolean hasNextPage)
    {
        mHasNextPage = hasNextPage;
        if (isLoadMore)
        {
            mCurrentPage++;
        } else
        {
            mCurrentPage = 1;
        }
    }

    /**
     * 重置
     */
    public synchronized void reset()
    {
        mCurrentPage = 0;
        mCurrentCount = 0;
        mHasNextPage = false;
    }
}
