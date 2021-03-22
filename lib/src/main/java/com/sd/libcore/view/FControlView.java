package com.sd.libcore.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityPausedCallback;
import com.sd.lib.eventact.callback.ActivityResumedCallback;
import com.sd.lib.eventact.callback.ActivityStoppedCallback;
import com.sd.lib.eventact.observer.ActivityDestroyedObserver;
import com.sd.lib.eventact.observer.ActivityPausedObserver;
import com.sd.lib.eventact.observer.ActivityResumedObserver;
import com.sd.lib.eventact.observer.ActivityStoppedObserver;
import com.sd.lib.eventact.observer.ActivityTouchEventObserver;
import com.sd.lib.tag_view.FTagViewApi;
import com.sd.libcore.activity.FActivity;
import com.sd.libcore.activity.FStreamActivity;
import com.sd.libcore.business.holder.FActivityBusinessHolder;
import com.sd.libcore.business.holder.FBusinessHolder;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 */
public class FControlView extends FViewGroup implements
        ActivityResumedCallback,
        ActivityPausedCallback,
        ActivityStoppedCallback,
        ActivityDestroyedCallback
{
    /** 是否监听Activity的触摸事件 */
    private boolean mListenActivityTouchEvent = false;
    private FTagViewApi mTagViewApi;

    public FControlView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FTagViewApi getTagViewApi()
    {
        if (mTagViewApi == null)
            mTagViewApi = new FTagViewApi(this);
        return mTagViewApi;
    }

    /**
     * 设置是否监听Activity的触摸事件
     *
     * @param listen
     */
    public void setListenActivityTouchEvent(boolean listen)
    {
        mListenActivityTouchEvent = listen;
    }

    /**
     * 返回Activity流标识
     *
     * @return
     */
    @NonNull
    public final String getStreamTagActivity()
    {
        final Activity activity = getActivity();
        if (activity == null)
            return getStreamTagView();

        if (activity instanceof FStreamActivity)
            return ((FStreamActivity) activity).getStreamTag();

        return activity.toString();
    }

    /**
     * 返回当前View流标识
     *
     * @return
     */
    @NonNull
    public final String getStreamTagView()
    {
        final String className = getClass().getName();
        final String hashCode = Integer.toHexString(System.identityHashCode(this));
        return className + "@" + hashCode;
    }

    /**
     * 返回Http请求标识
     *
     * @return
     */
    @NonNull
    public String getHttpTag()
    {
        final String className = getClass().getName();
        final String hashCode = Integer.toHexString(System.identityHashCode(this));
        return className + "@" + hashCode;
    }

    /**
     * 显示进度框
     *
     * @param msg
     */
    public void showProgressDialog(@Nullable String msg)
    {
        final Activity activity = getActivity();
        if (activity instanceof FActivity)
            ((FActivity) activity).showProgressDialog(msg);
    }

    /**
     * 隐藏进度框
     */
    public void dismissProgressDialog()
    {
        final Activity activity = getActivity();
        if (activity instanceof FActivity)
            ((FActivity) activity).dismissProgressDialog();
    }

    @Deprecated
    public final FBusinessHolder getBusinessHolder()
    {
        return FActivityBusinessHolder.with(getActivity());
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        final Activity activity = getActivity();
        mActivityResumedObserver.register(activity);
        mActivityPausedObserver.register(activity);
        mActivityStoppedObserver.register(activity);
        mActivityDestroyedObserver.register(activity);

        if (mListenActivityTouchEvent)
            mActivityTouchEventObserver.register(activity);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mActivityResumedObserver.unregister();
        mActivityPausedObserver.unregister();
        mActivityStoppedObserver.unregister();
        mActivityDestroyedObserver.unregister();
        mActivityTouchEventObserver.unregister();
    }

    private final ActivityResumedObserver mActivityResumedObserver = new ActivityResumedObserver()
    {
        @Override
        public void onActivityResumed(@NonNull Activity activity)
        {
            FControlView.this.onActivityResumed(activity);
        }
    };

    private final ActivityPausedObserver mActivityPausedObserver = new ActivityPausedObserver()
    {
        @Override
        public void onActivityPaused(@NonNull Activity activity)
        {
            FControlView.this.onActivityPaused(activity);
        }
    };

    private final ActivityStoppedObserver mActivityStoppedObserver = new ActivityStoppedObserver()
    {
        @Override
        public void onActivityStopped(@NonNull Activity activity)
        {
            FControlView.this.onActivityStopped(activity);
        }
    };

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(@NonNull Activity activity)
        {
            FControlView.this.onActivityDestroyed(activity);
        }
    };

    private final ActivityTouchEventObserver mActivityTouchEventObserver = new ActivityTouchEventObserver()
    {
        @Override
        public boolean onActivityDispatchTouchEvent(@NonNull Activity activity, @NonNull MotionEvent event)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                if (getVisibility() == VISIBLE)
                {
                    final int x = (int) event.getRawX();
                    final int y = (int) event.getRawY();
                    final boolean touchInside = isViewUnder(x, y);

                    return onActivityTouch(touchInside);
                }
            }
            return false;
        }
    };

    @Override
    public void onActivityResumed(@NonNull Activity activity)
    {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity)
    {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity)
    {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity)
    {
    }

    /**
     * Activity触摸事件
     *
     * @param touchInside true-view内部；false-view外部
     * @return
     */
    public boolean onActivityTouch(boolean touchInside)
    {
        return false;
    }
}
