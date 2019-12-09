package com.sd.libcore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.eventact.ActivityEventDispatcher;
import com.sd.lib.eventact.ActivityEventDispatcherFactory;


public abstract class FActivity extends AppCompatActivity implements OnClickListener
{
    private ActivityEventDispatcher mEventDispatcher;
    private ProgressDialog mProgressDialog;

    public Activity getActivity()
    {
        return this;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final int layoutId = onCreateContentView();
        if (layoutId != 0)
            setContentView(layoutId);
    }

    /**
     * 返回activity布局id，基类调用的顺序：
     * <p>
     * 1. onCreateContentView()<br>
     * 2. setContentView()<br>
     * 3. onCreateTitleView() 或者 onCreateTitleViewLayoutId()<br>
     * 4. onInitTitleView(View view)<br>
     * 5. onInitContentView(View view)<br>
     *
     * @return
     */
    protected int onCreateContentView()
    {
        return 0;
    }

    @Override
    public void setContentView(int layoutId)
    {
        final View contentView = getLayoutInflater().inflate(layoutId, (ViewGroup) findViewById(android.R.id.content), false);
        setContentView(contentView);
    }

    @Override
    public void setContentView(View view)
    {
        final View contentView = addTitleViewIfNeed(view);
        super.setContentView(contentView);

        onInitContentView(contentView);
    }

    /**
     * setContentView方法之后会回调此方法，可以用来初始化View
     *
     * @param view
     */
    protected void onInitContentView(View view)
    {

    }

    /**
     * 为contentView添加titleView
     *
     * @param contentView
     * @return
     */
    private View addTitleViewIfNeed(View contentView)
    {
        final View titleView = createTitleView();
        if (titleView == null)
            return contentView;

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(titleView);
        linearLayout.addView(contentView);
        return linearLayout;
    }

    private View createTitleView()
    {
        View titleView = onCreateTitleView();
        if (titleView == null)
        {
            final int layoutId = onCreateTitleViewLayoutId();
            if (layoutId != 0)
                titleView = getLayoutInflater().inflate(layoutId, (ViewGroup) findViewById(android.R.id.content), false);
        }

        if (titleView != null)
        {
            titleView = transformTitleView(titleView);
            if (titleView == null)
                throw new RuntimeException("transformTitleView return null");

            onInitTitleView(titleView);
        }

        return titleView;
    }

    /**
     * 返回标题栏布局
     *
     * @return
     */
    protected View onCreateTitleView()
    {
        return null;
    }

    /**
     * 返回标题栏布局id
     *
     * @return
     */
    protected int onCreateTitleViewLayoutId()
    {
        return 0;
    }

    /**
     * 转换标题栏，可以做一些全局的修改
     *
     * @param view
     * @return
     */
    protected View transformTitleView(View view)
    {
        return view;
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
    protected void onDestroy()
    {
        super.onDestroy();
        dismissProgressDialog();
    }

    private ActivityEventDispatcher getEventDispatcher()
    {
        if (mEventDispatcher == null)
            mEventDispatcher = ActivityEventDispatcherFactory.create(this);
        return mEventDispatcher;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        getEventDispatcher().dispatch_onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev)
    {
        final boolean result = getEventDispatcher().dispatch_dispatchTouchEvent(ev);
        if (result)
            return true;

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    {
        final boolean result = getEventDispatcher().dispatch_dispatchKeyEvent(event);
        if (result)
            return true;

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

    public void showProgressDialog(String msg)
    {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void dismissProgressDialog()
    {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
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

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params)
    {
        if (params == null)
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        super.addContentView(view, params);
    }

    @Override
    public void onClick(View v)
    {

    }
}
