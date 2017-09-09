package com.fanwe.library.handler.js;

import android.app.Activity;
import android.content.Intent;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fanwe.library.model.StartAppPageJsonModel;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;

/**
 * Created by Administrator on 2016/9/20.
 */
public class AppJsWHandler extends BaseJsHandler
{
    public AppJsWHandler(String name, Activity activity)
    {
        super(name, activity);
    }

    @JavascriptInterface
    public void logout()
    {
        CookieManager.getInstance().removeSessionCookie();
    }

    @JavascriptInterface
    public void start_app_page(String json)
    {
        try
        {
            StartAppPageJsonModel model = JSON.parseObject(json, StartAppPageJsonModel.class);

            String packename = SDPackageUtil.getPackageName();
            String target = model.getAndroid_page();

            Intent intent = new Intent();
            intent.setClassName(packename, target);
            intent.putExtra("data", json);

            startActivity(intent);
        } catch (Exception e)
        {
            e.printStackTrace();
            SDToast.showToast("数据解析异常");
        }
    }

    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void close_page()
    {
        finish();
    }
}
