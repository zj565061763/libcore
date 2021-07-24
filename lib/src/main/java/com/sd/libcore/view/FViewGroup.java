package com.sd.libcore.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class FViewGroup extends FrameLayout implements View.OnClickListener {
    /** 置是否消费掉触摸事件，true-事件不会透过view继续往下传递 */
    private boolean mConsumeTouchEvent = false;
    private WeakReference<ViewGroup> mContainer;
    private View mContentView;

    /** {@link #onCreate()}是否已经通知 */
    private volatile boolean mHasOnCreate = false;

    public FViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 通知{@link #onCreate()}
     */
    private void notifyOnCreate() {
        if (mHasOnCreate) {
            return;
        }
        if (isAttached()) {
            mHasOnCreate = true;
            onCreate();
        }
    }

    /**
     * View第一次被添加到UI上面的时候回调
     */
    protected void onCreate() {
    }

    /**
     * 返回内容View
     *
     * @return
     */
    @Nullable
    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置内容View布局
     *
     * @param layoutId 布局id
     */
    public void setContentView(int layoutId) {
        final View view = layoutId == 0 ?
                null :
                LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        setContentView(view);
    }

    /**
     * 设置内容View
     *
     * @param contentView
     */
    public void setContentView(@Nullable View contentView) {
        removeAllViews();
        mContentView = contentView;

        if (contentView != null) {
            addView(contentView);
        }

        onContentViewChanged(contentView);
    }

    /**
     * 内容view发生变化
     *
     * @param contentView
     */
    protected void onContentViewChanged(@Nullable View contentView) {
    }

    /**
     * 设置是否消费掉触摸事件
     *
     * @param consumeTouchEvent true-消费掉事件，事件不会透过view继续往下传递
     */
    public void setConsumeTouchEvent(boolean consumeTouchEvent) {
        mConsumeTouchEvent = consumeTouchEvent;
    }

    /**
     * 设置父容器
     *
     * @param container
     * @return
     */
    public FViewGroup setContainer(@Nullable View container) {
        if (container == null) {
            mContainer = null;
        } else {
            if (container instanceof ViewGroup) {
                mContainer = new WeakReference<>((ViewGroup) container);
            } else {
                throw new IllegalArgumentException("container must be instance of ViewGroup");
            }
        }
        return this;
    }

    /**
     * 返回设置的父容器
     *
     * @return
     */
    @Nullable
    public ViewGroup getContainer() {
        return mContainer == null ? null : mContainer.get();
    }

    @Nullable
    public Activity getActivity() {
        final Context context = getContext();
        return context instanceof Activity ? (Activity) context : null;
    }

    /**
     * 把View添加到设置的容器{@link #setContainer(View)}
     *
     * @param replace true-父容器仅保留当前View对象在容器中
     */
    public final void attach(boolean replace) {
        final ViewGroup viewGroup = getContainer();
        if (viewGroup == null) {
            return;
        }

        if (getParent() != viewGroup) {
            if (replace) {
                viewGroup.removeAllViews();
            }

            detach();
            viewGroup.addView(this);
            return;
        }

        if (replace) {
            final int count = viewGroup.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View item = viewGroup.getChildAt(i);
                if (item != this) {
                    viewGroup.removeView(item);
                }
            }
        }
    }

    /**
     * 把当前View从父容器上移除
     */
    public void detach() {
        final Activity activity = getActivity();
        if (activity != null && activity.isFinishing()) {
            return;
        }

        final ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            try {
                ((ViewGroup) parent).removeView(this);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 是否已经被添加到ui上面
     *
     * @return
     */
    public final boolean isAttached() {
        if (Build.VERSION.SDK_INT >= 19) {
            return isAttachedToWindow();
        } else {
            return getWindowToken() != null;
        }
    }

    private int[] mTempLocation;

    /**
     * view是否在某个坐标下面
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isViewUnder(int x, int y) {
        if (mTempLocation == null) {
            mTempLocation = new int[2];
        }

        getLocationOnScreen(mTempLocation);

        final int left = mTempLocation[0];
        final int top = mTempLocation[1];
        final int right = left + getWidth();
        final int bottom = top + getHeight();

        return left < right && top < bottom
                && x >= left && x < right && y >= top && y < bottom;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mConsumeTouchEvent) {
            super.onTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (mContentView == child) {
            mContentView = null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mHasOnCreate) {
            post(new Runnable() {
                @Override
                public void run() {
                    notifyOnCreate();
                }
            });
        }
    }
}
