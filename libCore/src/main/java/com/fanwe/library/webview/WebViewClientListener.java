package com.fanwe.library.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public abstract class WebViewClientListener
{

	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		return false;
	}

	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
	{
	}

	public void onPageStarted(WebView view, String url, Bitmap favicon)
	{
	}

	public void onPageFinished(WebView view, String url)
	{
	}

	public void onLoadResource(WebView view, String url)
	{
	}

	public WebResourceResponse shouldInterceptRequest(WebView view, String url)
	{
		return null;
	}

	public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg)
	{
	}

	public void onFormResubmission(WebView view, Message dontResend, Message resend)
	{
	}

	public void doUpdateVisitedHistory(WebView view, String url, boolean isReload)
	{
	}

	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
	{
	}

	public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
	{
	}

	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event)
	{
		return false;
	}

	public void onUnhandledKeyEvent(WebView view, KeyEvent event)
	{
	}

	public void onScaleChanged(WebView view, float oldScale, float newScale)
	{
	}

	public void onReceivedLoginRequest(WebView view, String realm, String account, String args)
	{
	}

}
