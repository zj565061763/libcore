package com.fanwe.library.utils;

import android.app.Activity;
import android.view.View;

/**
 * 监听软键盘显示隐藏（仅竖屏方向有效）
 */
public class SDKeyboardListener extends SDWindowSizeListener
{
    private boolean isKeyboardActive;
    private int keyboardHeight = -1;
    private SDKeyboardVisibilityCallback callback;

    /**
     * 监听软键盘显示隐藏
     *
     * @param activity
     * @param callback
     */
    public void listen(Activity activity, SDKeyboardVisibilityCallback callback)
    {
        super.listen(activity, null);
        this.callback = callback;
    }

    @Override
    protected void onHeightChanged(int newHeight, int oldHeight, int differ, View target)
    {
        super.onHeightChanged(newHeight, oldHeight, differ, target);

        int absDiffer = Math.abs(differ);
        if (absDiffer > 400)
        {
            if (differ > 0)
            {
                if (isKeyboardActive)
                {
                    if (absDiffer == keyboardHeight)
                    {
                        //键盘收起
                        isKeyboardActive = false;

                        if (callback != null)
                        {
                            callback.onKeyboardVisibilityChange(isKeyboardActive, absDiffer);
                        }
                        LogUtil.i("onKeyboard hdie:" + absDiffer);
                    }
                }
            } else
            {
                if (!isKeyboardActive)
                {
                    if (SDKeyboardUtil.isKeyboardActive(target.getContext()))
                    {
                        // 键盘显示
                        isKeyboardActive = true;
                        keyboardHeight = absDiffer;

                        if (callback != null)
                        {
                            callback.onKeyboardVisibilityChange(isKeyboardActive, absDiffer);
                        }
                        LogUtil.i("onKeyboard show:" + absDiffer);
                    }
                }
            }
        }
    }

    /**
     * 获得键盘高度
     *
     * @return
     */
    public int getKeyboardHeight()
    {
        return keyboardHeight;
    }

    public interface SDKeyboardVisibilityCallback
    {
        /**
         * 软键盘显示隐藏回调
         *
         * @param visible true-软键盘显示，false-软盘隐藏
         * @param height  软键盘高度
         */
        void onKeyboardVisibilityChange(boolean visible, int height);
    }

}
