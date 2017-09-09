package com.fanwe.library.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDToast;

public class DefaultWebViewClient extends WebViewClient
{

    private WebViewClientListener listener;

    public void setListener(WebViewClientListener listener)
    {
        this.listener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if (listener != null)
        {
            return listener.shouldOverrideUrlLoading(view, url);
        } else
        {
            if (!SDNetworkReceiver.isNetworkConnected(view.getContext()))
            {
                SDToast.showToast("亲!您的网络状况不太好!");
                return true;
            } else
            {
                if (url.startsWith("tel:"))
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }
                if (url.startsWith("weixin://wap/pay?"))
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }
                if (url.startsWith("appay://appayservice/?"))
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }
                if (url.contains("taobao://"))
                {
                    super.shouldOverrideUrlLoading(view, url);
                    return false;
                }
                if (url.contains("tmall://"))
                {
                    super.shouldOverrideUrlLoading(view, url);
                    return false;
                }
                view.loadUrl(url);
                return true;
            }
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
    {
        if (listener != null)
        {
            listener.onReceivedError(view, errorCode, description, failingUrl);
        } else
        {
            view.loadUrl(failingUrl);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon)
    {
        if (listener != null)
        {
            listener.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        if (listener != null)
        {
            listener.onPageFinished(view, url);
        }
    }

    @Override
    public void onLoadResource(WebView view, String url)
    {
        if (listener != null)
        {
            listener.onLoadResource(view, url);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url)
    {
        if (listener != null)
        {
            return listener.shouldInterceptRequest(view, url);
        } else
        {
            return super.shouldInterceptRequest(view, url);
        }
    }

    @Override
    @Deprecated
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg)
    {
        if (listener != null)
        {
            listener.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
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
        } else
        {
            return super.shouldOverrideKeyEvent(view, event);
        }
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
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args)
    {
        if (listener != null)
        {
            listener.onReceivedLoginRequest(view, realm, account, args);
        }
    }

}
