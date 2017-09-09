package com.fanwe.library.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.fanwe.library.listener.SDAnimatorCreateCallback;

import java.util.ArrayList;
import java.util.List;

public class SDAnimationUtil
{

    public static String getPropertyAlpha()
    {
        return "alpha";
    }

    public static String getPropertyScaleX()
    {
        return "scaleX";
    }

    public static String getPropertyScaleY()
    {
        return "scaleY";
    }

    public static String getPropertyTranslationY()
    {
        return "translationY";
    }

    public static String getPropertyTranslationX()
    {
        return "translationX";
    }

    public static ObjectAnimator rotation(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = ObjectAnimator.ofFloat(view, "rotation", values);
            animator.setInterpolator(new LinearInterpolator());
        }
        return animator;
    }

    public static ObjectAnimator translationY(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = ObjectAnimator.ofFloat(view, "translationY", values);
        }
        return animator;
    }

    public static ObjectAnimator translationX(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = ObjectAnimator.ofFloat(view, "translationX", values);
        }
        return animator;
    }

    public static ObjectAnimator scaleX(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = ObjectAnimator.ofFloat(view, "scaleX", values);
        }
        return animator;
    }

    public static ObjectAnimator scaleY(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = ObjectAnimator.ofFloat(view, "scaleY", values);
        }
        return animator;
    }

    public static AnimatorSet scale(View view, float... values)
    {
        AnimatorSet animator = null;
        if (view != null)
        {
            animator = new AnimatorSet();

            ObjectAnimator animatorX = scaleX(view, values);
            ObjectAnimator animatorY = scaleY(view, values);

            animator.playTogether(animatorX, animatorY);
        }
        return animator;
    }

    public static ObjectAnimator alpha(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = ObjectAnimator.ofFloat(view, "alpha", values);
        }
        return animator;
    }

    public static ObjectAnimator rotationInfinite(View view, float... values)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = rotation(view, values);
            animator.setRepeatCount(ValueAnimator.INFINITE);
        }
        return animator;
    }

    public static ObjectAnimator alphaIn(View view)
    {
        return alpha(view, 0.0f, 1.0f);
    }

    public static ObjectAnimator alphaOut(View view)
    {
        return alpha(view, 1.0f, 0.0f);
    }

    public static ObjectAnimator translateInLeft(View view)
    {
        return translationX(view, -view.getWidth(), 0);
    }

    public static ObjectAnimator translateOutLeft(View view)
    {
        return translationX(view, 0, -view.getWidth());
    }

    public static ObjectAnimator translateInRight(View view)
    {
        return translationX(view, view.getWidth(), 0);
    }

    public static ObjectAnimator translateOutRight(View view)
    {
        return translationX(view, 0, view.getWidth());
    }

    public static ObjectAnimator translateInTop(View view)
    {
        return translationY(view, -view.getHeight(), 0);
    }

    public static ObjectAnimator translateOutTop(View view)
    {
        return translationY(view, 0, -view.getHeight());
    }

    public static ObjectAnimator translateInBottom(View view)
    {
        return translationY(view, view.getHeight(), 0);
    }

    public static ObjectAnimator translateOutBottom(View view)
    {
        return translationY(view, 0, view.getHeight());
    }

    public static AnimatorSet playTogether(Animator... items)
    {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(items);
        return animatorSet;
    }

    public static AnimatorSet playSequentially(Animator... items)
    {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(items);
        return animatorSet;
    }

    public static ObjectAnimator stopAnimator(View view, long duration)
    {
        ObjectAnimator animator = null;
        if (view != null)
        {
            animator = alpha(view, view.getAlpha());
            animator.setDuration(duration);
        }
        return animator;
    }

    public static ObjectAnimator translateMirrorHorizontal(View view)
    {
        if (view != null)
        {
            int viewX = SDViewUtil.getXOnScreen(view);
            int viewWidth = SDViewUtil.getWidth(view);
            int screenWidth = SDViewUtil.getScreenWidth();

            int startX = 0;
            int endX = 0;

            int middleX = screenWidth / 2 - viewWidth / 2;
            int deltaX = Math.abs(viewX - middleX);
            if (viewX > middleX)
            {
                endX = -2 * deltaX;
            } else if (viewX < middleX)
            {
                endX = 2 * deltaX;
            }
            return translationX(view, startX, endX);
        }
        return null;
    }

    public static ObjectAnimator translateMirrorVertical(View view)
    {
        if (view != null)
        {
            int viewY = SDViewUtil.getYOnScreen(view);
            int viewHeight = SDViewUtil.getHeight(view);
            int screenHeight = SDViewUtil.getScreenHeight() - SDViewUtil.getStatusBarHeight();

            int startY = 0;
            int endY = 0;

            int middleY = screenHeight / 2 - viewHeight / 2;
            int deltaY = Math.abs(viewY - middleY);
            if (viewY > middleY)
            {
                endY = -2 * deltaY;
            } else if (viewY < middleY)
            {
                endY = 2 * deltaY;
            }
            return translationY(view, startY, endY);
        }
        return null;
    }


    /**
     * view贴住屏幕左侧该移动的距离
     *
     * @param view
     * @return
     */
    public static int getXLeft(View view)
    {
        if (view != null)
        {
            int viewX = SDViewUtil.getXOnScreen(view);

            return -viewX;
        }
        return 0;
    }

    /**
     * view在屏幕左侧外面该移动的距离
     *
     * @param view
     * @return
     */
    public static int getXLeftOut(View view)
    {
        if (view != null)
        {
            int viewWidth = SDViewUtil.getWidth(view);

            return getXLeft(view) - viewWidth;
        }
        return 0;
    }

    /**
     * view的x在屏幕中间该移动的距离
     *
     * @param view
     * @return
     */
    public static int getXCenter(View view)
    {
        if (view != null)
        {
            int screenWidth = SDViewUtil.getScreenWidth();

            return getXLeft(view) + screenWidth / 2;
        }
        return 0;
    }

    /**
     * view的x中心点在屏幕中间该移动的距离
     *
     * @param view
     * @return
     */
    public static int getXCenterCenter(View view)
    {
        if (view != null)
        {
            int viewWidth = SDViewUtil.getWidth(view);

            return getXCenter(view) - viewWidth / 2;
        }
        return 0;
    }

    /**
     * view贴住屏幕右侧该移动的距离
     *
     * @param view
     * @return
     */
    public static int getXRight(View view)
    {
        if (view != null)
        {
            int viewWidth = SDViewUtil.getWidth(view);
            int screenWidth = SDViewUtil.getScreenWidth();

            return getXLeft(view) + (screenWidth - viewWidth);
        }
        return 0;
    }

    /**
     * view在屏幕右侧外面该移动的距离
     *
     * @param view
     * @return
     */
    public static int getXRightOut(View view)
    {
        if (view != null)
        {
            int viewWidth = SDViewUtil.getWidth(view);

            return getXRight(view) + viewWidth;
        }
        return 0;
    }

    /**
     * view贴住屏幕顶部该移动的距离
     *
     * @param view
     * @return
     */
    public static int getYTop(View view)
    {
        if (view != null)
        {
            int viewY = SDViewUtil.getYOnScreen(view);
            return -viewY;
        }
        return 0;
    }

    /**
     * view在屏幕顶部外面该移动的距离
     *
     * @param view
     * @return
     */
    public static int getYTopOut(View view)
    {
        if (view != null)
        {
            int viewHeight = SDViewUtil.getHeight(view);

            return getYTop(view) - viewHeight;
        }
        return 0;
    }

    /**
     * view的y在屏幕中间该移动的距离
     *
     * @param view
     * @return
     */
    public static int getYCenter(View view)
    {
        if (view != null)
        {
            int screenHeight = SDViewUtil.getScreenHeight();
            return getYTop(view) + screenHeight / 2;
        }
        return 0;
    }

    /**
     * view的y中心点在屏幕中间该移动的距离
     *
     * @param view
     * @return
     */
    public static int getYCenterCenter(View view)
    {
        if (view != null)
        {
            int viewHeight = SDViewUtil.getHeight(view);

            return getYCenter(view) - viewHeight / 2;
        }
        return 0;
    }

    /**
     * view贴住屏幕底部该移动的距离
     *
     * @param view
     * @return
     */
    public static int getYBottom(View view)
    {
        if (view != null)
        {
            int screenHeight = SDViewUtil.getScreenHeight();
            int viewHeight = SDViewUtil.getHeight(view);

            return getYTop(view) + (screenHeight - viewHeight);
        }
        return 0;
    }

    /**
     * view在屏幕底部外面该移动的距离
     *
     * @param view
     * @return
     */
    public static int getYBottomOut(View view)
    {
        if (view != null)
        {
            int viewHeight = SDViewUtil.getHeight(view);

            return getYBottom(view) + viewHeight;
        }
        return 0;
    }


    public static ObjectAnimator translateLeftToRight(View view)
    {
        if (view != null)
        {
            int viewX = SDViewUtil.getXOnScreen(view);
            int viewWidth = SDViewUtil.getWidth(view);
            int screenWidth = SDViewUtil.getScreenWidth();

            int startX = -viewX - viewWidth;
            int endX = screenWidth - viewX;
            return translationX(view, startX, endX);
        }
        return null;
    }

    public static ObjectAnimator translateRightToLeft(View view)
    {
        if (view != null)
        {
            int viewX = SDViewUtil.getXOnScreen(view);
            int viewWidth = SDViewUtil.getWidth(view);
            int screenWidth = SDViewUtil.getScreenWidth();

            int startX = screenWidth - viewX;
            int endX = -viewX - viewWidth;
            return translationX(view, startX, endX);
        }
        return null;
    }

    public static ObjectAnimator translateTopToBottom(View view)
    {
        if (view != null)
        {
            int viewY = SDViewUtil.getYOnScreen(view) - SDViewUtil.getStatusBarHeight();
            int viewHeight = SDViewUtil.getHeight(view);
            int screenHeight = SDViewUtil.getScreenHeight();

            int startY = -viewY - viewHeight;
            int endY = screenHeight - viewY;
            return translationY(view, startY, endY);
        }
        return null;
    }

    public static ObjectAnimator translateBottomToTop(View view)
    {
        if (view != null)
        {
            int viewY = SDViewUtil.getYOnScreen(view) - SDViewUtil.getStatusBarHeight();
            int viewHeight = SDViewUtil.getHeight(view);
            int screenHeight = SDViewUtil.getScreenHeight();

            int startY = screenHeight - viewY;
            int endY = -viewY - viewHeight;
            return translationY(view, startY, endY);
        }
        return null;
    }

    public static AnimatorSet playSequentially(View target, String propertyName, SDAnimatorCreateCallback listener, float... values)
    {
        AnimatorSet animatorSet = null;
        if (values != null && values.length > 0 && propertyName != null)
        {
            animatorSet = new AnimatorSet();
            List<Animator> items = new ArrayList<>();
            for (int i = 0; i < values.length - 1; i++)
            {
                int j = i + 1;

                ObjectAnimator animator = new ObjectAnimator();
                animator.setTarget(target);
                animator.setPropertyName(propertyName);
                animator.setFloatValues(values[i], values[j]);
                if (listener != null)
                {
                    listener.onCreate(i, animator);
                }
                items.add(animator);
            }
            animatorSet.playSequentially(items);
        }
        return animatorSet;
    }
}
