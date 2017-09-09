package com.fanwe.library.view;

import android.animation.Animator;
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

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.listener.SDActivityDispatchKeyEventCallback;
import com.fanwe.library.listener.SDActivityDispatchTouchEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.SDViewVisibilityHandler;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class SDAppView extends FrameLayout implements
        View.OnClickListener,
        SDEventObserver,
        SDActivityDispatchKeyEventCallback,
        SDActivityDispatchTouchEventCallback,
        SDActivityLifecycleCallback,
        ISDViewContainer
{

    private SDViewVisibilityHandler mVisibilityHandler;

    /**
     * 是否需要注册EventBus事件
     */
    private boolean mNeedRegisterEventBus = true;
    /**
     * 是否已经注册EventBus事件
     */
    private boolean mHasRegisterEventBus = false;
    /**
     * 是否需要注册Activity事件
     */
    private boolean mNeedRegisterActivityEvent = true;
    /**
     * 是否已经注册Activity事件
     */
    private boolean mHasRegisterActivityEvent = false;
    /**
     * 设置是否消费掉触摸事件，true-事件不会透过view继续往下传递
     */
    private boolean mConsumeTouchEvent = false;

    private boolean mHasOnLayout = false;
    private List<Runnable> mListLayoutRunnable;

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
     * 设置是否需要注册EventBus事件
     *
     * @param needRegisterEventBus
     */
    public void setNeedRegisterEventBus(boolean needRegisterEventBus)
    {
        mNeedRegisterEventBus = needRegisterEventBus;
    }

    /**
     * 设置是否需要注册Activity事件
     *
     * @param needRegisterActivityEvent
     */
    public void setNeedRegisterActivityEvent(boolean needRegisterActivityEvent)
    {
        mNeedRegisterActivityEvent = needRegisterActivityEvent;
    }

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    public SDAppView setWidth(int width)
    {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
        {
            params = generateDefaultLayoutParams();
        }
        if (params.width != width)
        {
            params.width = width;
            setLayoutParams(params);
        }
        return this;
    }

    /**
     * 设置高度
     *
     * @param height
     * @return
     */
    public SDAppView setHeight(int height)
    {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
        {
            params = generateDefaultLayoutParams();
        }
        if (params.height != height)
        {
            params.height = height;
            setLayoutParams(params);
        }
        return this;
    }

    /**
     * 设置是否消费掉触摸事件
     *
     * @param consumeTouchEvent true-消费掉事件，事件不会透过view继续往下传递
     * @return
     */
    public SDAppView setConsumeTouchEvent(boolean consumeTouchEvent)
    {
        this.mConsumeTouchEvent = consumeTouchEvent;
        return this;
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void addVisibilityCallback(SDViewVisibilityCallback callback)
    {
        getVisibilityHandler().addVisibilityCallback(callback);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void removeVisibilityCallback(SDViewVisibilityCallback callback)
    {
        getVisibilityHandler().removeVisibilityCallback(callback);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void clearVisibilityCallback()
    {
        getVisibilityHandler().clearVisibilityCallback();
    }

    /**
     * @return
     * @see SDViewVisibilityHandler
     */
    public final SDViewVisibilityHandler getVisibilityHandler()
    {
        if (mVisibilityHandler == null)
        {
            mVisibilityHandler = new SDViewVisibilityHandler(this);
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

    public SDBaseActivity getSDBaseActivity()
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
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public SDAppView setVisibleAnimator(Animator animator)
    {
        getVisibilityHandler().setVisibleAnimator(animator);
        return this;
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public SDAppView setInvisibleAnimator(Animator animator)
    {
        getVisibilityHandler().setInvisibleAnimator(animator);
        return this;
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
     * 用findViewById(id)替代
     */
    @Deprecated
    public <V extends View> V find(int id)
    {
        View view = findViewById(id);
        return (V) view;
    }

    /**
     * 把自己从父布局移除
     */
    public void removeSelf()
    {
        SDViewUtil.removeView(this);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void setVisible()
    {
        getVisibilityHandler().setVisible(false);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void setVisible(boolean anim)
    {
        getVisibilityHandler().setVisible(anim);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void setGone()
    {
        getVisibilityHandler().setGone(false);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void setGone(boolean anim)
    {
        getVisibilityHandler().setGone(anim);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void setInvisible()
    {
        getVisibilityHandler().setInvisible(false);
    }

    /**
     * 用getVisibilityHandler()处理
     */
    @Deprecated
    public void setInvisible(boolean anim)
    {
        getVisibilityHandler().setInvisible(anim);
    }

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
    }

    @Override
    public void onClick(View v)
    {
    }

    public boolean isVisible()
    {
        return View.VISIBLE == getVisibility();
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
                mListLayoutRunnable = new ArrayList<>();
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
        if (isVisible() && getParent() != null)
        {
            switch (ev.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    if (SDViewUtil.isViewUnder(this, ev))
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
        if (isVisible() && getParent() != null)
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
        registerEventBus();
        registerActivityEvent();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        mHasOnLayout = false;
        if (mListLayoutRunnable != null)
        {
            mListLayoutRunnable.clear();
            mListLayoutRunnable = null;
        }

        unregisterEventBus();
        unregisterActivityEvent();
        super.onDetachedFromWindow();
    }

    /**
     * 注册activity事件监听
     */
    public final void registerActivityEvent()
    {
        if (mNeedRegisterActivityEvent)
        {
            if (getSDBaseActivity() != null)
            {
                if (!mHasRegisterActivityEvent)
                {
                    getSDBaseActivity().registerAppView(this);
                    mHasRegisterActivityEvent = true;
                }
            }
        }
    }

    /**
     * 取消注册activity事件监听
     */
    public final void unregisterActivityEvent()
    {
        if (getSDBaseActivity() != null)
        {
            if (mHasRegisterActivityEvent)
            {
                getSDBaseActivity().unregisterAppView(this);
                mHasRegisterActivityEvent = false;
            }
        }
    }

    /**
     * 注册EventBus
     */
    public final void registerEventBus()
    {
        if (mNeedRegisterEventBus)
        {
            if (!mHasRegisterEventBus)
            {
                EventBus.getDefault().register(this);
                mHasRegisterEventBus = true;
            }
        }
    }

    /**
     * 取消注册EventBus
     */
    public final void unregisterEventBus()
    {
        if (mHasRegisterEventBus)
        {
            EventBus.getDefault().unregister(this);
            mHasRegisterEventBus = false;
        }
    }

    public void showProgressDialog(String msg)
    {
        if (getSDBaseActivity() != null)
        {
            getSDBaseActivity().showProgressDialog(msg);
        }
    }

    public void dismissProgressDialog()
    {
        if (getSDBaseActivity() != null)
        {
            getSDBaseActivity().dismissProgressDialog();
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
        unregisterEventBus();
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
    public void addView(int parentId, View view)
    {
        SDViewUtil.addView((ViewGroup) findViewById(parentId), view);
    }

    @Override
    public View removeView(int viewId)
    {
        View view = findViewById(viewId);
        removeView(view);
        return view;
    }

    @Override
    public void replaceView(int parentId, View child)
    {
        SDViewUtil.replaceView((ViewGroup) findViewById(parentId), child);
    }

    @Override
    public void replaceView(ViewGroup parent, View child)
    {
        SDViewUtil.replaceView(parent, child);
    }

    @Override
    public void toggleView(int parentId, View child)
    {
        SDViewUtil.toggleView((ViewGroup) findViewById(parentId), child);
    }

    @Override
    public void toggleView(ViewGroup parent, View child)
    {
        SDViewUtil.toggleView(parent, child);
    }
}
