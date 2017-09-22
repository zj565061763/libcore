package com.fanwe.library.common;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class SDCookieManager
{
    private static final String KEY_GROUP = ";";
    private static final String KEY_PAIR = "=";

    private static SDCookieManager instance;

    public static SDCookieManager getInstance()
    {
        if (instance == null)
        {
            instance = new SDCookieManager();
        }
        return instance;
    }

    public void init(Context context)
    {
        CookieSyncManager.createInstance(context);
    }

    public String getCookie(String url)
    {
        return CookieManager.getInstance().getCookie(url);
    }

    public List<HttpCookie> getHttpCookie(String url)
    {
        String cookie = getCookie(url);
        if (TextUtils.isEmpty(cookie))
        {
            return null;
        }
        if (!cookie.contains(KEY_PAIR))
        {
            return null;
        }
        String[] arrCookie = cookie.split(KEY_GROUP);
        if (arrCookie == null || arrCookie.length <= 0)
        {
            return null;
        }
        List<HttpCookie> listCookie = new ArrayList<>();
        for (String item : arrCookie)
        {
            if (item.contains(KEY_PAIR))
            {
                String[] arrPair = item.split(KEY_PAIR);
                if (arrPair != null && arrPair.length == 2)
                {
                    HttpCookie httpCookie = new HttpCookie(arrPair[0], arrPair[1]);
                    listCookie.add(httpCookie);
                }
            }
        }
        return listCookie;
    }

    public void setCookie(String url, String value)
    {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(value))
        {
            return;
        }
        CookieManager.getInstance().setCookie(url, value);
    }

    public void setCookie(String url, HttpCookie cookie)
    {
        if (cookie == null)
        {
            return;
        }
        setCookie(url, cookie.getName() + "=" + cookie.getValue());
    }

    public void setCookie(String url, List<HttpCookie> listCookie)
    {
        if (listCookie == null || listCookie.isEmpty())
        {
            return;
        }
        for (HttpCookie item : listCookie)
        {
            setCookie(url, item);
        }
    }

    public void removeSessionCookie()
    {
        CookieManager.getInstance().removeSessionCookie();
    }

    public void removeAllCookie()
    {
        CookieManager.getInstance().removeAllCookie();
    }

    /**
     * 将webview cookie持久化到本地
     */
    public void flush()
    {
        CookieSyncManager.getInstance().sync();
    }

}
