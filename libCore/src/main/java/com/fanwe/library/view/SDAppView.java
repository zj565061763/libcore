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
import android.widget.FrameLayout;

import com.fanwe.lib.event.FEventObserver;
import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.lib.utils.extend.FViewVisibilityHandler;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.listener.SDActivityKeyEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.listener.SDActivityTouchEventCallback;

import java.lang.ref.WeakReference;
import java.util.Iterator;
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

    private FViewVisibilityHandler mVisibilityHandler;
    /**
     * 设置是否消费掉触摸事件，true-事件不会透过view继续往下传递
     */
    private boolean mConsumeTouchEvent = false;

    private WeakReference<ViewGroup> mContainer;

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
     * @return
     */
    public final SDAppView setContainer(ViewGroup container)
    {
        if (container == null)
        {
            mContainer = null;
        } else
        {
            mContainer = new WeakReference<>(container);
        }
        return this;
    }

    /**
     * 返回设置的父容器
     *
     * @return
     */
    public final ViewGroup getContainer()
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
     * 设置view的attach状态
     *
     * @param attach true-将view添加到设置的容器{@link #setContainer(ViewGroup)}，false-将view从父容器移除
     */
    public void attach(boolean attach)
    {
        if (attach)
        {
            ViewGroup container = getContainer();
            if (container != null)
            {
                if (container != getParent())
                {
                    FViewUtil.removeView(this);
                    container.addView(this);
                }
            }
        } else
        {
            FViewUtil.removeView(this);
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
        runLayoutRunnableIfNeed();
    }

    private void runLayoutRunnableIfNeed()
    {
        if (mListLayoutRunnable == null || mListLayoutRunnable.isEmpty())
        {
            return;
        }

        Iterator<Runnable> it = mListLayoutRunnable.iterator();
        while (it.hasNext())
        {
            Runnable r = it.next();
            r.run();
            it.remove();
        }
        mListLayoutRunnable = null;
    }

    /**
     * 如果View已经触发过onLayout方法，则Runnable对象在调用此方法的时候直接触发<br>
     * 如果View还没触发过onLayout方法，则会在第一次onLayout方法触发的时候触发Runnable对象
     *
     * @param r
     * @return true-直接执行
     */
    public boolean postLayoutRunnable(Runnable r)
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
     * @return
     */
    public boolean removeLayoutRunnable(Runnable r)
    {
        if (mListLayoutRunnable == null || mListLayoutRunnable.isEmpty())
        {
            return false;
        }
        boolean result = mListLayoutRunnable.remove(r);
        if (mListLayoutRunnable.isEmpty())
        {
            mListLayoutRunnable = null;
        }
        return result;
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
        if (getBaseActivity() != null)
        {
            getBaseActivity().registerAppView(this);
        }
    }

    /**
     * 取消注册activity事件监听
     */
    public final void unregisterActivityEvent()
    {
        if (getBaseActivity() != null)
        {
            getBaseActivity().unregisterAppView(this);
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
        FEventObserver.unregisterAll(this);
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
