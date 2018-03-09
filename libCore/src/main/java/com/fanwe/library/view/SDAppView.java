package com.fanwe.library.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.lib.utils.extend.FViewVisibilityHandler;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.listener.SDActivityKeyEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.listener.SDActivityTouchEventCallback;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class SDAppView extends FrameLayout implements
        View.OnClickListener,
        SDActivityKeyEventCallback,
        SDActivityTouchEventCallback,
        SDActivityLifecycleCallback,
        ISDViewContainer
{
    public SDAppView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        baseInit();
    }

    public SDAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        baseInit();
    }

    public SDAppView(Context context)
    {
        super(context);
        baseInit();
    }

    /**
     * 设置是否消费掉触摸事件，true-事件不会透过view继续往下传递
     */
    private boolean mConsumeTouchEvent = false;
    private WeakReference<ViewGroup> mContainer;
    private FViewVisibilityHandler mVisibilityHandler;

    private boolean mHasOnLayout = false;
    private List<Runnable> mListLayoutRunnable;

    private void baseInit()
    {
        int layoutId = onCreateContentView();
        if (layoutId != 0)
        {
            setContentView(layoutId);
        }

        onBaseInit();
    }

    /**
     * 可重写此方法返回布局id
     *
     * @return
     */
    protected int onCreateContentView()
    {
        return 0;
    }

    /**
     * 基类构造方法调用的初始化方法<br>
     * 如果子类在此方法内访问子类定义属性时候直接new的属性，如：private String value = "value"，则value的值将为null
     */
    protected void onBaseInit()
    {
    }

    /**
     * 设置是否消费掉触摸事件
     *
     * @param consumeTouchEvent true-消费掉事件，事件不会透过view继续往下传递
     */
    public void setConsumeTouchEvent(boolean consumeTouchEvent)
    {
        mConsumeTouchEvent = consumeTouchEvent;
    }

    /**
     * 设置父容器
     *
     * @param container
     */
    public void setContainer(View container)
    {
        if (container == null)
        {
            mContainer = null;
        } else
        {
            if (container instanceof ViewGroup)
            {
                mContainer = new WeakReference<>((ViewGroup) container);
            } else
            {
                throw new IllegalArgumentException("container must be instance of ViewGroup");
            }
        }
    }

    /**
     * 返回设置的父容器
     *
     * @return
     */
    public ViewGroup getContainer()
    {
        return mContainer == null ? null : mContainer.get();
    }

    public final FViewVisibilityHandler getVisibilityHandler()
    {
        if (mVisibilityHandler == null)
        {
            mVisibilityHandler = new FViewVisibilityHandler(this);
        }
        return mVisibilityHandler;
    }

    public Activity getActivity()
    {
        Context context = getContext();
        if (context instanceof Activity)
        {
            return (Activity) context;
        } else
        {
            return null;
        }
    }

    public SDBaseActivity getBaseActivity()
    {
        Activity activity = getActivity();
        if (activity instanceof SDBaseActivity)
        {
            return (SDBaseActivity) activity;
        } else
        {
            return null;
        }
    }

    /**
     * 设置布局
     *
     * @param layoutId 布局id
     */
    public void setContentView(int layoutId)
    {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
    }

    public void setContentView(View contentView)
    {
        removeAllViews();
        addView(contentView);
    }

    public void setContentView(View contentView, ViewGroup.LayoutParams params)
    {
        removeAllViews();
        addView(contentView, params);
    }

    /**
     * 把View添加到设置的容器{@link #setContainer(View)}
     *
     * @param replace true-在添加之前会先移除容器的所有子View
     */
    public final void attachToContainer(boolean replace)
    {
        final ViewGroup container = getContainer();
        if (container == null)
        {
            return;
        }

        final ViewParent parent = getParent();
        if (parent != container)
        {
            if (replace)
            {
                container.removeAllViews();
            }
            detach();
            container.addView(this);
        }
    }

    /**
     * 把当前View从父容器上移除
     */
    public final void detach()
    {
        final ViewParent parent = getParent();
        if (parent instanceof ViewGroup)
        {
            ((ViewGroup) parent).removeView(this);
        }
    }

    @Override
    public void onClick(View v)
    {
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        mHasOnLayout = true;
        if (mListLayoutRunnable != null)
        {
            for (Runnable item : mListLayoutRunnable)
            {
                item.run();
            }
            mListLayoutRunnable.clear();
            mListLayoutRunnable = null;
        }
    }

    /**
     * 如果View已经触发过onLayout方法，则Runnable对象在调用此方法的时候直接触发<br>
     * 如果View还没触发过onLayout方法，则会在第一次onLayout方法触发的时候触发Runnable对象
     *
     * @param r
     * @return true-直接执行
     */
    public final boolean postLayoutRunnable(Runnable r)
    {
        if (mHasOnLayout)
        {
            r.run();
            return true;
        } else
        {
            if (mListLayoutRunnable == null)
            {
                mListLayoutRunnable = new CopyOnWriteArrayList<>();
            }
            mListLayoutRunnable.add(r);
            return false;
        }
    }

    /**
     * 移除Runnable
     *
     * @param r
     */
    public final void removeLayoutRunnable(Runnable r)
    {
        if (mListLayoutRunnable == null)
        {
            return;
        }

        mListLayoutRunnable.remove(r);
        if (mListLayoutRunnable.isEmpty())
        {
            mListLayoutRunnable = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mConsumeTouchEvent)
        {
            super.onTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(Activity activity, MotionEvent ev)
    {
        if (View.VISIBLE == getVisibility() && getParent() != null)
        {
            switch (ev.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    if (FViewUtil.isViewUnder(this, ev))
                    {
                        return onTouchDownInside(ev);
                    } else
                    {
                        return onTouchDownOutside(ev);
                    }
                default:
                    break;
            }
        }
        return false;
    }

    protected boolean onTouchDownOutside(MotionEvent ev)
    {
        return false;
    }

    protected boolean onTouchDownInside(MotionEvent ev)
    {
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(Activity activity, KeyEvent event)
    {
        if (View.VISIBLE == getVisibility() && getParent() != null)
        {
            switch (event.getAction())
            {
                case KeyEvent.ACTION_DOWN:
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    {
                        return onBackPressed();
                    }
                    break;

                default:
                    break;
            }
        }
        return false;
    }

    public boolean onBackPressed()
    {
        return false;
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        registerActivityEvent();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mHasOnLayout = false;
        if (mListLayoutRunnable != null)
        {
            mListLayoutRunnable.clear();
            mListLayoutRunnable = null;
        }
        unregisterActivityEvent();
    }

    /**
     * 注册activity事件监听
     */
    public final void registerActivityEvent()
    {
        final SDBaseActivity activity = getBaseActivity();
        if (activity != null)
        {
            activity.getLifecycleCallbackHolder().add(this);
            activity.getTouchEventCallbackHolder().add(this);
            activity.getKeyEventCallbackHolder().add(this);
        }
    }

    /**
     * 取消注册activity事件监听
     */
    public final void unregisterActivityEvent()
    {
        final SDBaseActivity activity = getBaseActivity();
        if (activity != null)
        {
            activity.getLifecycleCallbackHolder().remove(this);
            activity.getTouchEventCallbackHolder().remove(this);
            activity.getKeyEventCallbackHolder().remove(this);
        }
    }

    public void showProgressDialog(String msg)
    {
        if (getBaseActivity() != null)
        {
            getBaseActivity().showProgressDialog(msg);
        }
    }

    public void dismissProgressDialog()
    {
        if (getBaseActivity() != null)
        {
            getBaseActivity().dismissProgressDialog();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {

    }

    @Override
    public void onActivityPaused(Activity activity)
    {

    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {

    }

    @Override
    public void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
    {

    }

    @Override
    public void addView(int parentId, View child)
    {
        FViewUtil.addView((ViewGroup) findViewById(parentId), child);
    }

    @Override
    public void replaceView(int parentId, View child)
    {
        FViewUtil.replaceView((ViewGroup) findViewById(parentId), child);
    }

    @Override
    public void toggleView(int parentId, View child)
    {
        FViewUtil.toggleView((ViewGroup) findViewById(parentId), child);
    }
}
