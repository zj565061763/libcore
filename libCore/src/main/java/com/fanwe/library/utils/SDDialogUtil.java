package com.fanwe.library.utils;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/4/26.
 */

public class SDDialogUtil
{
    //----------dialog position start----------

    /**
     * 设置dialog在view的顶部和view左边对齐
     *
     * @param dialog
     * @param view
     * @param marginBottom
     * @param marginLeft
     */
    public static void setDialogTopAlignLeft(Dialog dialog, View view, int marginBottom, int marginLeft)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.BOTTOM | Gravity.LEFT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] + marginLeft;
                int y = SDViewUtil.getScreenHeight() - location[1] + marginBottom;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的顶部和view中心对齐
     *
     * @param dialog
     * @param view
     * @param marginBottom
     * @param marginLeft
     */
    public static void setDialogTopAlignCenter(Dialog dialog, View view, int marginBottom, int marginLeft)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] - SDViewUtil.getScreenWidth() / 2 + view.getWidth() / 2 + marginLeft;
                int y = SDViewUtil.getScreenHeight() - location[1] + marginBottom;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的顶部和view右边对齐
     *
     * @param dialog
     * @param view
     * @param marginBottom
     * @param marginRight
     */
    public static void setDialogTopAlignRight(Dialog dialog, View view, int marginBottom, int marginRight)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = SDViewUtil.getScreenWidth() - location[0] - view.getWidth() + marginRight;
                int y = SDViewUtil.getScreenHeight() - location[1] + marginBottom;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的底部和view左边对齐
     *
     * @param dialog
     * @param view
     * @param marginTop
     * @param marginLeft
     */
    public static void setDialogBottomAlignLeft(Dialog dialog, View view, int marginTop, int marginLeft)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.TOP | Gravity.LEFT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] + marginLeft;
                int y = location[1] + view.getHeight() + marginTop - SDViewUtil.getActivityStatusBarHeight(view.getContext());

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的底部和view中心对齐
     *
     * @param dialog
     * @param view
     * @param marginTop
     * @param marginLeft
     */
    public static void setDialogBottomAlignCenter(Dialog dialog, View view, int marginTop, int marginLeft)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] - SDViewUtil.getScreenWidth() / 2 + view.getWidth() / 2 + marginLeft;
                int y = location[1] + view.getHeight() + marginTop - SDViewUtil.getActivityStatusBarHeight(view.getContext());

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的底部和view右边对齐
     *
     * @param dialog
     * @param view
     * @param marginTop
     * @param marginRight
     */
    public static void setDialogBottomAlignRight(Dialog dialog, View view, int marginTop, int marginRight)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.TOP | Gravity.RIGHT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = SDViewUtil.getScreenWidth() - location[0] - view.getWidth() + marginRight;
                int y = location[1] + view.getHeight() + marginTop - SDViewUtil.getActivityStatusBarHeight(view.getContext());

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的左边和view顶部对齐
     *
     * @param dialog
     * @param view
     * @param marginRight
     * @param marginTop
     */
    public static void setDialogLeftAlignTop(Dialog dialog, View view, int marginRight, int marginTop)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.TOP | Gravity.RIGHT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = SDViewUtil.getScreenWidth() - location[0] + marginRight;
                int y = location[1] + marginTop - SDViewUtil.getActivityStatusBarHeight(view.getContext());

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的左边和view中心对齐
     *
     * @param dialog
     * @param view
     * @param marginRight
     * @param marginTop
     */
    public static void setDialogLeftAlignCenter(Dialog dialog, View view, int marginRight, int marginTop)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = SDViewUtil.getScreenWidth() - location[0] + marginRight;
                int y = location[1] - SDViewUtil.getScreenHeight() / 2 + view.getHeight() / 2 - SDViewUtil.getActivityStatusBarHeight(view.getContext()) / 2 + marginTop;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的左边和view底部对齐
     *
     * @param dialog
     * @param view
     * @param marginRight
     * @param marginBottom
     */
    public static void setDialogLeftAlignBottom(Dialog dialog, View view, int marginRight, int marginBottom)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = SDViewUtil.getScreenWidth() - location[0] + marginRight;
                int y = SDViewUtil.getScreenHeight() - location[1] - view.getHeight() + marginBottom;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的右边和view顶部对齐
     *
     * @param dialog
     * @param view
     * @param marginLeft
     * @param marginTop
     */
    public static void setDialogRightAlignTop(Dialog dialog, View view, int marginLeft, int marginTop)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.TOP | Gravity.LEFT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] + view.getWidth() + marginLeft;
                int y = location[1] + marginTop - SDViewUtil.getActivityStatusBarHeight(view.getContext());

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的右边和view中心对齐
     *
     * @param dialog
     * @param view
     * @param marginLeft
     * @param marginTop
     */
    public static void setDialogRightAlignCenter(Dialog dialog, View view, int marginLeft, int marginTop)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] + view.getWidth() + marginLeft;
                int y = location[1] - SDViewUtil.getScreenHeight() / 2 + view.getHeight() / 2 - SDViewUtil.getActivityStatusBarHeight(view.getContext()) / 2 + marginTop;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 设置dialog在view的右边和view底部对齐
     *
     * @param dialog
     * @param view
     * @param marginLeft
     * @param marginBottom
     */
    public static void setDialogRightAlignBottom(Dialog dialog, View view, int marginLeft, int marginBottom)
    {
        if (dialog != null && view != null)
        {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            if (params != null)
            {
                params.gravity = Gravity.BOTTOM | Gravity.LEFT;
                int[] location = SDViewUtil.getLocationOnScreen(view);
                int x = location[0] + view.getWidth() + marginLeft;
                int y = SDViewUtil.getScreenHeight() - location[1] - view.getHeight() + marginBottom;

                params.x = x;
                params.y = y;
                dialog.getWindow().setAttributes(params);
            }
        }
    }

    //----------dialog position end----------
}
