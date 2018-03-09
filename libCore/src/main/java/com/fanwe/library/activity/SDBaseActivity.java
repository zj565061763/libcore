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

import com.fanwe.lib.holder.objects.FObjectsHolder;
import com.fanwe.lib.holder.objects.FStrongObjectsHolder;
import com.fanwe.lib.holder.objects.ForeachCallback;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.listener.SDActivityKeyEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.listener.SDActivityTouchEventCallback;


public abstract class SDBaseActivity extends AppCompatActivity implements
        OnClickListener
{
    private SDFragmentManager mFragmentManager;

    private ProgressDialog mProgressDialog;

    private FObjectsHolder<SDActivityLifecycleCallback> mLifecycleCallbackHolder;
    private FObjectsHolder<SDActivityTouchEventCallback> mTouchEventCallbackHolder;
    private FObjectsHolder<SDActivityKeyEventCallback> mKeyEventCallbackHolder;

    public Activity getActivity()
    {
        return this;
    }

    public final FObjectsHolder<SDActivityLifecycleCallback> getLifecycleCallbackHolder()
    {
        if (mLifecycleCallbackHolder == null)
        {
            mLifecycleCallbackHolder = new FStrongObjectsHolder<>();
        }
        return mLifecycleCallbackHolder;
    }

    public final FObjectsHolder<SDActivityTouchEventCallback> getTouchEventCallbackHolder()
    {
        if (mTouchEventCallbackHolder == null)
        {
            mTouchEventCallbackHolder = new FStrongObjectsHolder<>();
        }
        return mTouchEventCallbackHolder;
    }

    public final FObjectsHolder<SDActivityKeyEventCallback> getKeyEventCallbackHolder()
    {
        if (mKeyEventCallbackHolder == null)
        {
            mKeyEventCallbackHolder = new FStrongObjectsHolder<>();
        }
        return mKeyEventCallbackHolder;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

            onInitTitleView(titleView);
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

    /**
     * 初始化标题栏view
     *
     * @param view
     */
    protected void onInitTitleView(View view)
    {

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
        notifyOnResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        notifyOnPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        notifyOnStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dismissProgressDialog();
        notifyOnDestroy();
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
        final Object data = getTouchEventCallbackHolder().foreachReverse(new ForeachCallback<SDActivityTouchEventCallback>()
        {
            @Override
            protected void next(SDActivityTouchEventCallback item)
            {
                if (item.dispatchTouchEvent(SDBaseActivity.this, ev))
                {
                    setData(true);
                    breakForeach();
                }
            }
        });
        if (data != null)
        {
            // 不为null的话直接返回true，不做data为true的判断，上面已经写死了
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    {
        final Object data = getKeyEventCallbackHolder().foreachReverse(new ForeachCallback<SDActivityKeyEventCallback>()
        {
            @Override
            protected void next(SDActivityKeyEventCallback item)
            {
                if (item.dispatchKeyEvent(SDBaseActivity.this, event))
                {
                    setData(true);
                    breakForeach();
                }
            }
        });
        if (data != null)
        {
            // 不为null的话直接返回true，不做data为true的判断，上面已经写死了
            return true;
        }

        return super.dispatchKeyEvent(event);
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
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityCreated(SDBaseActivity.this, savedInstanceState);
            }
        });
    }

    private void notifyOnStart()
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityStarted(SDBaseActivity.this);
            }
        });
    }

    private void notifyOnResume()
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityResumed(SDBaseActivity.this);
            }
        });
    }

    private void notifyOnPause()
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityPaused(SDBaseActivity.this);
            }
        });
    }

    private void notifyOnStop()
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityStopped(SDBaseActivity.this);
            }
        });
    }

    private void notifyOnDestroy()
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityDestroyed(SDBaseActivity.this);
            }
        });
    }

    private void notifyOnSaveInstanceState(final Bundle outState)
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivitySaveInstanceState(SDBaseActivity.this, outState);
            }
        });
    }

    private void notifyOnRestoreInstanceState(final Bundle savedInstanceState)
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityRestoreInstanceState(SDBaseActivity.this, savedInstanceState);
            }
        });
    }

    private void notifyOnActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        getLifecycleCallbackHolder().foreach(new ForeachCallback<SDActivityLifecycleCallback>()
        {
            @Override
            protected void next(SDActivityLifecycleCallback item)
            {
                item.onActivityResult(SDBaseActivity.this, requestCode, resultCode, data);
            }
        });
    }

    //------------notify callback end------------------

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params)
    {
        if (params == null)
        {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        super.addContentView(view, params);
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
