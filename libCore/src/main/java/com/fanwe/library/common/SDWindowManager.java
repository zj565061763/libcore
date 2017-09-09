package com.fanwe.library.common;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fanwe.library.SDLibrary;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class SDWindowManager
{
    private static SDWindowManager sInstance;

    private ArrayList<WeakReference<View>> mListView = new ArrayList<>();

    private SDWindowManager()
    {
    }

    public static SDWindowManager getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new SDWindowManager();
        }
        return sInstance;
    }

    public WindowManager getWindowManager()
    {
        WindowManager windowManager = (WindowManager) SDLibrary.getInstance().getContext().getSystemService(Context.WINDOW_SERVICE);
        return windowManager;
    }

    public void addView(View view, ViewGroup.LayoutParams params)
    {
        if (view != null && params != null)
        {
            getWindowManager().addView(view, params);

            WeakReference<View> weakView = new WeakReference<View>(view);
            mListView.add(weakView);
        }
    }

    public void updateViewLayout(View view, ViewGroup.LayoutParams params)
    {
        if (view != null && params != null)
        {
            getWindowManager().updateViewLayout(view, params);
        }
    }

    public void removeView(View view)
    {
        if (view != null)
        {
            getWindowManager().removeView(view);

            removeWeakReference(view);
        }
    }

    public void removeViewImmediate(View view)
    {
        if (view != null)
        {
            getWindowManager().removeViewImmediate(view);

            removeWeakReference(view);
        }
    }

    //

    public WindowManager.LayoutParams newLayoutParams()
    {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return params;
    }

    private void removeWeakReference(View view)
    {
        Iterator<WeakReference<View>> it = mListView.iterator();
        while (it.hasNext())
        {
            WeakReference<View> item = it.next();
            View itemView = item.get();
            if (itemView != null)
            {
                if (itemView == view)
                {
                    it.remove();
                }
            }
        }
    }

    public void removeView(Class clazz)
    {
        List<View> list = getView(clazz);
        if (!list.isEmpty())
        {
            for (View item : list)
            {
                removeView(item);
            }
        }
    }

    public List<View> getView(Class clazz)
    {
        List<View> list = new ArrayList<>();
        if (clazz != null)
        {
            for (WeakReference<View> item : mListView)
            {
                View itemView = item.get();
                if (itemView != null)
                {
                    if (itemView.getClass() == clazz)
                    {
                        list.add(itemView);
                    }
                }
            }
        }
        return list;
    }

    public View getFirstView(Class clazz)
    {
        View view = null;
        if (clazz != null)
        {
            for (WeakReference<View> item : mListView)
            {
                View itemView = item.get();
                if (itemView != null)
                {
                    if (itemView.getClass() == clazz)
                    {
                        view = itemView;
                        break;
                    }
                }
            }
        }
        return view;
    }

    public boolean hasView(Class clazz)
    {
        return getFirstView(clazz) != null;
    }

}
