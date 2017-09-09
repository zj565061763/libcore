package com.fanwe.library.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.webview.CustomWebView;

import java.util.HashMap;
import java.util.Map;

/**
 * WebView fragment
 *
 * @author js02
 */
public class WebViewFragment extends SDBaseFragment implements SDTitleSimple.SDTitleSimpleListener
{

    public static final int REQUEST_GET_CONTENT = 100;

    protected ProgressBar mPgbHorizontal;
    protected CustomWebView mWeb;

    protected EnumProgressMode mProgressMode = EnumProgressMode.NONE;
    protected EnumWebviewHeightMode mWebviewHeightMode = EnumWebviewHeightMode.WRAP_CONTENT;

    protected String mStrUrl;
    protected String mStrHtmlContent;
    protected String mStrReferer;
    protected String mStrTitle;

    protected boolean isShowTitle = false;
    protected boolean isScaleToShowAll = false;
    protected boolean isSupportZoom = true;
    protected WebViewFragmentListener mListener;
    protected SDTitleSimple mTitle;
    protected TextView mTvClose;
    protected ValueCallback<Uri> mUploadMsg;

    public void setmListener(WebViewFragmentListener mListener)
    {
        this.mListener = mListener;
    }

    public void setmProgressMode(EnumProgressMode mProgressMode)
    {
        this.mProgressMode = mProgressMode;
        changeProgressMode();
    }

    public void setmWebviewHeightMode(EnumWebviewHeightMode mWebviewHeightMode)
    {
        this.mWebviewHeightMode = mWebviewHeightMode;
        changeWebViewHeightMode();
    }

    public CustomWebView getWebView()
    {
        return this.mWeb;
    }

    public void setShowTitle(boolean isShowTitle)
    {
        this.isShowTitle = isShowTitle;
        changeTitleVisibility();
    }

    public void setSupportZoom(boolean isSupportZoom)
    {
        this.isSupportZoom = isSupportZoom;
    }

    public void setScaleToShowAll(boolean isScaleToNormal)
    {
        this.isScaleToShowAll = isScaleToNormal;
    }

    public void setUrl(String url)
    {
        this.mStrUrl = url;
    }

    public void setTitle(String title)
    {
        this.mStrTitle = title;
        if (mTitle != null)
        {
            mTitle.setMiddleTextTop(title);
        }
    }

    public void setHtmlContent(String htmlContent)
    {
        this.mStrHtmlContent = htmlContent;
    }

    public void setReferer(String referer)
    {
        this.mStrReferer = referer;
    }

    @Override
    protected int onCreateContentView()
    {
        int resId = getContentViewResId();
        if (resId == 0)
        {
            resId = R.layout.frag_webview;
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
        findViews();
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected View onCreateTitleView()
    {
        mTitle = new SDTitleSimple(getActivity());
        mTitle.setmListener(this);
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        return mTitle;
    }

    private void findViews()
    {
        mWeb = findWebView();
        mPgbHorizontal = findProgressBar();
    }

    protected CustomWebView findWebView()
    {
        return (CustomWebView) findViewById(R.id.wv_webview);
    }

    protected ProgressBar findProgressBar()
    {
        return (ProgressBar) findViewById(R.id.pgb_horizontal);
    }

    protected void init()
    {
        initTitle(mStrTitle);
        changeWebViewHeightMode();
        changeProgressMode();
        initWebView();
        initFinish();
        startLoadData();
    }

    private void initTitle(String title)
    {

        mTitle.setMiddleTextTop(title);

        // addCloseButton();

        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("关闭");

        changeTitleVisibility();
    }

    protected void changeWebViewHeightMode()
    {
        if (mWeb != null && mWebviewHeightMode != null)
        {
            switch (mWebviewHeightMode)
            {
                case WRAP_CONTENT:
                    SDViewUtil.setHeight(mWeb, ViewGroup.LayoutParams.WRAP_CONTENT);
                    break;
                case MATCH_PARENT:
                    SDViewUtil.setHeight(mWeb, ViewGroup.LayoutParams.MATCH_PARENT);
                    break;

                default:
                    break;
            }
        }
    }

    protected void changeProgressMode()
    {
        if (mProgressMode != null && mPgbHorizontal != null)
        {
            switch (mProgressMode)
            {
                case HORIZONTAL:
                    SDViewUtil.setVisible(mPgbHorizontal);
                    break;
                case NONE:
                    SDViewUtil.setGone(mPgbHorizontal);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 初始化webview
     */
    private void initWebView()
    {
        initSetting();
        addJavascriptInterface();

        mWeb.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                WebViewFragment.this.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });

        mWeb.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                WebViewFragment.this.onProgressChanged(view, newProgress);
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
            {
                WebViewFragment.this.openFileChooser(uploadFile, acceptType, capture);
            }
        });

        if (mListener != null)
        {
            mListener.onInitFinish(mWeb);
        }
    }

    protected void initFinish()
    {

    }

    protected void initSetting()
    {
        mWeb.setSupportZoom(isSupportZoom);
        mWeb.setScaleToShowAll(isScaleToShowAll);
    }

    protected void addJavascriptInterface()
    {

    }

    public void startLoadData()
    {
        if (mStrHtmlContent != null)
        {
            mWeb.loadData(mStrHtmlContent);
            return;
        }

        if (mStrUrl != null)
        {
            if (!TextUtils.isEmpty(mStrReferer))
            {
                Map<String, String> mapHeader = new HashMap<String, String>();
                mapHeader.put("Referer", mStrReferer);
                mWeb.get(mStrUrl, mapHeader);
            } else
            {
                mWeb.get(mStrUrl);
            }
            return;
        }
    }

    // WebViewClient 方法回调
    protected boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if (url.startsWith("weixin://"))
        {
            try
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e)
            {
            }
            return true;
        }

