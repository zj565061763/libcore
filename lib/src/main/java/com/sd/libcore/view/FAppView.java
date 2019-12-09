package com.sd.libcore.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.activity.FActivity;
import com.sd.libcore.activity.FStreamActivity;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用{@link FControlView}替代
 */
@Deprecated
public class FAppView extends FrameLayout implements FStream, View.OnClickListener
{
    /**
     * 设置是否消费掉触摸事件，true-事件不会透过view继续往下传递
     */
    private boolean mConsumeTouchEvent = false;
    private WeakReference<ViewGroup> mContainer;

    private boolean mHasOnLayout = false;
    private List<Runnable> mListLayoutRunnable;

    private int[] mLocationOnScreen;

    public FAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        baseInit();
    }

    private void baseInit()
    {
        FStreamManager.getInstance().bindStream(this, this);

        final int layoutId = onCreateContentView();
        if (layoutId != 0)
            setContentView(layoutId);

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
    public FAppView setContainer(View container)
    {
        if (container == null)
        {
            mContainer = null;
        } else
        {
            if (container instanceof ViewGroup)
                mContainer = new WeakReference<>((ViewGroup) container);
            else
                throw new IllegalArgumentException("container must be instance of ViewGroup");
        }
        return this;
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

    public Activity getActivity()
    {
        final Context context = getContext();
        return context instanceof Activity ? (Activity) context : null;
    }

    public FActivity getFActivity()
    {
        final Activity activity = getActivity();
        return activity instanceof FActivity ? (FActivity) activity : null;
    }

    /**
     * 把View添加到设置的容器{@link #setContainer(View)}
     *
     * @param replace true-父容器仅保留当前View对象在容器中
     */
    public final void attach(boolean replace)
    {
        final ViewGroup viewGroup = getContainer();
        if (viewGroup == null)
            return;

        if (getParent() != viewGroup)
        {
            if (replace)
                viewGroup.removeAllViews();

            detach();
            viewGroup.addView(this);
        } else
        {
            if (replace)
            {
                final int count = viewGroup.getChildCount();
                for (int i = count - 1; i >= 0; i--)
                {
                    final View item = viewGroup.getChildAt(i);
                    if (item != this)
                        viewGroup.removeView(item);
                }
            }
        }
    }

    /**
     * 把当前View从父容器上移除
     */
    public final void detach()
    {
        if (getParent() == null)
            return;
        try
        {
            ((ViewGroup) getParent()).removeView(this);
        } catch (Exception e)
        {
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

    /**
     * 返回View在屏幕上的坐标
     *
     * @return
     */
    public final int[] getLocationOnScreen()
    {
        if (mLocationOnScreen == null)
            mLocationOnScreen = new int[]{0, 0};

        getLocationOnScreen(mLocationOnScreen);
        return mLocationOnScreen;
    }

    /**
     * view是否位于指定的坐标之下
     *
     * @param x 屏幕x坐标
     * @param y 屏幕y坐标
     * @return
     */
    public final boolean isViewUnder(int x, int y)
    {
        final int[] location = getLocationOnScreen();
        final int left = location[0];
        final int top = location[1];
        final int right = left + getWidth();
        final int bottom = top + getHeight();

        return left < right && top < bottom
                && x >= left && x < right && y >= top && y < bottom;
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
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mHasOnLayout = false;
        if (mListLayoutRunnable != null)
        {
            mListLayoutRunnable.clear();
            mListLayoutRunnable = null;
        }
    }

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz)
    {
        return getStreamTagActivity();
    }

    public final String getStreamTagActivity()
    {
        final Activity activity = getActivity();
        if (activity == null)
            return getStreamTagView();

        if (activity instanceof FStreamActivity)
            return ((FStreamActivity) activity).getStreamTag();

        return activity.toString();
    }

    public final String getStreamTagView()
    {
        final String className = getClass().getName();
        final String hashCode = Integer.toHexString(System.identityHashCode(this));
        return className + "@" + hashCode;
    }

    public void showProgressDialog(String msg)
    {
        if (getFActivity() != null)
        {
            getFActivity().showProgressDialog(msg);
        }
    }

    public void dismissProgressDialog()
    {
        if (getFActivity() != null)
        {
            getFActivity().dismissProgressDialog();
        }
    }
}
