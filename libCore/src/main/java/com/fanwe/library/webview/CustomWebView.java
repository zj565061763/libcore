package com.fanwe.library.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fanwe.library.common.SDCookieManager;
import com.fanwe.library.handler.js.BaseJsHandler;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDViewUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomWebView extends WebView
{
    public static final int REQUEST_GET_CONTENT = 100;
    private static final String WEBVIEW_CACHE_DIR = "/webviewcache"; // web缓存目录

    private ValueCallback<Uri> mContentValueCallback;
    private List<String> mListActionViewUrl = new ArrayList<>();
    private List<String> mListBrowsableUrl = new ArrayList<String>();
    private File mCacheDir;
    private ProgressBar mProgressBar;

    public CustomWebView(Context context)
    {
        super(context);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public void setProgressBar(ProgressBar progressBar)
    {
        this.mProgressBar = progressBar;
    }

    public void addActionViewUrl(String url)
    {
        if (url == null)
        {
            return;
        }
        if (!mListActionViewUrl.contains(url))
        {
            mListActionViewUrl.add(url);
        }
    }

    private void initActionViewUrl()
    {
        addActionViewUrl("tel:");
        addActionViewUrl("weixin:");
        addActionViewUrl("appay:");
        addActionViewUrl("sinaweibo:");
        addActionViewUrl("alipayqr");
        addActionViewUrl("alipays");
        addActionViewUrl("mqqapi://");
    }

    public void addBrowsableUrl(String url)
    {
        if (url == null)
        {
            return;
        }
        if (!mListBrowsableUrl.contains(url))
        {
            mListBrowsableUrl.add(url);
        }
    }

    private void initBrowsableUrl()
    {
        addBrowsableUrl("intent://platformapi/startapp");
        addBrowsableUrl("intent://dl/business");
    }

    public boolean interceptActionViewUrl(String url)
    {
        boolean result = false;
        if (url != null)
        {
            for (String item : mListActionViewUrl)
            {
                if (url.startsWith(item))
                {
                    startActionView(url);
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean interceptBrowsableUrl(String url)
    {
        boolean result = false;
        if (url != null)
        {
            for (String item : mListBrowsableUrl)
            {
                if (url.startsWith(item))
                {
                    startBrowsable(url);
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    protected void startActionView(String url)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            getContext().startActivity(intent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void startBrowsable(String url)
    {
        try
        {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setComponent(null);
            getContext().startActivity(intent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void init()
    {
        String cacheDirPath = getContext().getCacheDir().getAbsolutePath() + WEBVIEW_CACHE_DIR;
        mCacheDir = new File(cacheDirPath);
        if (!mCacheDir.exists())
        {
            mCacheDir.mkdirs();
        }

        initActionViewUrl();
        initBrowsableUrl();
        initSettings(getSettings());

        setWebViewClient(webViewClient);
        setWebChromeClient(webChromeClient);

        setDownloadListener(new DownloadListener()
        {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                CustomWebView.this.getContext().startActivity(intent);
            }
        });
        requestFocus();
    }

    public void setWebViewClientListener(WebViewClient listener)
    {
        webViewClient.setListener(listener);
    }

    public void setWebChromeClientListener(WebChromeClient listener)
    {
        webChromeClient.setListener(listener);
    }

    /**
     * WebViewClient
     */
    private SDWebViewClientWrapper webViewClient = new SDWebViewClientWrapper()
    {
        @Override
        public void onPageFinished(WebView view, String url)
        {
            SDCookieManager.getInstance().flush();
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (interceptActionViewUrl(url))
            {
                return true;
            }

            if (interceptBrowsableUrl(url))
            {
                return true;
            }

            view.loadUrl(url);

            super.shouldOverrideUrlLoading(view, url);
            return true;
        }
    };


    /**
     * WebChromeClient
     */
    private SDWebChromeClientWrapper webChromeClient = new SDWebChromeClientWrapper()
    {

        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            if (mProgressBar != null)
            {
                if (newProgress == 100)
                {
                    SDViewUtil.setGone(mProgressBar);
                } else
                {
                    SDViewUtil.setVisible(mProgressBar);
                    mProgressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
        {
            Context context = getContext();
            if (context instanceof Activity)
            {
                Activity activity = (Activity) context;
                mContentValueCallback = uploadFile;
                Intent intent = SDIntentUtil.getIntentGetContent();
                activity.startActivityForResult(intent, REQUEST_GET_CONTENT);
            }
            super.openFileChooser(uploadFile, acceptType, capture);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_GET_CONTENT:
                    if (data != null)
                    {
                        Uri value = data.getData();
                        if (value != null)
                        {
                            mContentValueCallback.onReceiveValue(value);
                            mContentValueCallback = null;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    protected void initSettings(WebSettings settings)
    {
        setScaleToShowAll(true);
        setSupportZoom(true);
        setDisplayZoomControls(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true); // 开启DOM storage API 功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setSavePassword(false);

        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(mCacheDir.getAbsolutePath());

        // Database
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(mCacheDir.getAbsolutePath());

        // AppCache
        settings.setAppCacheEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        settings.setAppCachePath(mCacheDir.getAbsolutePath());

        String us = settings.getUserAgentString();
        us = us + " fanwe_app_sdk" + " sdk_type/android" + " sdk_version_name/" + SDPackageUtil.getVersionName() + " sdk_version/" + SDPackageUtil.getVersionCode() + " sdk_guid/"
                + SDPackageUtil.getDeviceId() + " screen_width/" + SDViewUtil.getScreenWidth() + " screen_height/" + SDViewUtil.getScreenHeight();
        settings.setUserAgentString(us);
    }

    public final void setScaleToShowAll(boolean isScaleToShowAll)
    {
        getSettings().setLoadWithOverviewMode(isScaleToShowAll);
        getSettings().setUseWideViewPort(isScaleToShowAll);
    }

    public final void setSupportZoom(boolean isSupportZoom)
    {
        getSettings().setSupportZoom(isSupportZoom);
        getSettings().setBuiltInZoomControls(isSupportZoom);
    }

    public final void setDisplayZoomControls(boolean display)
    {
        getSettings().setDisplayZoomControls(display);
    }

    public void addJavascriptInterface(BaseJsHandler handler)
    {
        if (handler != null)
        {
            addJavascriptInterface(handler, handler.getName());
        }
    }

    public void loadData(String htmlContent)
    {
        if (htmlContent != null)
        {
            loadDataWithBaseURL("about:blank", htmlContent, "text/html", "utf-8", null);
        }
    }

    // get
    public void get(String url)
    {
        get(url, null, null);
    }

    public void get(String url, RequestParams params)
    {
        get(url, params, null);
    }

    public void get(String url, Map<String, String> mapHeader)
    {
        get(url, null, mapHeader);
    }

    public void get(String url, RequestParams params, Map<String, String> mapHeader)
    {
        if (TextUtils.isEmpty(url))
        {
            return;
        }
        if (params != null)
        {
            url = params.build(url);
        }
        if (mapHeader != null && !mapHeader.isEmpty())
        {
            loadUrl(url, mapHeader);
        } else
        {
            loadUrl(url);
        }
    }

    // post
    public void post(String url)
    {
        post(url, null);
    }

    public void post(String url, RequestParams params)
    {
        if (TextUtils.isEmpty(url))
        {
            return;
        }

        byte[] postData = null;
        if (params != null)
        {
            String data = params.build();
            if (!TextUtils.isEmpty(data))
            {
                postData = SDBase64.encodeToByte(data);
            }
        }
        postUrl(url, postData);
    }

    public void loadJsFunction(String function, Object... params)
    {
        loadJsFunction(buildJsFunctionString(function, params));
    }

    public String buildJsFunctionString(String function, Object... params)
    {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(function))
        {
            sb.append(function).append("(");
            if (params != null && params.length > 0)
            {
                for (Object param : params)
                {
                    if (param instanceof String)
                    {
                        sb.append("'").append(String.valueOf(param)).append("'");
                    } else
                    {
                        sb.append(String.valueOf(param));
                    }
                    sb.append(",");
                }
                sb.setLength(sb.length() - 1);
            }
            sb.append(")");
        }
        return sb.toString();
    }

    @SuppressLint("NewApi")
    public void loadJsFunction(String js)
    {
        if (!TextUtils.isEmpty(js))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                evaluateJavascript(js, new ValueCallback<String>()
                {
                    @Override
                    public void onReceiveValue(String arg0)
                    {
                    }
                });
            } else
            {
                loadUrl("javascript:" + js);
            }
        }
    }
}
