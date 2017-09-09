package com.fanwe.library.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;

public abstract class WebChromeClientListener
{

	public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
	{

	}

	public void onProgressChanged(WebView view, int newProgress)
	{
	}

	public void onReceivedTitle(WebView view, String title)
	{
	}

	public void onReceivedIcon(WebView view, Bitmap icon)
	{
	}

	public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed)
	{
	}

	public void onShowCustomView(View view, CustomViewCallback callback)
	{
	}

	public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback)
	{
	}

	public void onHideCustomView()
	{
	}

	public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
	{
		return false;
	}

	public void onRequestFocus(WebView view)
	{
	}

	public void onCloseWindow(WebView window)
	{
	}

	public boolean onJsAlert(WebView view, String url, String message, JsResult result)
	{
		return false;
	}

	public boolean onJsConfirm(WebView view, String url, String message, JsResult result)
	{
		return false;
	}

	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result)
	{
		return false;
	}

	public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result)
	{
		return false;
	}

	public void onGeolocationPermissionsShowPrompt(String origin, Callback callback)
	{
	}

	public void onGeolocationPermissionsHidePrompt()
	{
	}

	public boolean onJsTimeout()
	{
		return true;
	}

	public void onConsoleMessage(String message, int lineNumber, String sourceID)
	{
	}

	public boolean onConsoleMessage(ConsoleMessage consoleMessage)
	{
		return false;
	}

	public Bitmap getDefaultVideoPoster()
	{
		return null;
	}

	public View getVideoLoadingProgressView()
	{
		return null;
	}

	public void getVisitedHistory(ValueCallback<String[]> callback)
	{
	}

	public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota,
			QuotaUpdater quotaUpdater)
	{
	}

	public void onReachedMaxAppCacheSize(long requiredStorage, long quota, QuotaUpdater quotaUpdater)
	{
	}

}