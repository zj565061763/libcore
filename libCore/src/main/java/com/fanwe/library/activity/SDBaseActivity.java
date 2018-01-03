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

import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.lib.utils.extend.FActivityStack;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.event.SDEvent;
import com.fanwe.library.event.SDEventObserver;
import com.fanwe.library.listener.SDActivityDispatchKeyEventCallback;
import com.fanwe.library.listener.SDActivityDispatchTouchEventCallback;
import com.fanwe.library.listener.SDActivityLifecycleCallback;
import com.fanwe.library.view.ISDViewContainer;
import com.fanwe.library.view.SDAppView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;

public abstract class SDBaseActivity extends AppCompatActivity implements
        SDEventObserver,
        OnClickListener,
        ISDViewContainer
{
    private SDFragmentManager mFragmentManager;

    private ProgressDialog mProgressDialog;

    private boolean mIsResume;

    private List<SDActivityLifecycleCallback> mListActivityLifecycleCallback = new ArrayList<>();
    private List<SDActivityDispatchTouchEventCallback> mListDispatchTouchEventCallback = new ArrayList<>();
    private List<SDActivityDispatchKeyEventCallback> mListDispatchKeyEventCallback = new ArrayList<>();

    public Activity getActivity()
    {
        return this;
    }

    public List<SDActivityLifecycleCallback> getListActivityLifecycleCallback()
    {
        return mListActivityLifecycleCallback;
    }

    public List<SDActivityDispatchTouchEventCallback> getListDispatchTouchEventCallback()
    {
        return mListDispatchTouchEventCallback;
    }

    public List<SDActivityDispatchKeyEventCallback> getListDispatchKeyEventCallback()
    {
        return mListDispatchKeyEventCallback;
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
        FActivityStack.getInstance().onCreate(this);
        EventBus.getDefault().register(this);
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
        FActivityStack.getInstance().onResume(this);
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
        notifyOnStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FActivityStack.getInstance().onDestroy(this);
        EventBus.getDefault().unregister(this);
        dismissProgressDialog();

        notifyOnDestroy();
    }


    @Override
    public void finish()
    {
        FActivityStack.getInstance().onDestroy(this);
        super.finish();
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
        int size = mListDispatchTouchEventCallback.size();
        for (int i = size - 1; i >= 0; i--)
        {
            SDActivityDispatchTouchEventCallback item = mListDispatchTouchEventCallback.get(i);
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
        int size = mListDispatchKeyEventCallback.size();
        for (int i = size - 1; i >= 0; i--)
        {
            SDActivityDispatchKeyEventCallback item = mListDispatchKeyEventCallback.get(i);
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
            mListDispatchKeyEventCallback.add(view);
            mListDispatchTouchEventCallback.add(view);
            mListActivityLifecycleCallback.add(view);
        }
    }

    public void unregisterAppView(SDAppView view)
    {
        if (view != null)
        {
            mListDispatchKeyEventCallback.remove(view);
            mListDispatchTouchEventCallback.remove(view);
            mListActivityLifecycleCallback.remove(view);
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
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityCreated(this, savedInstanceState);
        }
    }

    private void notifyOnStart()
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityStarted(this);
        }
    }

    private void notifyOnResume()
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityResumed(this);
        }
    }

    private void notifyOnPause()
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityPaused(this);
        }
    }

    private void notifyOnStop()
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityStopped(this);
        }
    }

    private void notifyOnSaveInstanceState(final Bundle outState)
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivitySaveInstanceState(this, outState);
        }
    }

    private void notifyOnDestroy()
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityDestroyed(this);
        }
    }

    private void notifyOnRestoreInstanceState(final Bundle savedInstanceState)
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
            item.onActivityRestoreInstanceState(this, savedInstanceState);
        }
    }

    private void notifyOnActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        Iterator<SDActivityLifecycleCallback> it = mListActivityLifecycleCallback.iterator();
        while (it.hasNext())
        {
            SDActivityLifecycleCallback item = it.next();
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
    public void onEventMainThread(SDEvent event)
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
