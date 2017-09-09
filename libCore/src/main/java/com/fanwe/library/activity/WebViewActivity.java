package com.fanwe.library.activity;

import android.os.Bundle;

import com.fanwe.library.R;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.fragment.WebViewFragment.EnumProgressMode;

/**
 * webview界面
 *
 * @author js02
 */
public class WebViewActivity extends SDBaseActivity
{
    /**
     * webview 要加载的链接
     */
    public static final String EXTRA_URL = "extra_url";
    /**
     * webview 界面标题
     */
    public static final String EXTRA_TITLE = "extra_title";
    /**
     * 要显示的HTML内容
     */
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";
    /**
     * header中的referer
     */
    public static final String EXTRA_REFERER = "extra_referer";

    protected WebViewFragment mFragWebview;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_webview);
        init();
    }

    protected void init()
    {
        mFragWebview = createFragment();
        mFragWebview.setShowTitle(true);
        getIntentData();
        addFragments();
    }

    protected WebViewFragment createFragment()
    {
        WebViewFragment frag = new WebViewFragment();
        frag.setmProgressMode(EnumProgressMode.HORIZONTAL);
        return frag;
    }

    protected void addFragments()
    {
        if (mFragWebview != null)
        {
            getSDFragmentManager().replace(R.id.fl_content, mFragWebview);
        }
    }

    protected void getIntentData()
    {
        String strUrl = getIntent().getStringExtra(EXTRA_URL);
        String strTitle = getIntent().getStringExtra(EXTRA_TITLE);
        String strHtmlContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
        String strReferer = getIntent().getStringExtra(EXTRA_REFERER);

        // 初始化fragment
        mFragWebview.setHtmlContent(strHtmlContent);
        mFragWebview.setUrl(strUrl);
        mFragWebview.setReferer(strReferer);
        mFragWebview.setTitle(strTitle);
    }

}