package com.fanwe.library.utils;

import android.text.TextUtils;

/**
 * url链接拼装工具
 */
public class UrlLinkBuilder
{
    private String url;
    private StringBuilder sb;

    public UrlLinkBuilder(String url)
    {
        this.url = url;
        reset();
    }

    /**
     * 添加参数
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public UrlLinkBuilder add(String key, String value)
    {
        if (!TextUtils.isEmpty(key) && value != null)
        {
            beforeAdd();
            sb.append(key).append("=").append(value);
        }
        return this;
    }

    /**
     * 返回拼接的url链接
     *
     * @return
     */
    public String build()
    {
        return sb.toString();
    }

    /**
     * 重置builder，会清空已经拼接的参数
     */
    public void reset()
    {
        sb = new StringBuilder(url);
    }

    private void beforeAdd()
    {
        String currentUrl = sb.toString();
        if (currentUrl.contains("?"))
        {
            if (currentUrl.endsWith("?"))
            {

            } else if (currentUrl.endsWith("&"))
            {

            } else
            {
                sb.append("&");
            }
        } else
        {
            sb.append("?");
        }
    }

}
