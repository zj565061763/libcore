package com.fanwe.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.adapter.SDAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SDViewUtil
{
    /**
     * 设置view的背景
     *
     * @param view
     * @param drawable
     */
    public static void setBackgroundDrawable(View view, Drawable drawable)
    {
        if (view == null)
        {
            return;
        }
        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();
        int paddingRight = view.getPaddingRight();
        int paddingBottom = view.getPaddingBottom();
        view.setBackgroundDrawable(drawable);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * 设置view的背景
     *
     * @param view
     * @param resId 背景资源id
     */
    public static void setBackgroundResource(View view, int resId)
    {
        if (view == null)
        {
            return;
        }
        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();
        int paddingRight = view.getPaddingRight();
        int paddingBottom = view.getPaddingBottom();
        view.setBackgroundResource(resId);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * 设置view的背景颜色
     *
     * @param view
     * @param resId 颜色资源id
     */
    public static void setBackgroundColorResId(View view, int resId)
    {
        view.setBackgroundColor(SDResourcesUtil.getColor(resId));
    }

    /**
     * 设置textView的字体颜色
     *
     * @param textView
     * @param resId    字体颜色资源id
     */
    public static void setTextViewColorResId(TextView textView, int resId)
    {
        textView.setTextColor(SDResourcesUtil.getColor(resId));
    }

    /**
     * 设置textView的字体大小
     *
     * @param textView
     * @param sizeSp   sp单位
     */
    public static void setTextSizeSp(TextView textView, float sizeSp)
    {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
    }

    /**
     * 设置textView的字体大小
     *
     * @param textView
     * @param sizeDp   dp单位
     */
    public static void setTextSizeDp(TextView textView, float sizeDp)
    {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, sizeDp);
    }

    /**
     * 设置textView的字体大小
     *
     * @param textView
     * @param sizePx   px单位
     */
    public static void setTextSizePx(TextView textView, float sizePx)
    {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx);
    }

    /**
     * 设置view的左边margin（当view的LayoutParams instanceof MarginLayoutParams才有效）
     *
     * @param view
     * @param left
     */
    public static void setMarginLeft(View view, int left)
    {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
        {
            return;
        }
        if (params.leftMargin != left)
        {
            params.leftMargin = left;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置view的顶部margin（当view的LayoutParams instanceof MarginLayoutParams才有效）
     *
     * @param view
     * @param top
     */
    public static void setMarginTop(View view, int top)
    {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
        {
            return;
        }
        if (params.topMargin != top)
        {
            params.topMargin = top;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置view的右边margin（当view的LayoutParams instanceof MarginLayoutParams才有效）
     *
     * @param view
     * @param right
     */
    public static void setMarginRight(View view, int right)
    {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
        {
            return;
        }
        if (params.rightMargin != right)
        {
            params.rightMargin = right;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置view的底部margin（当view的LayoutParams instanceof MarginLayoutParams才有效）
     *
     * @param view
     * @param bottom
     */
    public static void setMarginBottom(View view, int bottom)
    {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
        {
            return;
        }
        if (params.bottomMargin != bottom)
        {
            params.bottomMargin = bottom;
            view.setLayoutParams(params);
        }
    }

    /**
     * 设置view的上下左右margin（当view的LayoutParams instanceof MarginLayoutParams才有效）
     *
     * @param view
     * @param margins
     */
    public static void setMargins(View view, int margins)
    {
        setMargin(view, margins, margins, margins, margins);
    }

    /**
     * 设置view的上下左右margin（当view的LayoutParams instanceof MarginLayoutParams才有效）
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMargin(View view, int left, int top, int right, int bottom)
    {
        MarginLayoutParams params = getMarginLayoutParams(view);
        if (params == null)
        {
            return;
        }

        boolean needSet = false;
        if (params.leftMargin != left)
        {
            params.leftMargin = left;
            needSet = true;
        }
        if (params.topMargin != top)
        {
            params.topMargin = top;
            needSet = true;
        }
        if (params.rightMargin != right)
        {
            params.rightMargin = right;
            needSet = true;
        }
        if (params.bottomMargin != bottom)
        {
            params.bottomMargin = bottom;
            needSet = true;
        }
        if (needSet)
        {
            view.setLayoutParams(params);
        }
    }

    /**
     * 获得view的MarginLayoutParams，返回值可能为null
     *
     * @param view
     * @return
     */
    public static MarginLayoutParams getMarginLayoutParams(View view)
    {
        if (view == null)
        {
            return null;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params instanceof MarginLayoutParams)
        {
            return (MarginLayoutParams) params;
        } else
        {
            return null;
        }
    }

    /**
     * 设置该view在父布局中的gravity
     *
     * @param view
     * @param gravity
     */
    public static void setLayoutGravity(View view, int gravity)
    {
        if (view == null)
        {
            return;
        }

        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null)
        {
            return;
        }

        if (p instanceof FrameLayout.LayoutParams)
        {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) p;
            if (params.gravity != gravity)
            {
                params.gravity = gravity;
                view.setLayoutParams(params);
            }
        } else if (p instanceof LinearLayout.LayoutParams)
        {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) p;
            if (params.gravity != gravity)
            {
                params.gravity = gravity;
                view.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置view的左边padding
     *
     * @param view
     * @param left
     */
    public static void setPaddingLeft(View view, int left)
    {
        if (view == null)
        {
            return;
        }
        view.setPadding(left, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 设置view的顶部padding
     *
     * @param view
     * @param top
     */
    public static void setPaddingTop(View view, int top)
    {
        if (view == null)
        {
            return;
        }
        view.setPadding(view.getPaddingLeft(), top, view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 设置view的右边padding
     *
     * @param view
     * @param right
     */
    public static void setPaddingRight(View view, int right)
    {
        if (view == null)
        {
            return;
        }
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), right, view.getPaddingBottom());
    }

    /**
     * 设置view的底部padding
     *
     * @param view
     * @param bottom
     */
    public static void setPaddingBottom(View view, int bottom)
    {
        if (view == null)
        {
            return;
        }
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), bottom);
    }

    /**
     * 设置view的上下左右padding
     *
     * @param view
     * @param paddings
     */
    public static void setPaddings(View view, int paddings)
    {
        if (view == null)
        {
            return;
        }
        view.setPadding(paddings, paddings, paddings, paddings);
    }

    // ------------------------layoutInflater
    public static LayoutInflater getLayoutInflater()
    {
        return LayoutInflater.from(SDLibrary.getInstance().getContext());
    }

    public static View inflate(int resource, ViewGroup root)
    {
        return getLayoutInflater().inflate(resource, root);
    }

    public static View inflate(int resource, ViewGroup root, boolean attachToRoot)
    {
        return getLayoutInflater().inflate(resource, root, attachToRoot);
    }

    public static DisplayMetrics getDisplayMetrics()
    {
        return SDLibrary.getInstance().getContext().getResources().getDisplayMetrics();
    }

    public static int sp2px(float sp)
    {
        final float fontScale = getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    public static int dp2px(float dp)
    {
        final float scale = getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px)
    {
        final float scale = getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth()
    {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getScreenHeight()
    {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 获得屏幕宽度的百分比
     *
     * @param percent [0-1]
     * @return
     */
    public static int getScreenWidthPercent(float percent)
    {
        return (int) ((float) getScreenWidth() * percent);
    }

    /**
     * 获得屏幕高度的百分比
     *
     * @param percent [0-1]
     * @return
     */
    public static int getScreenHeightPercent(float percent)
    {
        return (int) ((float) getScreenHeight() * percent);
    }

    /**
     * 根据传入的宽度，获得按指定比例缩放后的高度
     *
     * @param scaleWidth  指定的比例宽度
     * @param scaleHeight 指定的比例高度
     * @param width       宽度
     * @return
     */
    public static int getScaleHeight(int scaleWidth, int scaleHeight, int width)
    {
        int result = 0;
        if (scaleWidth != 0)
        {
            result = scaleHeight * width / scaleWidth;
        }
        return result;
    }

    /**
     * 根据传入的高度，获得按指定比例缩放后的宽度
     *
     * @param scaleWidth  指定的比例宽度
     * @param scaleHeight 指定的比例高度
     * @param height      高度
     * @return
     */
    public static int getScaleWidth(int scaleWidth, int scaleHeight, int height)
    {
        int result = 0;
        if (scaleHeight != 0)
        {
            result = scaleWidth * height / scaleHeight;
        }
        return result;
    }

    /**
     * 判断当前线程是否是主线程.
     *
     * @return
     */
    public static boolean isMainThread()
    {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    /**
     * 测量view，测量后，可以获得view的测量宽高
     *
     * @param v
     */
    public static void measureView(View v)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }

    /**
     * 获得view的高度
     *
     * @param view
     * @return
     */
    public static int getHeight(View view)
    {
        int height = 0;
        height = view.getHeight();
        if (height <= 0)
        {
            if (view.getLayoutParams() != null)
            {
                height = view.getLayoutParams().height;
            }
        }
        if (height <= 0)
        {
            measureView(view);
            height = view.getMeasuredHeight();
        }
        return height;
    }

    /**
     * 获得view的宽度
     *
     * @param view
     * @return
     */
    public static int getWidth(View view)
    {
        int width = 0;
        width = view.getWidth();
        if (width <= 0)
        {
            if (view.getLayoutParams() != null)
            {
                width = view.getLayoutParams().width;
            }
        }
        if (width <= 0)
        {
            measureView(view);
            width = view.getMeasuredWidth();
        }
        return width;
    }


    /**
     * 设置view的高度
     *
     * @param view
     * @param height
     * @return
     */
    public static boolean setHeight(View view, int height)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null)
        {
            if (params.height != height)
            {
                params.height = height;
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置view的宽度
     *
     * @param view
     * @param width
     * @return
     */
    public static boolean setWidth(View view, int width)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null)
        {
            if (params.width != width)
            {
                params.width = width;
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置view的宽度和高度
     *
     * @param view
     * @param width
     * @param height
     * @return
     */
    public static boolean setSize(View view, int width, int height)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null)
        {
            boolean needSet = false;
            if (params.width != width)
            {
                params.width = width;
                needSet = true;
            }
            if (params.height != height)
            {
                params.height = height;
                needSet = true;
            }
            if (needSet)
            {
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 当view的父布局是RelativeLayout的时候，设置view的布局规则
     *
     * @param view
     * @param anchorId
     * @param rules
     */
    public static void addRule(View view, int anchorId, Integer... rules)
    {
        if (view == null || rules == null)
        {
            return;
        }
        if (!(view.getLayoutParams() instanceof RelativeLayout.LayoutParams))
        {
            return;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (params == null)
        {
            return;
        }

        for (Integer item : rules)
        {
            if (anchorId != 0)
            {
                params.addRule(item, anchorId);
            } else
            {
                params.addRule(item);
            }
        }
        view.setLayoutParams(params);
    }

    /**
     * 当view的父布局是RelativeLayout的时候，移除view的布局规则
     *
     * @param view
     * @param rules 要移除的布局规则
     */
    public static void removeRule(View view, Integer... rules)
    {
        if (view == null || rules == null)
        {
            return;
        }
        if (!(view.getLayoutParams() instanceof RelativeLayout.LayoutParams))
        {
            return;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (params == null)
        {
            return;
        }

        for (Integer item : rules)
        {
            params.removeRule(item);
        }
        view.setLayoutParams(params);
    }

    /**
     * view是否处于VISIBLE状态
     *
     * @param view
     * @return
     */
    public static boolean isVisible(View view)
    {
        if (view == null)
        {
            return false;
        } else
        {
            return view.getVisibility() == View.VISIBLE;
        }
    }

    /**
     * view是否处于INVISIBLE状态
     *
     * @param view
     * @return
     */
    public static boolean isInvisible(View view)
    {
        if (view == null)
        {
            return false;
        } else
        {
            return view.getVisibility() == View.INVISIBLE;
        }
    }

    /**
     * view是否处于GONE状态
     *
     * @param view
     * @return
     */
    public static boolean isGone(View view)
    {
        if (view == null)
        {
            return false;
        } else
        {
            return view.getVisibility() == View.GONE;
        }
    }

    /**
     * 设置view为VISIBLE
     *
     * @param view
     * @return true-view处于VISIBLE
     */
    public static boolean setVisible(View view)
    {
        if (view == null)
        {
            return false;
        }
        if (isVisible(view))
        {
            return true;
        }
        view.setVisibility(View.VISIBLE);
        return true;
    }

    /**
     * 设置view为INVISIBLE
     *
     * @param view
     * @return true-view处于INVISIBLE
     */
    public static boolean setInvisible(View view)
    {
        if (view == null)
        {
            return false;
        }
        if (isInvisible(view))
        {
            return true;
        }
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    /**
     * 设置view为GONE
     *
     * @param view
     * @return true-view处于GONE
     */
    public static boolean setGone(View view)
    {
        if (view == null)
        {
            return false;
        }
        if (isGone(view))
        {
            return true;
        }
        view.setVisibility(View.GONE);
        return true;
    }

    /**
     * 设置view在VISIBLE和GONE之间切换
     *
     * @param view
     * @return true-view处于VISIBLE
     */
    public static boolean toggleVisibleOrGone(View view)
    {
        if (view == null)
        {
            return false;
        }
        if (isVisible(view))
        {
            setGone(view);
            return false;
        } else
        {
            setVisible(view);
            return true;
        }
    }

    /**
     * 设置view在VISIBLE和INVISIBLE之间切换
     *
     * @param view
     * @return true-view处于VISIBLE
     */
    public static boolean toggleVisibleOrInvisible(View view)
    {
        if (view == null)
        {
            return false;
        }
        if (isVisible(view))
        {
            setInvisible(view);
            return false;
        } else
        {
            setVisible(view);
            return true;
        }
    }

    /**
     * 设置view为VISIBLE或者INVISIBLE
     *
     * @param view
     * @param visible
     * @return
     */
    public static boolean setVisibleOrInvisible(View view, boolean visible)
    {
        if (visible)
        {
            setVisible(view);
        } else
        {
            setInvisible(view);
        }
        return visible;
    }

    public static boolean setVisibleOrGone(View view, boolean visible)
    {
        if (visible)
        {
            setVisible(view);
        } else
        {
            setGone(view);
        }
        return visible;
    }

    /**
     * 获得view在屏幕上的x坐标
     *
     * @param view
     * @return
     */
    public static int getXOnScreen(View view)
    {
        return getLocationOnScreen(view)[0];
    }

    /**
     * 获得view在屏幕上的y坐标
     *
     * @param view
     * @return
     */
    public static int getYOnScreen(View view)
    {
        return getLocationOnScreen(view)[1];
    }

    /**
     * 获得view在屏幕上的坐标
     *
     * @param view
     * @return 数组第一个元素是x坐标，第二个是y坐标
     */
    public static int[] getLocationOnScreen(View view)
    {
        int[] location = {0, 0};
        if (view != null)
        {
            view.getLocationOnScreen(location);
        }
        return location;
    }

    /**
     * 获得view在屏幕上的坐标，y坐标会减去状态栏的高度
     *
     * @param view
     * @return 数组第一个元素是x坐标，第二个是y坐标
     */
    public static int[] getLocationOnScreenIgnoreStatusBar(View view)
    {
        int[] location = getLocationOnScreen(view);
        if (location != null)
        {
            int statusBarHeight = getStatusBarHeight();
            location[1] -= statusBarHeight;
        }
        return location;
    }


    /**
     * 把view从它的父布局移除
     *
     * @param view
     */
    public static void removeView(View view)
    {
        try
        {
            if (view == null)
            {
                return;
            }
            ViewParent viewParent = view.getParent();
            if (viewParent == null)
            {
                return;
            }
            if (!(viewParent instanceof ViewGroup))
            {
                return;
            }
            ViewGroup parent = (ViewGroup) viewParent;
            parent.removeView(view);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获得view的截图
     *
     * @param view
     * @return
     */
    public static Bitmap createViewBitmap(View view)
    {
        Bitmap bmp = null;
        if (view != null)
        {
            view.setDrawingCacheEnabled(true);
            Bitmap drawingCache = view.getDrawingCache();
            if (drawingCache != null)
            {
                bmp = Bitmap.createBitmap(drawingCache);
            }
            view.destroyDrawingCache();
        }
        return bmp;
    }

    /**
     * 获得view的镜像
     *
     * @param view
     * @return
     */
    public static ImageView getViewsImage(View view)
    {
        Bitmap bmp = createViewBitmap(view);
        if (bmp == null)
        {
            return null;
        }
        ImageView imageView = new ImageView(view.getContext());
        imageView.setImageBitmap(bmp);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(bmp.getWidth(), bmp.getHeight());
        imageView.setLayoutParams(params);
        return imageView;
    }

    /**
     * 获得view在屏幕上的可见范围
     *
     * @param view
     * @return
     */
    public static Rect getGlobalVisibleRect(View view)
    {
        return getGlobalVisibleRect(view, null);
    }

    /**
     * 获得view在屏幕上的可见范围
     *
     * @param view
     * @param outRect 如果为null，内部会创建一个Rect对象
     * @return
     */
    public static Rect getGlobalVisibleRect(View view, Rect outRect)
    {
        if (outRect == null)
        {
            outRect = new Rect();
        }
        if (view != null && view.getVisibility() == View.VISIBLE)
        {
            view.getGlobalVisibleRect(outRect);
        }
        return outRect;
    }

    /**
     * 相对屏幕的x和y坐标是否在view的区域内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isViewUnder(View view, int x, int y)
    {
        boolean result = false;
        Rect r = getGlobalVisibleRect(view);
        if (r != null)
        {
            result = r.contains(x, y);
        }
        return result;
    }

    /**
     * MotionEvent是否在view的区域内
     *
     * @param view
     * @param e
     * @return
     */
    public static boolean isViewUnder(View view, MotionEvent e)
    {
        boolean result = false;
        if (e != null)
        {
            result = isViewUnder(view, (int) e.getRawX(), (int) e.getRawY());
        }
        return result;
    }

    public static void wrapperPopupWindow(PopupWindow pop)
    {
        if (pop != null)
        {
            ColorDrawable dw = new ColorDrawable(0x00ffffff);
            pop.setBackgroundDrawable(dw);
            pop.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
            pop.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
            pop.setFocusable(true);
            pop.setOutsideTouchable(true);
        }
    }

    /**
     * 按指定drawable的比例缩放view的高度
     *
     * @param view
     * @param drawable
     */
    public static void scaleHeight(View view, Drawable drawable)
    {
        if (drawable != null)
        {
            scaleHeight(view, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    /**
     * 按指定比例缩放view的高度
     *
     * @param view
     * @param scaleWidth  指定比例宽度
     * @param scaleHeight 指定比例高度
     */
    public static void scaleHeight(View view, int scaleWidth, int scaleHeight)
    {
        if (view != null)
        {
            int viewWidth = getWidth(view);
            if (scaleWidth > 0)
            {
                int newHeight = getScaleHeight(scaleWidth, scaleHeight, viewWidth);
                setHeight(view, newHeight);
            }
        }
    }

    /**
     * 设置view的宽度为WRAP_CONTENT
     *
     * @param view
     */
    public static void setWidthWrapContent(View view)
    {
        setWidth(view, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置view的宽度为MATCH_PARENT
     *
     * @param view
     */
    public static void setWidthMatchParent(View view)
    {
        setWidth(view, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置view的高度为WRAP_CONTENT
     *
     * @param view
     */
    public static void setHeightWrapContent(View view)
    {
        setHeight(view, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置view的高度为MATCH_PARENT
     *
     * @param view
     */
    public static void setHeightMatchParent(View view)
    {
        setHeight(view, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置view的宽度为weight，仅当view处于LinearLayout里面时有效
     *
     * @param view
     * @param weight
     */
    public static void setWidthWeight(View view, float weight)
    {
        if (view != null)
        {
            ViewGroup.LayoutParams vgParams = view.getLayoutParams();
            if (vgParams != null && vgParams instanceof LayoutParams)
            {
                LayoutParams params = (LayoutParams) vgParams;
                if (params != null)
                {
                    params.width = 0;
                    params.weight = weight;
                    view.setLayoutParams(params);
                }
            }
        }
    }

    /**
     * 设置view的高度为weight，仅当view处于LinearLayout里面时有效
     *
     * @param view
     * @param weight
     */
    public static void setHeightWeight(View view, float weight)
    {
        if (view != null)
        {
            ViewGroup.LayoutParams vgParams = view.getLayoutParams();
            if (vgParams != null && vgParams instanceof LinearLayout.LayoutParams)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vgParams;
                if (params != null)
                {
                    params.height = 0;
                    params.weight = weight;
                    view.setLayoutParams(params);
                }
            }
        }
    }

    /**
     * 开始动画Drawable
     *
     * @param drawable
     */
    public static void startAnimationDrawable(Drawable drawable)
    {
        if (drawable != null && drawable instanceof AnimationDrawable)
        {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if (!animationDrawable.isRunning())
            {
                animationDrawable.start();
            }
        }
    }

    /**
     * 停止动画Drawable
     *
     * @param drawable
     */
    public static void stopAnimationDrawable(Drawable drawable)
    {
        stopAnimationDrawable(drawable, 0);
    }

    /**
     * 停止动画Drawable
     *
     * @param drawable
     * @param stopIndex 要停止在第几帧
     */
    public static void stopAnimationDrawable(Drawable drawable, int stopIndex)
    {
        if (drawable != null && drawable instanceof AnimationDrawable)
        {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            animationDrawable.stop();
            animationDrawable.selectDrawable(stopIndex);
        }
    }

    /**
     * 重置view（透明度1，旋转0，平移0，缩放0）
     *
     * @param view
     */
    public static void resetView(View view)
    {
        if (view != null)
        {
            view.setAlpha(1.0f);
            view.setRotation(0.0f);
            view.setRotationX(0.0f);
            view.setRotationY(0.0f);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }

    /**
     * 测量文字的宽度
     *
     * @param textView
     * @param content  文字内容
     * @return
     */
    public static float measureText(TextView textView, String content)
    {
        float width = 0;
        if (textView != null && content != null)
        {
            width = textView.getPaint().measureText(content);
        }
        return width;
    }

    /**
     * 用新的view去替换布局中的旧view
     *
     * @param oldView
     * @param newView
     */
    public static void replaceOldView(View oldView, View newView)
    {
        if (oldView != null && newView != null && oldView != newView)
        {
            ViewParent viewParent = oldView.getParent();
            if (viewParent instanceof ViewGroup)
            {
                ViewGroup viewGroup = (ViewGroup) viewParent;
                int index = viewGroup.indexOfChild(oldView);
                ViewGroup.LayoutParams params = oldView.getLayoutParams();

                removeView(oldView);
                removeView(newView);

                viewGroup.addView(newView, index, params);
            }
        }
    }

    /**
     * 替换child到parent，替换之前会先移除parent的所有view
     *
     * @param parent
     * @param child
     * @return
     */
    public static boolean replaceView(ViewGroup parent, View child)
    {
        return addView(parent, child, null, true);
    }

    /**
     * 添加child到parent
     *
     * @param parent
     * @param child
     * @return
     */
    public static boolean addView(ViewGroup parent, View child)
    {
        return addView(parent, child, null, false);
    }

    /**
     * 添加child到parent
     *
     * @param parent         父容器
     * @param child          要添加的view
     * @param params         布局参数
     * @param removeAllViews 添加的时候是否需要先移除parent的所有子view
     * @return
     */
    private static boolean addView(ViewGroup parent, View child, ViewGroup.LayoutParams params, boolean removeAllViews)
    {
        if (parent != null && child != null && child.getParent() != parent)
        {
            if (removeAllViews)
            {
                parent.removeAllViews();
            }
            removeView(child);
            if (params != null)
            {
                parent.addView(child, params);
            } else
            {
                parent.addView(child);
            }
            return true;
        }
        return false;
    }

    /**
     * 切换容器的内容为view<br>
     * 如果child没有被添加到parent，child将会被添加到parent，最终只显示child，隐藏parent的所有其他child
     *
     * @param parent
     * @param child
     */
    public static void toggleView(ViewGroup parent, View child)
    {
        if (child == null || parent == null)
        {
            return;
        }
        if (child.getParent() != parent)
        {
            removeView(child);
            parent.addView(child);
        }
        List<View> listChild = getChilds(parent);
        for (View item : listChild)
        {
            if (item == child)
            {
                setVisible(item);
            } else
            {
                setGone(item);
            }
        }
    }

    /**
     * 获得parent的所有第一级子view
     *
     * @param parent
     * @return
     */
    public static List<View> getChilds(ViewGroup parent)
    {
        if (parent == null)
        {
            return null;
        }
        int count = parent.getChildCount();
        if (count <= 0)
        {
            return null;
        }
        List<View> listChild = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            listChild.add(parent.getChildAt(i));
        }
        return listChild;
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = SDResourcesUtil.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = SDResourcesUtil.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 返回activity的状态栏高度<br>
     * 1.activity==null，返回状态栏高度<br>
     * 2.activity!=null，如果该activity的状态栏可见则返回状态栏高度，如果不可见则返回0
     *
     * @param activity
     * @return
     */
    public static int getActivityStatusBarHeight(Context activity)
    {
        if (activity == null)
        {
            return getStatusBarHeight();
        }
        if (!(activity instanceof Activity))
        {
            return getStatusBarHeight();
        }

        if (isStatusBarVisible((Activity) activity))
        {
            return getStatusBarHeight();
        } else
        {
            return 0;
        }
    }

    /**
     * 状态栏是否可见
     *
     * @param activity
     * @return
     */
    public static boolean isStatusBarVisible(Activity activity)
    {
        if (activity == null)
        {
            return false;
        }
        return ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0);
    }

    /**
     * 设置ImageView的图片为灰色
     *
     * @param imageView
     */
    public static void setImageViewGray(ImageView imageView)
    {
        if (imageView != null)
        {
            Drawable drawable = imageView.getDrawable();
            if (drawable != null)
            {
                Bitmap bmp = SDImageUtil.drawable2Bitmap(drawable);
                Bitmap bmpGray = SDImageUtil.getGrayBitmap(bmp);
                if (bmpGray != null)
                {
                    imageView.setImageBitmap(bmpGray);
                }
            }
        }
    }

    /**
     * 设置view的背景为灰色
     *
     * @param view
     */
    public static void setBackgroundGray(View view)
    {
        if (view != null)
        {
            Drawable drawable = view.getBackground();
            if (drawable != null)
            {
                Bitmap bmp = SDImageUtil.drawable2Bitmap(drawable);
                Bitmap bmpGray = SDImageUtil.getGrayBitmap(bmp);
                if (bmpGray != null)
                {
                    Drawable drawableGray = SDImageUtil.Bitmap2Drawable(bmpGray);
                    if (drawableGray != null)
                    {
                        setBackgroundDrawable(view, drawableGray);
                    }
                }
            }
        }
    }

    /**
     * 第一个item是否完全可见
     *
     * @param absListView
     * @return
     */
    public static boolean isFirstItemTotallyVisible(AbsListView absListView)
    {
        final Adapter adapter = absListView.getAdapter();
        if (null == adapter || adapter.isEmpty())
        {
            return true;
        } else
        {
            if (absListView.getFirstVisiblePosition() <= 1)
            {
                final View firstVisibleChild = absListView.getChildAt(0);
                if (firstVisibleChild != null)
                {
                    return firstVisibleChild.getTop() >= 0;
                }
            }
        }
        return false;
    }

    /**
     * 最后一个item是否可见
     *
     * @param absListView
     * @return
     */
    public static boolean isLastItemTotallyVisible(AbsListView absListView)
    {
        final Adapter adapter = absListView.getAdapter();
        if (null == adapter || adapter.isEmpty())
        {
            return true;
        } else
        {
            final int lastItemPosition = absListView.getCount() - 1;
            final int lastVisiblePosition = absListView.getLastVisiblePosition();
            if (lastVisiblePosition >= lastItemPosition - 1)
            {
                final int childIndex = lastVisiblePosition - absListView.getFirstVisiblePosition();
                final View lastVisibleChild = absListView.getChildAt(childIndex);
                if (lastVisibleChild != null)
                {
                    return lastVisibleChild.getBottom() <= absListView.getBottom();
                }
            }
        }
        return false;
    }

    /**
     * 滚动listview，当deltaY大于listview的高度的时候则该次调用只能滚动listview的高度
     *
     * @param deltaY   滚动的距离。正值：item向下移动，负值：item向上移动
     * @param listView
     */
    public static void scrollListBy(int deltaY, AbsListView listView)
    {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT)
        {
            listView.scrollListBy(-deltaY);
        } else
        {
            try
            {
                Method method = AbsListView.class.getDeclaredMethod("trackMotionScroll", int.class, int.class);
                if (method != null)
                {
                    method.setAccessible(true);
                    method.invoke(listView, deltaY, deltaY);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置状态栏背景
     *
     * @param activity
     * @param res      颜色资源或者图片资源
     */
    public static void setStatusBarTintResource(Activity activity, int res)
    {
        try
        {
            setStatusBarTransparent(activity, true);

            SystemBarTintManager manager = new SystemBarTintManager(activity);
            manager.setStatusBarTintEnabled(true);
            manager.setStatusBarTintResource(res);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏背景颜色
     *
     * @param activity
     * @param color    颜色值
     */
    public static void setStatusBarTintColor(Activity activity, int color)
    {
        try
        {
            setStatusBarTransparent(activity, true);

            SystemBarTintManager manager = new SystemBarTintManager(activity);
            manager.setStatusBarTintEnabled(true);
            manager.setStatusBarTintColor(color);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏背景透明
     *
     * @param activity
     * @param transparent true-透明，false不透明
     */
    public static void setStatusBarTransparent(Activity activity, boolean transparent)
    {
        try
        {
            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (transparent)
            {
                params.flags |= bits;
            } else
            {
                params.flags &= ~bits;
            }
            window.setAttributes(params);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------Deprecated methods start----------

    @Deprecated
    public static void updateImageViewSize(ImageView imageView, Drawable drawable)
    {
        scaleHeight(imageView, drawable);
    }

    @Deprecated
    public static <T> void updateAdapterByList(List<T> listOriginalData, List<T> listNewData, SDAdapter<T> mAdapter, boolean isLoadMore)
    {
        updateAdapterByList(listOriginalData, listNewData, mAdapter, isLoadMore, null, "未找到更多数据");
    }

    @Deprecated
    public static <T> void updateAdapterByList(List<T> listOriginalData, List<T> listNewData, SDAdapter<T> mAdapter, boolean isLoadMore,
                                               String noData, String noMoreData)
    {
        updateList(listOriginalData, listNewData, isLoadMore, noData, noMoreData);
        if (mAdapter != null && listOriginalData != null)
        {
            mAdapter.updateData(listOriginalData);
        }
    }

    @Deprecated
    public static <T> void updateList(List<T> listOriginalData, List<T> listNewData, boolean isLoadMore)
    {
        updateList(listOriginalData, listNewData, isLoadMore, null, "未找到更多数据");
    }

    @Deprecated
    public static <T> void updateList(List<T> listOriginalData, List<T> listNewData, boolean isLoadMore, String noData, String noMoreData)
    {
        if (listOriginalData != null)
        {
            if (listNewData != null && listNewData.size() > 0) // 有新数据
            {
                if (!isLoadMore)
                {
                    listOriginalData.clear();
                }
                listOriginalData.addAll(listNewData);
            } else
            {
                if (!isLoadMore)
                {
                    if (!TextUtils.isEmpty(noData))
                    {
                        SDToast.showToast(noData);
                    }
                    listOriginalData.clear();
                } else
                {
                    if (!TextUtils.isEmpty(noMoreData))
                    {
                        SDToast.showToast(noMoreData);
                    }
                }
            }
        }
    }


    /**
     * 用setVisible(view)替代
     *
     * @param view
     * @return
     */
    @Deprecated
    public static boolean show(View view)
    {
        return setVisible(view);
    }

    /**
     * 用setGone(view)替代
     *
     * @param view
     * @return
     */
    @Deprecated
    public static boolean hide(View view)
    {
        return setGone(view);
    }

    /**
     * 用removeView(view)替代
     *
     * @param view
     */
    @Deprecated
    public static void removeViewFromParent(View view)
    {
        removeView(view);
    }

    /**
     * 用dialog替代PopupWindow
     *
     * @param pop
     * @param view
     * @param marginBottom
     */
    @Deprecated
    public static void showPopTop(PopupWindow pop, View view, int marginBottom)
    {
        int[] location = getLocationOnScreen(view);
        int x = location[0] - SDViewUtil.getScreenWidth() / 2 + view.getWidth() / 2;
        int y = SDViewUtil.getScreenHeight() - location[1] + marginBottom;
        pop.showAtLocation(view, Gravity.BOTTOM, x, y);
    }

    //----------Deprecated methods end----------
}
