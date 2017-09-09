package com.fanwe.library.utils;

import android.animation.Animator;
import android.view.View;
import android.view.ViewTreeObserver;

import com.fanwe.library.holder.ISDObjectsHolder;
import com.fanwe.library.holder.SDObjectsHolder;
import com.fanwe.library.listener.SDIterateCallback;
import com.fanwe.library.listener.SDViewVisibilityCallback;

import java.util.Iterator;

/**
 * view的显示隐藏处理
 */
public class SDViewVisibilityHandler
{

    private View mView;
    /**
     * 显示动画
     */
    private Animator mVisibleAnimator;
    /**
     * 隐藏动画
     */
    private Animator mInvisibleAnimator;
    /**
     * true-gone，false-invisible
     */
    private boolean mIsGoneMode = true;
    /**
     * 当前view的visibility状态
     */
    private int mVisibility;
    private ISDObjectsHolder<SDViewVisibilityCallback> mCallbackHolder = new SDObjectsHolder<>();

    public SDViewVisibilityHandler(View view)
    {
        setView(view);
    }

    /**
     * 设置要处理的view
     *
     * @param view
     * @return
     */
    public SDViewVisibilityHandler setView(View view)
    {
        if (mView != view)
        {
            releaseView();
            mView = view;
            initView();
        }
        return this;
    }

    /**
     * 初始化view
     */
    private void initView()
    {
        if (mView != null)
        {
            mVisibility = mView.getVisibility();
            mView.getViewTreeObserver().addOnPreDrawListener(preDrawListener);
        }
    }

    /**
     * 释放view资源等
     */
    private void releaseView()
    {
        if (mView != null)
        {
            mView.getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
            mView = null;
        }
        reset();
    }

    private void reset()
    {
        mIsGoneMode = true;
    }

    private ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener()
    {
        @Override
        public boolean onPreDraw()
        {
            notifyVisiblityCallbackIfNeed();
            return true;
        }
    };

    /**
     * 当visibility发生变化的时候通知回调
     */
    private void notifyVisiblityCallbackIfNeed()
    {
        if (mView == null)
        {
            return;
        }

        if (mVisibility == mView.getVisibility())
        {
            return;
        }

        mVisibility = mView.getVisibility();
        notifyVisiblityCallback();
    }

    /**
     * 获得设置的view
     *
     * @return
     */
    public View getView()
    {
        return mView;
    }

    /**
     * 添加回调
     *
     * @param callback
     * @return
     */
    public SDViewVisibilityHandler addVisibilityCallback(SDViewVisibilityCallback callback)
    {
        mCallbackHolder.add(callback);
        return this;
    }

    /**
     * 移除回调
     *
     * @param callback
     * @return
     */
    public SDViewVisibilityHandler removeVisibilityCallback(SDViewVisibilityCallback callback)
    {
        mCallbackHolder.remove(callback);
        return this;
    }

    /**
     * 清空回调
     *
     * @return
     */
    public SDViewVisibilityHandler clearVisibilityCallback()
    {
        mCallbackHolder.clear();
        return this;
    }

    /**
     * 设置显示动画
     *
     * @param visibleAnimator
     * @return
     */
    public SDViewVisibilityHandler setVisibleAnimator(Animator visibleAnimator)
    {
        if (mVisibleAnimator != visibleAnimator)
        {
            if (mVisibleAnimator != null)
            {
                mVisibleAnimator.removeListener(mVisibleListener);
            }
            mVisibleAnimator = visibleAnimator;
            if (visibleAnimator != null)
            {
                visibleAnimator.addListener(mVisibleListener);
            }
        }
        return this;
    }

    /**
     * 设置隐藏动画
     *
     * @param invisibleAnimator
     * @return
     */
    public SDViewVisibilityHandler setInvisibleAnimator(Animator invisibleAnimator)
    {
        if (mInvisibleAnimator != invisibleAnimator)
        {
            if (mInvisibleAnimator != null)
            {
                mInvisibleAnimator.removeListener(mInvisibleListener);
            }
            mInvisibleAnimator = invisibleAnimator;
            if (invisibleAnimator != null)
            {
                invisibleAnimator.addListener(mInvisibleListener);
            }
        }
        return this;
    }

    /**
     * 显示view（View.VISIBLE）
     *
     * @param anim true-执行动画
     */
    public void setVisible(boolean anim)
    {
        if (mView == null)
        {
            return;
        }
        if (isVisible())
        {
            return;
        }

        if (anim)
        {
            startVisibleAnimator();
        } else
        {
            setVisibleInternal();
        }
    }

    /**
     * 开始显示动画
     */
    private void startVisibleAnimator()
    {
        if (isVisibleAnimatorStarted())
        {
            return;
        }

        if (mVisibleAnimator != null)
        {
            mVisibleAnimator.start();
        } else
        {
            setVisibleInternal();
        }
    }

    private void setVisibleInternal()
    {
        if (mView == null)
        {
            return;
        }
        mView.setVisibility(View.VISIBLE);
        notifyVisiblityCallbackIfNeed();
    }

    /**
     * 隐藏view（View.GONE）
     *
     * @param anim
     */
    public void setGone(boolean anim)
    {
        if (mView == null)
        {
            return;
        }
        if (isGone())
        {
            return;
        }

        if (anim)
        {
            startInvisibleAnimator(true);
        } else
        {
            setGoneInternal();
        }
    }

