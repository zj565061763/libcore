package com.fanwe.library.model;

/**
 * 分页逻辑封装
 */
public class FPageModel
{
    private int page = 1;
    private int hasNextPage = 0;

    /**
     * 是否有下一页数据
     *
     * @return
     */
    public boolean hasNextPage()
    {
        return hasNextPage == 1;
    }

    /**
     * 返回当前请求需要传入的page
     *
     * @param isLoadMore
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
     * 更新当前分页
     *
     * @param isLoadMore
     * @param hasNextPage
     */
    public void updatePageOnSuccess(boolean isLoadMore, int hasNextPage)
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
