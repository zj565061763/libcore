package com.fanwe.library.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SDWebChromeClientWrapper extends WebChromeClient
{
    private WebChromeClient listener;

    public void setListener(WebChromeClient listener)
    {
        this.listener = listener;
    }

    public WebChromeClient getListener()
    {
        return listener;
    }

    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
    {
        //notify listener
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress)
    {
        if (listener != null)
        {
            listener.onProgressChanged(view, newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title)
    {
        if (listener != null)
        {
            listener.onReceivedTitle(view, title);
        }
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon)
    {
        if (listener != null)
        {
            listener.onReceivedIcon(view, icon);
        }
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed)
    {
        if (listener != null)
        {
            listener.onReceivedTouchIconUrl(view, url, precomposed);
        }
        super.onReceivedTouchIconUrl(view, url, precomposed);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback)
    {
        if (listener != null)
        {
            listener.onShowCustomView(view, callback);
        }
        super.onShowCustomView(view, callback);
    }

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback)
    {
        if (listener != null)
        {
            listener.onShowCustomView(view, requestedOrientation, callback);
        }
        super.onShowCustomView(view, requestedOrientation, callback);
    }

    @Override
    public void onHideCustomView()
    {
        if (listener != null)
        {
            listener.onHideCustomView();
        }
        super.onHideCustomView();
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
    {
        if (listener != null)
        {
            return listener.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view)
    {
        if (listener != null)
        {
            listener.onRequestFocus(view);
        }
        super.onRequestFocus(view);
    }

    @Override
    public void onCloseWindow(WebView window)
    {
        if (listener != null)
        {
            listener.onCloseWindow(window);
        }
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result)
    {
        if (listener != null)
        {
            return listener.onJsAlert(view, url, message, result);
        }
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result)
    {
        if (listener != null)
        {
            return listener.onJsConfirm(view, url, message, result);
        }
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result)
    {
        if (listener != null)
        {
            return listener.onJsPrompt(view, url, message, defaultValue, result);
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result)
    {
        if (listener != null)
        {
            return listener.onJsBeforeUnload(view, url, message, result);
        }
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater)
    {
        if (listener != null)
        {
            listener.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        }
        super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
    }

    @Override
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater)
    {
        if (listener != null)
        {
            listener.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        }
        super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback)
    {
        if (listener != null)
        {
            listener.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt()
    {
        if (listener != null)
        {
            listener.onGeolocationPermissionsHidePrompt();
        }
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onPermissionRequest(PermissionRequest request)
    {
        if (listener != null)
        {
            listener.onPermissionRequest(request);
        }
        super.onPermissionRequest(request);
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request)
    {
        if (listener != null)
        {
            listener.onPermissionRequestCanceled(request);
        }
        super.onPermissionRequestCanceled(request);
    }

    @Override
    public boolean onJsTimeout()
    {
        if (listener != null)
        {
            return listener.onJsTimeout();
        }
        return super.onJsTimeout();
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID)
    {
        if (listener != null)
        {
            listener.onConsoleMessage(message, lineNumber, sourceID);
        }
        super.onConsoleMessage(message, lineNumber, sourceID);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage)
    {
        if (listener != null)
        {
            return listener.onConsoleMessage(consoleMessage);
        }
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public Bitmap getDefaultVideoPoster()
    {
        if (listener != null)
        {
            return listener.getDefaultVideoPoster();
        }
        return super.getDefaultVideoPoster();
    }

    @Override
    public View getVideoLoadingProgressView()
    {
        if (listener != null)
        {
            return listener.getVideoLoadingProgressView();
        }
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback)
    {
        if (listener != null)
        {
            listener.getVisitedHistory(callback);
        }
        super.getVisitedHistory(callback);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams)
    {
        if (listener != null)
        {
            return listener.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}
