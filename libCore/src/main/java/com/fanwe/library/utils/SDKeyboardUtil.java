package com.fanwe.library.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘操作工具类
 */
public class SDKeyboardUtil
{

    private SDKeyboardUtil()
    {
    }

    /**
     * 延迟显示软键盘
     *
     * @param view
     * @param delay 延迟多少毫秒
     */
    public static void showKeyboard(final View view, long delay)
    {
        if (view == null)
        {
            return;
        }
        if (delay <= 0)
        {
            showKeyboard(view);
        } else
        {
            view.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    showKeyboard(view);
                }
            }, delay);
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showKeyboard(View view)
    {
        if (view == null)
        {
            return;
        }
        InputMethodManager manager = getInputMethodManager(view.getContext());
        view.setFocusable(true);
        view.requestFocus();
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view)
    {
        if (view == null)
        {
            return;
        }
        InputMethodManager manager = getInputMethodManager(view.getContext());
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        if (manager.isActive())
        {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 软键盘是否处于显示状态
     *
     * @param context
     * @return
     */
    public static boolean isKeyboardActive(Context context)
    {
        InputMethodManager manager = getInputMethodManager(context);
        return manager.isActive();
    }

    /**
     * 获得InputMethodManager对象
     *
     * @param context
     * @return
     */
    public static InputMethodManager getInputMethodManager(Context context)
    {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return manager;
    }
}