    private void setGoneInternal()
    {
        if (mView == null)
        {
            return;
        }
        mView.setVisibility(View.GONE);
        notifyVisiblityCallbackIfNeed();
    }

    /**
     * 开始隐藏动画
     *
     * @param isGoneMode
     */
    private void startInvisibleAnimator(boolean isGoneMode)
    {
        if (isInvisibleAnimatorStarted())
        {
            return;
        }

        this.mIsGoneMode = isGoneMode;
        if (mInvisibleAnimator != null)
        {
            cancelVisibleAnimator();
            mInvisibleAnimator.start();
        } else
        {
            if (isGoneMode)
            {
                setGoneInternal();
            } else
            {
                setInvisibleInternal();
            }
        }
    }

    /**
     * 隐藏view（View.INVISIBLE）
     *
     * @param anim
     */
    public void setInvisible(boolean anim)
    {
        if (mView == null)
        {
            return;
        }
        if (isInvisible())
        {
            return;
        }

        if (anim)
        {
            startInvisibleAnimator(false);
        } else
        {
            setInvisibleInternal();
        }
    }

    private void setInvisibleInternal()
    {
        if (mView == null)
        {
            return;
        }
        mView.setVisibility(View.INVISIBLE);
        notifyVisiblityCallbackIfNeed();
    }

    /**
     * 显示动画是否已经启动
     */
    private boolean isVisibleAnimatorStarted()
    {
        return mVisibleAnimator != null && mVisibleAnimator.isStarted();
    }

    /**
     * 隐藏动画是否已经启动
     *
     * @return
     */
    private boolean isInvisibleAnimatorStarted()
    {
        return mInvisibleAnimator != null && mInvisibleAnimator.isStarted();
    }

    /**
     * 取消显示动画
     */
    private void cancelVisibleAnimator()
    {
        if (isVisibleAnimatorStarted())
        {
            if (mVisibleAnimator != null)
            {
                mVisibleAnimator.cancel();
            }
        }
    }

    /**
     * 取消隐藏动画
     */
    private void cancelInvisibleAnimator()
    {
        if (isInvisibleAnimatorStarted())
        {
            if (mInvisibleAnimator != null)
            {
                mInvisibleAnimator.cancel();
            }
        }
    }

    /**
     * 显示监听
     */
    private Animator.AnimatorListener mVisibleListener = new Animator.AnimatorListener()
    {
        @Override
        public void onAnimationStart(Animator animation)
        {
            setVisibleInternal();
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
        }

        @Override
        public void onAnimationCancel(Animator animation)
        {
        }

        @Override
        public void onAnimationRepeat(Animator animation)
        {
        }
    };

    /**
     * 隐藏监听
     */
    private Animator.AnimatorListener mInvisibleListener = new Animator.AnimatorListener()
    {
        @Override
        public void onAnimationStart(Animator animation)
        {
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
            if (mIsGoneMode)
            {
                setGoneInternal();
            } else
            {
                setInvisibleInternal();
            }
            SDViewUtil.resetView(mView);
        }

        @Override
        public void onAnimationCancel(Animator animation)
        {
            if (mIsGoneMode)
            {
                setGoneInternal();
            } else
            {
                setInvisibleInternal();
            }
            SDViewUtil.resetView(mView);
        }

        @Override
        public void onAnimationRepeat(Animator animation)
        {
        }
    };

    /**
     * 通知回调
     */
    public final void notifyVisiblityCallback()
    {
        if (mView == null)
        {
            return;
        }

        mCallbackHolder.foreach(new SDIterateCallback<SDViewVisibilityCallback>()
        {
            @Override
            public boolean next(int i, SDViewVisibilityCallback item, Iterator<SDViewVisibilityCallback> it)
            {
                item.onViewVisibilityChanged(mView, mView.getVisibility());
                return false;
            }
        });
    }

    /**
     * view的状态是否处于View.VISIBLE
     *
     * @return
     */
    public boolean isVisible()
    {
        if (mView == null)
        {
            return false;
        }
        return mView.getVisibility() == View.VISIBLE;
    }

    /**
     * view的状态是否处于View.GONE
     *
     * @return
     */
    public boolean isGone()
    {
        if (mView == null)
        {
            return false;
        }
        return mView.getVisibility() == View.GONE;
    }

    /**
     * view的状态是否处于View.INVISIBLE
     *
     * @return
     */
    public boolean isInvisible()
    {
        if (mView == null)
        {
            return false;
        }
        return mView.getVisibility() == View.INVISIBLE;
    }

    /**
     * 在View.VISIBLE和View.GONE之前切换
     *
     * @param anim
     */
    public void toggleVisibleOrGone(boolean anim)
    {
        if (mView == null)
        {
            return;
        }

        if (isVisible())
        {
            setGone(anim);
        } else
        {
            setVisible(anim);
        }
    }

    /**
     * 在View.VISIBLE和View.INVISIBLE之前切换
     *
     * @param anim
     */
    public void toggleVisibleOrInvisible(boolean anim)
    {
        if (mView == null)
        {
            return;
        }

        if (isVisible())
        {
            setInvisible(anim);
        } else
        {
            setVisible(anim);
        }
    }

    public Animator getVisibleAnimator()
    {
        return mVisibleAnimator;
    }

    public Animator getInvisibleAnimator()
    {
        return mInvisibleAnimator;
    }

}
