package com.fanwe.library.model;

/**
 * 分页逻辑封装
 */
public class FPageModel
{
    private int currentPage = 1;
    private boolean hasNextPage = false;

    private int currentCount;

    /**
     * 返回当前数据的数量
     *
     * @return
     */
    public int getCurrentCount()
    {
        return currentCount;
    }

    /**
     * 返回当前的页数
     *
     * @return
     */
    public int getCurrentPage()
    {
        return currentPage;
    }

    /**
     * 是否有下一页数据
     *
     * @return
     */
    public boolean hasNextPage()
    {
        return hasNextPage;
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
            return currentPage + 1;
        } else
        {
            return 1;
        }
    }

    /**
     * 接口请求成功后，更新当前分页
     *
     * @param isLoadMore  是否加载更多
     * @param hasNextPage 是否有下一页数据
     */
    public void updatePageOnSuccess(boolean isLoadMore, boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
        if (isLoadMore)
        {
            if (hasNextPage())
            {
                currentPage++;
            }
        } else
        {
            currentPage = 1;
        }
    }

    /**
     * 接口请求成功更新当前分页
     *
     * @param isLoadMore 是否加载更多
     * @param newCount   本次请求的数量
     * @param totalCount 总数量
     */
    public void updatePageOnSuccess(boolean isLoadMore, int newCount, int totalCount)
    {
        if (isLoadMore)
        {
            currentCount += newCount;
        } else
        {
            currentCount = newCount;
        }

        final boolean hasNext = totalCount > currentCount;
        updatePageOnSuccess(isLoadMore, hasNext);
    }
}
