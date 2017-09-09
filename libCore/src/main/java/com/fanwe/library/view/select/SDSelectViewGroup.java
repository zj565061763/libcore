package com.fanwe.library.view.select;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SDSelectViewGroup extends LinearLayout
{
    public SDSelectViewGroup(Context context)
    {
        super(context);
        init();
    }

    public SDSelectViewGroup(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDSelectViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private SDSelectViewManager mSelectViewManager = new SDSelectViewManager();
    private List<View> mListView = new ArrayList<>();

    private void init()
    {

    }

    public SDSelectViewManager getSelectViewManager()
    {
        return mSelectViewManager;
    }

    public void initChildView()
    {
        mListView.clear();
        for (int i = 0; i < getChildCount(); i++)
        {
            mListView.add(getChildAt(i));
        }

        mSelectViewManager.clearSelected();
        mSelectViewManager.setItems(mListView);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        initChildView();
    }
}
