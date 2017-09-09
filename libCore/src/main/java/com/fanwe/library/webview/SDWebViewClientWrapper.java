package com.fanwe.library.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SDWebViewClientWrapper extends WebViewClient
{
    private WebViewClient listener;

    public void setListener(WebViewClient listener)
    {
        this.listener = listener;
    }

    public WebViewClient getListener()
    {
        return listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if (listener != null)
        {
            return listener.shouldOverrideUrlLoading(view, url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon)
    {
        if (listener != null)
        {
            listener.onPageStarted(view, url, favicon);
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        if (listener != null)
        {
            listener.onPageFinished(view, url);
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url)
    {
        if (listener != null)
        {
            listener.onLoadResource(view, url);
        }
        super.onLoadResource(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url)
    {
        if (listener != null)
        {
            return listener.shouldInterceptRequest(view, url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request)
    {
        if (listener != null)
        {
            return listener.shouldInterceptRequest(view, request);
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg)
    {
        if (listener != null)
        {
            listener.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
    {
        if (listener != null)
        {
            listener.onReceivedError(view, errorCode, description, failingUrl);
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend)
    {
        if (listener != null)
        {
            listener.onFormResubmission(view, dontResend, resend);
        }
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload)
    {
        if (listener != null)
        {
            listener.doUpdateVisitedHistory(view, url, isReload);
        }
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
    {
        if (listener != null)
        {
            listener.onReceivedSslError(view, handler, error);
        }
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request)
    {
        if (listener != null)
        {
            listener.onReceivedClientCertRequest(view, request);
        }
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
    {
        if (listener != null)
        {
            listener.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event)
    {
        if (listener != null)
        {
            return listener.shouldOverrideKeyEvent(view, event);
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event)
    {
        if (listener != null)
        {
            listener.onUnhandledKeyEvent(view, event);
        }
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale)
    {
        if (listener != null)
        {
            listener.onScaleChanged(view, oldScale, newScale);
        }
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args)
    {
        if (listener != null)
        {
            listener.onReceivedLoginRequest(view, realm, account, args);
        }
        super.onReceivedLoginRequest(view, realm, account, args);
    }
}
