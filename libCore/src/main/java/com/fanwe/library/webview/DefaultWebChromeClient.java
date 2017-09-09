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
import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;

public class DefaultWebChromeClient extends WebChromeClient
{

    private WebChromeClientListener listener;

    public void setListener(WebChromeClientListener listener)
    {
        this.listener = listener;
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadFile)
    {
        if (listener != null)
        {
            listener.openFileChooser(uploadFile, "", "");
        } else
        {
            openFileChooser(uploadFile, "", "");
        }
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType)
    {
        if (listener != null)
        {
            listener.openFileChooser(uploadFile, acceptType, "");
        } else
        {
            openFileChooser(uploadFile, acceptType, "");
        }
    }

    // For Android > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
    {
        if (listener != null)
        {
            listener.openFileChooser(uploadFile, acceptType, capture);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress)
    {
        if (listener != null)
        {
            listener.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title)
    {
        if (listener != null)
        {
            listener.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon)
    {
        if (listener != null)
        {
            listener.onReceivedIcon(view, icon);
        }
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed)
    {
        if (listener != null)
        {
            listener.onReceivedTouchIconUrl(view, url, precomposed);
        }
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback)
    {
        if (listener != null)
        {
            listener.onShowCustomView(view, callback);
        }
    }

    @Override
    @Deprecated
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback)
    {
        if (listener != null)
        {
            listener.onShowCustomView(view, requestedOrientation, callback);
        }
    }

    @Override
    public void onHideCustomView()
    {
        if (listener != null)
        {
            listener.onHideCustomView();
        }
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
    {
        if (listener != null)
        {
            return listener.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        } else
        {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
    }

    @Override
    public void onRequestFocus(WebView view)
    {
        if (listener != null)
        {
            listener.onRequestFocus(view);
        }
    }

    @Override
    public void onCloseWindow(WebView window)
    {
        if (listener != null)
        {
            listener.onCloseWindow(window);
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result)
    {
        if (listener != null)
        {
            return listener.onJsAlert(view, url, message, result);
        } else
        {
            return super.onJsAlert(view, url, message, result);
        }
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result)
    {
        if (listener != null)
        {
            return listener.onJsConfirm(view, url, message, result);
        } else
        {
            return super.onJsConfirm(view, url, message, result);
        }
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result)
    {
        if (listener != null)
        {
            return listener.onJsPrompt(view, url, message, defaultValue, result);
        } else
        {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result)
    {
        if (listener != null)
        {
            return listener.onJsBeforeUnload(view, url, message, result);
        } else
        {
            return super.onJsBeforeUnload(view, url, message, result);
        }
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, Callback callback)
    {
        if (listener != null)
        {
            listener.onGeolocationPermissionsShowPrompt(origin, callback);
        } else
        {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    @Override
    public void onGeolocationPermissionsHidePrompt()
    {
        if (listener != null)
        {
            listener.onGeolocationPermissionsHidePrompt();
        }
    }

    @Override
    public boolean onJsTimeout()
    {
        if (listener != null)
        {
            return listener.onJsTimeout();
        } else
        {
            return super.onJsTimeout();
        }
    }

    @Override
    @Deprecated
    public void onConsoleMessage(String message, int lineNumber, String sourceID)
    {
        if (listener != null)
        {
            listener.onConsoleMessage(message, lineNumber, sourceID);
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage)
    {
        if (listener != null)
        {
            return listener.onConsoleMessage(consoleMessage);
        } else
        {
            return super.onConsoleMessage(consoleMessage);
        }
    }

    @Override
    public Bitmap getDefaultVideoPoster()
    {
        if (listener != null)
        {
            return listener.getDefaultVideoPoster();
        } else
        {
            return super.getDefaultVideoPoster();
        }
    }

    @Override
    public View getVideoLoadingProgressView()
    {
        if (listener != null)
        {
            return listener.getVideoLoadingProgressView();
        } else
        {
            return super.getVideoLoadingProgressView();
        }
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback)
    {
        if (listener != null)
        {
            listener.getVisitedHistory(callback);
        }
    }

    @Override
    @Deprecated
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater)
    {
        if (listener != null)
        {
            listener.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        }
    }

    @Override
    @Deprecated
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, QuotaUpdater quotaUpdater)
    {
        if (listener != null)
        {
            listener.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        } else
        {
            quotaUpdater.updateQuota(requiredStorage * 2);
        }
    }

}