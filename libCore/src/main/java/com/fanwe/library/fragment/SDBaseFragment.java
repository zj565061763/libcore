package com.fanwe.library.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.fanwe.lib.eventbus.FEventObserver;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.listener.SDActivityKeyEventCallback;
import com.fanwe.library.listener.SDActivityTouchEventCallback;

import java.util.List;

@Deprecated
public abstract class SDBaseFragment extends Fragment implements
        OnClickListener,
        SDActivityTouchEventCallback,
        SDActivityKeyEventCallback
{

    private SDFragmentManager fragmentManager;
    private boolean isRemovedFromViewPager = false;
    private boolean isStopped;

    public boolean isRemovedFromViewPager()
    {
        return isRemovedFromViewPager;
    }

    public void setIsRemovedFromViewPager(boolean isRemovedFromViewPager)
    {
        this.isRemovedFromViewPager = isRemovedFromViewPager;
    }

    public SDBaseFragment()
    {
        ensureArgumentsNotNull();
    }

    public boolean isStopped()
    {
        return isStopped;
    }

    private void ensureArgumentsNotNull()
    {
        if (getArguments() == null)
        {
            super.setArguments(new Bundle());
        }
    }

    @Override
    public void setArguments(Bundle args)
    {
        ensureArgumentsNotNull();
        if (args != null)
        {
            getArguments().putAll(args);
        }
    }

    public SDFragmentManager getSDFragmentManager()
    {
        if (fragmentManager == null)
        {
            fragmentManager = new SDFragmentManager(getChildFragmentManager());
        }
        return fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        SDBaseActivity activity = getBaseActivity();
        if (activity != null)
        {
            activity.getTouchEventCallbackHolder().add(this);
            activity.getKeyEventCallbackHolder().add(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View contentView = onCreateContentView(inflater, container, savedInstanceState);
        if (contentView == null)
        {
            int layoutId = onCreateContentView();
            if (layoutId != 0)
            {
                View layoutView = inflater.inflate(layoutId, container, false);
                contentView = addTitleView(layoutView);
            }
        }
        return contentView;
    }

    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return null;
    }

    /**
     * 此方法用于被重写返回fragment布局id
     *
     * @return
     */
    protected abstract int onCreateContentView();

    /**
     * 为contentView添加titleView
     *
     * @param contentView
     * @return
     */
    private View addTitleView(View contentView)
    {
        View viewFinal = contentView;

        View titleView = createTitleView();
        if (titleView != null)
        {
            LinearLayout linAll = new LinearLayout(getActivity());
            linAll.setOrientation(LinearLayout.VERTICAL);
            linAll.addView(titleView, createTitleViewLayoutParams());
            linAll.addView(contentView, createContentViewLayoutParams());
            viewFinal = linAll;
        }
        return viewFinal;
    }

    private View createTitleView()
    {
        View view = onCreateTitleView();
        if (view == null)
        {
            int resId = onCreateTitleViewResId();
            if (resId != 0)
            {
                view = LayoutInflater.from(getActivity()).inflate(resId, null);
            }
        }
        return view;
    }

    protected View onCreateTitleView()
    {
        return null;
    }

    protected int onCreateTitleViewResId()
    {
        return 0;
    }

    protected LinearLayout.LayoutParams createTitleViewLayoutParams()
    {
        return new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    protected LinearLayout.LayoutParams createContentViewLayoutParams()
    {
        return new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public SDBaseActivity getBaseActivity()
    {
        SDBaseActivity sdBaseActivity = null;
        if (getActivity() instanceof SDBaseActivity)
        {
            sdBaseActivity = (SDBaseActivity) getActivity();
        }
        return sdBaseActivity;
    }

    public boolean isEmpty(CharSequence content)
    {
        return TextUtils.isEmpty(content);
    }

    public boolean isEmpty(List<?> list)
    {
        return list == null || list.isEmpty();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        FEventObserver.unregisterAll(this);
        SDBaseActivity activity = getBaseActivity();
        if (activity != null)
        {
            activity.getTouchEventCallbackHolder().remove(this);
            activity.getKeyEventCallbackHolder().remove(this);
        }
    }

    /**
     * 调用此方法会触发onRefreshData()方法
     */
    public final void refreshData()
    {
        if (this.isAdded())
        {
            onRefreshData();
        }
    }

    /**
     * 调用refreshData()方法后触发此方法
     */
    protected void onRefreshData()
    {

    }

    public void hideFragmentView()
    {
        if (getView() != null)
        {
            getView().setVisibility(View.GONE);
        }
        notifyVisibleState();
    }

    public void showFragmentView()
    {
        if (getView() != null)
        {
            getView().setVisibility(View.VISIBLE);
        }
        notifyVisibleState();
    }

    public void invisibleFragmentView()
    {
        if (getView() != null)
        {
            getView().setVisibility(View.INVISIBLE);
        }
        notifyVisibleState();
    }

    public void notifyVisibleState()
    {

    }

    public boolean toggleFragmentView(List<?> list)
    {
        if (list != null && !list.isEmpty())
        {
            showFragmentView();
            return true;
        } else
        {
            hideFragmentView();
            return false;
        }
    }

    public boolean toggleFragmentView(Object obj)
    {
        if (obj != null)
        {
            showFragmentView();
            return true;
        } else
        {
            hideFragmentView();
            return false;
        }
    }

    public boolean toggleFragmentView(String content)
    {
        if (!TextUtils.isEmpty(content))
        {
            showFragmentView();
            return true;
        } else
        {
            hideFragmentView();
            return false;
        }
    }

    public boolean toggleFragmentView(int show)
    {
        if (show == 1)
        {
            showFragmentView();
            return true;
        } else
        {
            hideFragmentView();
            return false;
        }
    }

    public boolean toggleFragmentView(boolean show)
    {
        if (show)
        {
            showFragmentView();
            return true;
        } else
        {
            hideFragmentView();
            return false;
        }
    }

    public View findViewById(int id)
    {
        View view = null;
        if (getView() != null)
        {
            view = getView().findViewById(id);
        }
        return view;
    }

    public void remove()
    {
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    public boolean onBackPressed()
    {
        return false;
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public boolean dispatchTouchEvent(Activity activity, MotionEvent ev)
    {
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(Activity activity, KeyEvent event)
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
        return false;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        isStopped = false;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        isStopped = true;
    }
}
