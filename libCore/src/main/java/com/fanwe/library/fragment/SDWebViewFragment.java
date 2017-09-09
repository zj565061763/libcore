package com.fanwe.library.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.webview.CustomWebView;

import java.util.HashMap;
import java.util.Map;

/**
 * WebView fragment
 *
 * @author js02
 */
public class SDWebViewFragment extends SDBaseFragment
{
    protected CustomWebView webView;
    protected ProgressBar progressBar;

    protected int webViewHeight;
    protected boolean progressBarEnable;

    protected String url;
    protected String htmlContent;
    protected String referer;

    private InitListener initListener;

    public void setInitListener(InitListener initListener)
    {
        this.initListener = initListener;
    }

    public void setProgressBarEnable(boolean progressBarEnable)
    {
        this.progressBarEnable = progressBarEnable;
        if (progressBar != null)
        {
            if (progressBarEnable)
            {
                SDViewUtil.setVisible(progressBar);
            } else
            {
                SDViewUtil.setGone(progressBar);
            }
        }
    }

    public void setWebViewHeight(int webViewHeight)
    {
        this.webViewHeight = webViewHeight;
        if (webView != null)
        {
            SDViewUtil.setHeight(webView, webViewHeight);
        }
    }

    public CustomWebView getWebView()
    {
        return webView;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setHtmlContent(String htmlContent)
    {
        this.htmlContent = htmlContent;
    }

    public void setReferer(String referer)
    {
        this.referer = referer;
    }

    @Override
    protected int onCreateContentView()
    {
        int resId = getContentViewResId();
        if (resId == 0)
        {
            resId = R.layout.frag_sdwebview;
        }
        return resId;
    }

    /**
     * 可以被重写返回fragment布局ID，如果重写此方法，需要重写findWebView()和findProgressBar()
     *
     * @return
     */
    protected int getContentViewResId()
    {
        return 0;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        findViews(view);
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    private void findViews(View view)
    {
        webView = findWebView(view);
        progressBar = findProgressBar(view);
    }

    protected CustomWebView findWebView(View view)
    {
        return (CustomWebView) view.findViewById(R.id.wv_webview);
    }

    protected ProgressBar findProgressBar(View view)
    {
        return (ProgressBar) view.findViewById(R.id.pgb_horizontal);
    }

    protected void init()
    {
        setProgressBarEnable(false);
        setWebViewHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        initWebView();
        startLoadData();
    }

    /**
     * 初始化webview
     */
    private void initWebView()
    {
        initSetting(webView);
        addJavascriptInterface(webView);


        initFinish(webView);

        //最后通知监听，让监听有机会改变默认设置
        if (initListener != null)
        {
            initListener.onInitFinish(webView);
        }
    }

    protected void initFinish(CustomWebView webView)
    {

    }

    protected void initSetting(CustomWebView webView)
    {
        webView.setSupportZoom(true);
        webView.setScaleToShowAll(false);
    }

    protected void addJavascriptInterface(CustomWebView webView)
    {

    }

    public void startLoadData()
    {
        if (htmlContent != null)
        {
            webView.loadData(htmlContent);
            return;
        }

        if (url != null)
        {
            if (!TextUtils.isEmpty(referer))
            {
                Map<String, String> mapHeader = new HashMap<String, String>();
                mapHeader.put("Referer", referer);
                webView.get(url, mapHeader);
            } else
            {
                webView.get(url);
            }
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        webView.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean goBack()
    {
        boolean goback = false;
        if (webView != null && webView.canGoBack())
        {
            webView.goBack();
            goback = true;
        }
        return goback;
    }

    @Override
    public void onPause()
    {
        webView.onPause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        webView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        if (webView != null)
        {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    public interface InitListener
    {
        void onInitFinish(CustomWebView webView);
    }
}