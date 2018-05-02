package com.fanwe.library.model;

/**
 * 分页逻辑封装
 */
public class FPageModel
{
    private int page = 1;
    private boolean hasNextPage = false;

    /**
     * 返回当前的页数
     *
     * @return
     */
    public int currentPage()
    {
        return page;
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
            return page + 1;
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
                page++;
            }
        } else
        {
            page = 1;
        }
    }
}