        if (mListener != null)
        {
            return mListener.onLoadUrl(view, url);
        }
        return false;
    }

    // WebChromeClient 方法回调
    protected void onProgressChanged(WebView view, int newProgress)
    {
        if (mProgressMode != null)
        {
            switch (mProgressMode)
            {
                case HORIZONTAL:
                    if (newProgress == 100)
                    {
                        SDViewUtil.setGone(mPgbHorizontal);
                    } else
                    {
                        SDViewUtil.setVisible(mPgbHorizontal);
                        mPgbHorizontal.setProgress(newProgress);
                    }
                    break;
                case NONE:

                    break;

                default:
                    break;
            }
        }
    }

    protected void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
    {
        mUploadMsg = uploadFile;
        Intent intent = SDIntentUtil.getIntentGetContent();
        startActivityForResult(intent, REQUEST_GET_CONTENT);
    }

    @Override
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
                            mUploadMsg.onReceiveValue(value);
                            mUploadMsg = null;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean goBack()
    {
        boolean goback = false;
        if (mWeb != null && mWeb.canGoBack())
        {
            mWeb.goBack();
            goback = true;
        }
        return goback;
    }

    @Override
    public void onPause()
    {
        mWeb.onPause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        mWeb.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        if (mWeb != null)
        {
            mWeb.destroy();
            mWeb = null;
        }
        super.onDestroy();
    }

    private void changeTitleVisibility()
    {
        if (mTitle != null)
        {
            if (isShowTitle)
            {
                SDViewUtil.setVisible(mTitle);
            } else
            {
                SDViewUtil.setGone(mTitle);
            }
        }
    }

    protected void addCloseButton()
    {
        if (mTvClose != null)
        {
            mTitle.mLlLeft.removeView(mTvClose);
        }
        LayoutParams paramsClose = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        // paramsClose.leftMargin = SDViewUtil.dp2px(15);

        mTvClose = new TextView(getActivity());
        mTvClose.setText("关闭");
        SDViewUtil.setTextSizeSp(mTvClose, 17);
        mTvClose.setGravity(Gravity.CENTER);
        mTvClose.setTextColor(Color.parseColor("#ffffff"));
        mTvClose.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                getActivity().finish();
            }
        });
        mTitle.mLlLeft.addView(mTvClose, paramsClose);
    }

    @Override
    public void onCLickLeft_SDTitleSimple(SDTitleItem v)
    {
        if (goBack())
        {
        } else
        {
            getActivity().finish();
        }
    }

    @Override
    public void onCLickMiddle_SDTitleSimple(SDTitleItem v)
    {

    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        getActivity().finish();
    }

    public static class WebViewFragmentListener
    {
        public void onInitFinish(WebView webView)
        {
        }

        public boolean onLoadUrl(WebView webView, String url)
        {
            return false;
        }
    }

    public enum EnumProgressMode
    {
        HORIZONTAL, NONE
    }

    public enum EnumWebviewHeightMode
    {
        WRAP_CONTENT, MATCH_PARENT
    }
}