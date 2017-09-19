package com.fanwe.library.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;

/**
 * 监听软键盘显示隐藏（仅竖屏方向有效）
 */
public class SDKeyboardListener extends SDViewSizeListener
{
    private boolean mIsKeyboardActive;
    private int mKeyboardHeight = -1;
    private Rect mRect = new Rect();
    private SDKeyboardVisibilityCallback mKeyboardVisibilityCallback;

    public SDKeyboardListener setKeyboardVisibilityCallback(SDKeyboardVisibilityCallback keyboardVisibilityCallback)
    {
        mKeyboardVisibilityCallback = keyboardVisibilityCallback;
        return this;
    }

    public SDKeyboardListener setActivity(Activity activity)
    {
        setView(activity.findViewById(android.R.id.content));
        return this;
    }

    @Override
    protected int onGetWidth(View view)
    {
        view.getWindowVisibleDisplayFrame(mRect);
        return mRect.width();
    }

    @Override
    protected int onGetHeight(View view)
    {
        view.getWindowVisibleDisplayFrame(mRect);
        return mRect.height();
    }

    @Override
    protected void onHeightChanged(int newHeight, int oldHeight, View target)
    {
        super.onHeightChanged(newHeight, oldHeight, target);

        int differ = newHeight - oldHeight;
        int absDiffer = Math.abs(differ);
        if (absDiffer > 400)
        {
            if (differ > 0)
            {
                if (mIsKeyboardActive)
                {
                    if (absDiffer == mKeyboardHeight)
                    {
                        //键盘收起
                        mIsKeyboardActive = false;

                        if (mKeyboardVisibilityCallback != null)
                        {
                            mKeyboardVisibilityCallback.onKeyboardVisibilityChange(mIsKeyboardActive, absDiffer);
                        }
                        LogUtil.i("onKeyboard hdie:" + absDiffer);
                    }
                }
            } else
            {
                if (!mIsKeyboardActive)
                {
                    if (SDKeyboardUtil.isKeyboardActive(target.getContext()))
                    {
                        // 键盘显示
                        mIsKeyboardActive = true;
                        mKeyboardHeight = absDiffer;

                        if (mKeyboardVisibilityCallback != null)
                        {
                            mKeyboardVisibilityCallback.onKeyboardVisibilityChange(mIsKeyboardActive, absDiffer);
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
        return mKeyboardHeight;
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
