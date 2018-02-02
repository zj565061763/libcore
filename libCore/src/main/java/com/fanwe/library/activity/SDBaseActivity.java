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

import com.fanwe.lib.event.FEventObserver;
import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.listener.SDActivityKeyEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.listener.SDActivityTouchEventCallback;
import com.fanwe.library.view.ISDViewContainer;
import com.fanwe.library.view.SDAppView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public abstract class SDBaseActivity extends AppCompatActivity implements
        OnClickListener,
        ISDViewContainer
{
    private SDFragmentManager mFragmentManager;

    private ProgressDialog mProgressDialog;

    private List<SDActivityLifecycleCallback> mLifecycleCallbackHolder;
    private List<SDActivityTouchEventCallback> mTouchEventCallbackHolder;
    private List<SDActivityKeyEventCallback> mKeyEventCallbackHolder;

    public Activity getActivity()
    {
        return this;
    }

    public final List<SDActivityLifecycleCallback> getLifecycleCallbackHolder()
    {
        if (mLifecycleCallbackHolder == null)
        {
            mLifecycleCallbackHolder = new CopyOnWriteArrayList<SDActivityLifecycleCallback>()
            {
                @Override
                public boolean add(SDActivityLifecycleCallback o)
                {
                    if (contains(o))
                    {
                        return false;
                    }
                    return super.add(o);
                }
            };
        }
        return mLifecycleCallbackHolder;
    }

    public final List<SDActivityTouchEventCallback> getTouchEventCallbackHolder()
    {
        if (mTouchEventCallbackHolder == null)
        {
            mTouchEventCallbackHolder = new CopyOnWriteArrayList<SDActivityTouchEventCallback>()
            {
                @Override
                public boolean add(SDActivityTouchEventCallback o)
                {
                    if (contains(o))
                    {
                        return false;
                    }
                    return super.add(o);
                }
            };
        }
        return mTouchEventCallbackHolder;
    }

    public final List<SDActivityKeyEventCallback> getKeyEventCallbackHolder()
    {
        if (mKeyEventCallbackHolder == null)
        {
            mKeyEventCallbackHolder = new CopyOnWriteArrayList<SDActivityKeyEventCallback>()
            {
                @Override
                public boolean add(SDActivityKeyEventCallback o)
                {
                    if (contains(o))
                    {
                        return false;
                    }
                    return super.add(o);
                }
            };
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
        FEventObserver.unregisterAll(this);
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
        final int size = getTouchEventCallbackHolder().size();
        for (int i = size - 1; i >= 0; i--)
        {
            SDActivityTouchEventCallback item = getTouchEventCallbackHolder().get(i);
            if (item.dispatchTouchEvent(this, ev))
            {
                return true;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    {
        final int size = getKeyEventCallbackHolder().size();
        for (int i = size - 1; i >= 0; i--)
        {
            SDActivityKeyEventCallback item = getKeyEventCallbackHolder().get(i);
            if (item.dispatchKeyEvent(this, event))
            {
                return true;
            }
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

    public void registerAppView(SDAppView view)
    {
        if (view != null)
        {
            getLifecycleCallbackHolder().add(view);
            getTouchEventCallbackHolder().add(view);
            getKeyEventCallbackHolder().add(view);
        }
    }

    public void unregisterAppView(SDAppView view)
    {
        getLifecycleCallbackHolder().remove(view);
        getTouchEventCallbackHolder().remove(view);
        getKeyEventCallbackHolder().remove(view);
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
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityCreated(this, savedInstanceState);
        }
    }

    private void notifyOnStart()
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityStarted(this);
        }
    }

    private void notifyOnResume()
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityResumed(this);
        }
    }

    private void notifyOnPause()
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityPaused(this);
        }
    }

    private void notifyOnStop()
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityStopped(this);
        }
    }

    private void notifyOnDestroy()
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityDestroyed(this);
        }
    }

    private void notifyOnSaveInstanceState(final Bundle outState)
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivitySaveInstanceState(this, outState);
        }
    }

    private void notifyOnRestoreInstanceState(final Bundle savedInstanceState)
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityRestoreInstanceState(this, savedInstanceState);
        }
    }

    private void notifyOnActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        for (SDActivityLifecycleCallback item : getLifecycleCallbackHolder())
        {
            item.onActivityResult(this, requestCode, resultCode, data);
        }
    }

    //------------notify callback end------------------

    public void addContentView(View view)
    {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, params);
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
