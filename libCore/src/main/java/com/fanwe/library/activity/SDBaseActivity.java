package com.fanwe.library.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.holder.ISDObjectsHolder;
import com.fanwe.library.holder.SDObjectsHolder;
import com.fanwe.library.listener.SDActivityDispatchKeyEventCallback;
import com.fanwe.library.listener.SDActivityDispatchTouchEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.listener.SDIterateCallback;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.ISDViewContainer;
import com.fanwe.library.view.SDAppView;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;

import java.util.Iterator;

public abstract class SDBaseActivity extends AppCompatActivity implements
        SDEventObserver,
        OnClickListener,
        ISDViewContainer
{
    private SDFragmentManager mFragmentManager;

    private ProgressDialog mProgressDialog;

    private static boolean sIsBackground = false;
    private static long sBackgroundTime;

    private boolean mIsResume;

    private ISDObjectsHolder<SDActivityLifecycleCallback> mActivityLifecycleCallbackHolder = new SDObjectsHolder<>();
    private ISDObjectsHolder<SDActivityDispatchTouchEventCallback> mDispatchTouchEventCallbackHolder = new SDObjectsHolder<>();
    private ISDObjectsHolder<SDActivityDispatchKeyEventCallback> mDispatchKeyEventCallbackHolder = new SDObjectsHolder<>();

    /**
     * app是否处于后台
     *
     * @return true-处于后台
     */
    public static boolean isBackground()
    {
        return sIsBackground;
    }

    public Activity getActivity()
    {
        return this;
    }

    /**
     * 获得app进入后台的时间点
     *
     * @return
     */
    public static long getBackgroundTime()
    {
        return sBackgroundTime;
    }

    public ISDObjectsHolder<SDActivityLifecycleCallback> getActivityLifecycleCallbackHolder()
    {
        return mActivityLifecycleCallbackHolder;
    }

    public ISDObjectsHolder<SDActivityDispatchTouchEventCallback> getDispatchTouchEventCallbackHolder()
    {
        return mDispatchTouchEventCallbackHolder;
    }

    public ISDObjectsHolder<SDActivityDispatchKeyEventCallback> getDispatchKeyEventCallbackHolder()
    {
        return mDispatchKeyEventCallbackHolder;
    }

    /**
     * activity是否处于resume状态
     *
     * @return
     */
    public boolean isResume()
    {
        return mIsResume;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SDActivityManager.getInstance().onCreate(this);
        SDEventManager.register(this);
        afterOnCreater(savedInstanceState);

        notifyOnCreate(savedInstanceState);
    }

    private void afterOnCreater(Bundle savedInstanceState)
    {
        int layoutId = onCreateContentView();
        if (layoutId != 0)
        {
            setContentView(layoutId);
        }

        init(savedInstanceState);
    }

    /**
     * 返回布局activity布局id，基类调用的顺序：onCreateContentView()-setContentView()-init()
     *
     * @return
     */
    protected int onCreateContentView()
    {
        return 0;
    }

    /**
     * 重写此方法初始化，如果没有重写onCreateContentView()方法，则要手动调用setContentView()设置activity布局;
     *
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 用findViewById(id)
     */
    @Deprecated
    public <V extends View> V find(int id)
    {
        View view = findViewById(id);
        return (V) view;
    }

    @Override
    public void setContentView(int layoutResID)
    {
        View contentView = getLayoutInflater().inflate(layoutResID, (ViewGroup) findViewById(android.R.id.content), false);
        setContentView(contentView);
    }

    @Override
    public void setContentView(View view)
    {
        View contentView = addTitleView(view);
        contentView.setFitsSystemWindows(true);
        super.setContentView(contentView);
    }

    /**
     * 为contentView添加titleView
     *
     * @param contentView
     * @return
     */
    private View addTitleView(View contentView)
    {
        View viewFinal = contentView;

        int resId = onCreateTitleViewResId();
        if (resId != 0)
        {
            View titleView = LayoutInflater.from(this).inflate(resId, (ViewGroup) findViewById(android.R.id.content), false);

            LinearLayout linAll = new LinearLayout(this);
            linAll.setOrientation(LinearLayout.VERTICAL);
            linAll.addView(titleView);
            linAll.addView(contentView);
            viewFinal = linAll;
        }
        return viewFinal;
    }

    /**
     * 返回标题栏布局id
     *
     * @return
     */
    protected int onCreateTitleViewResId()
    {
        return 0;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        notifyOnStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mIsResume = true;
        SDActivityManager.getInstance().onResume(this);
        if (sIsBackground)
        {
            sIsBackground = false;
            onResumeFromBackground();
            sBackgroundTime = 0;
        }

        notifyOnResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mIsResume = false;
        notifyOnPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mIsResume = false;
        if (!sIsBackground)
        {
            if (SDPackageUtil.isBackground())
            {
                sIsBackground = true;
                sBackgroundTime = System.currentTimeMillis();
                onBackground();
            }
        }

        notifyOnStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SDActivityManager.getInstance().onDestroy(this);
        SDEventManager.unregister(this);
        dismissProgressDialog();

        notifyOnDestroy();
    }


    @Override
    public void finish()
    {
        SDActivityManager.getInstance().onDestroy(this);
        super.finish();
    }

    /**
     * app进入后台时候回调
     */
    protected void onBackground()
    {

    }

    /**
     * app从后台回到前台回调
     */
    protected void onResumeFromBackground()
    {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        try
        {
            super.onSaveInstanceState(outState);
            if (outState != null)
            {
                outState.remove("android:support:fragments");
            }
            notifyOnSaveInstanceState(outState);
        } catch (Exception e)
        {
            onSaveInstanceStateException(e);
        }
    }

    protected void onSaveInstanceStateException(Exception e)
    {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        try
        {
            super.onRestoreInstanceState(savedInstanceState);
            notifyOnRestoreInstanceState(savedInstanceState);
        } catch (Exception e)
        {
            onRestoreInstanceStateException(e);
        }
    }

    protected void onRestoreInstanceStateException(Exception e)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        notifyOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev)
    {
        boolean notifyResult = mDispatchTouchEventCallbackHolder.foreachReverse(new SDIterateCallback<SDActivityDispatchTouchEventCallback>()
        {
            @Override
            public boolean next(int i, SDActivityDispatchTouchEventCallback item, Iterator<SDActivityDispatchTouchEventCallback> it)
            {
                return item.dispatchTouchEvent(SDBaseActivity.this, ev);
            }
        });

        if (notifyResult)
        {
            return true;
        } else
        {
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    {
        boolean notifyResult = mDispatchKeyEventCallbackHolder.foreachReverse(new SDIterateCallback<SDActivityDispatchKeyEventCallback>()
        {
            @Override
            public boolean next(int i, SDActivityDispatchKeyEventCallback item, Iterator<SDActivityDispatchKeyEventCallback> it)
            {
                return item.dispatchKeyEvent(SDBaseActivity.this, event);
            }
        });

        if (notifyResult)
        {
            return true;
        } else
        {
            return super.dispatchKeyEvent(event);
        }
    }

    /**
     * activity是否处于竖屏方向
     *
     * @return
     */
    public boolean isOrientationPortrait()
    {
        return Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
    }

    /**
     * activity是否处于横屏方向
     *
     * @return
     */
    public boolean isOrientationLandscape()
    {
        return Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation;
    }

    /**
     * 设置activity为竖屏
     */
    public void setOrientationPortrait()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置activity为横屏
     */
    public void setOrientationLandscape()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public Dialog showProgressDialog(String msg)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
        return mProgressDialog;
    }

    public void dismissProgressDialog()
    {
        if (mProgressDialog != null)
        {
            try
            {
                mProgressDialog.dismiss();
            } catch (Exception e)
            {
            }
        }
    }

    @Override
    public void addView(View view)
    {
        SDViewUtil.addView((ViewGroup) findViewById(android.R.id.content), view);
    }

    @Override
    public void addView(int parentId, View view)
    {
        SDViewUtil.addView((ViewGroup) findViewById(parentId), view);
    }

    @Override
    public void removeView(View view)
    {
        SDViewUtil.removeView(view);
    }

    @Override
    public View removeView(int viewId)
    {
        View view = findViewById(viewId);
        SDViewUtil.removeView(view);
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

    public void registerAppView(SDAppView view)
    {
        if (view != null)
        {
            mDispatchKeyEventCallbackHolder.add(view);
            mDispatchTouchEventCallbackHolder.add(view);
            mActivityLifecycleCallbackHolder.add(view);
        }
    }

    public void unregisterAppView(SDAppView view)
    {
        if (view != null)
        {
            mDispatchKeyEventCallbackHolder.remove(view);
            mDispatchTouchEventCallbackHolder.remove(view);
            mActivityLifecycleCallbackHolder.remove(view);
        }
    }

    /**
     * 设置activity是否全屏
     *
     * @param fullScreen true-全屏，false-不全屏
     */
    public void setFullScreen(boolean fullScreen)
    {
        if (fullScreen)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    //------------notify callback start------------------

    private void notifyOnCreate(final Bundle savedInstanceState)
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityCreated(SDBaseActivity.this, savedInstanceState);
                return false;
            }
        });
    }

    private void notifyOnStart()
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityStarted(SDBaseActivity.this);
                return false;
            }
        });
    }

    private void notifyOnResume()
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityResumed(SDBaseActivity.this);
                return false;
            }
        });
    }

    private void notifyOnPause()
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityPaused(SDBaseActivity.this);
                return false;
            }
        });
    }

    private void notifyOnStop()
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityStopped(SDBaseActivity.this);
                return false;
            }
        });
    }

    private void notifyOnSaveInstanceState(final Bundle outState)
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivitySaveInstanceState(SDBaseActivity.this, outState);
                return false;
            }
        });
    }

    private void notifyOnDestroy()
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityDestroyed(SDBaseActivity.this);
                return false;
            }
        });
    }

    private void notifyOnRestoreInstanceState(final Bundle savedInstanceState)
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityRestoreInstanceState(SDBaseActivity.this, savedInstanceState);
                return false;
            }
        });
    }

    private void notifyOnActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        mActivityLifecycleCallbackHolder.foreach(new SDIterateCallback<SDActivityLifecycleCallback>()
        {
            @Override
            public boolean next(int i, SDActivityLifecycleCallback item, Iterator<SDActivityLifecycleCallback> it)
            {
                item.onActivityResult(SDBaseActivity.this, requestCode, resultCode, data);
                return false;
            }
        });
    }

    //------------notify callback end------------------

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {

    }

    @Override
    public void onClick(View v)
    {

    }

    /**
     * 不再维护，直接调用原生的方法操作
     *
     * @return
     */
    @Deprecated
    public SDFragmentManager getSDFragmentManager()
    {
        if (mFragmentManager == null)
        {
            mFragmentManager = new SDFragmentManager(getSupportFragmentManager());
        }
        return mFragmentManager;
    }
}
