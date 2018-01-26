package com.fanwe.library.activity;

import android.view.View;

import com.fanwe.lib.title.FTitle;
import com.fanwe.lib.title.FTitleItem;
import com.fanwe.library.R;

/**
 * Created by zhengjun on 2018/1/26.
 */
public abstract class FTitleActivity extends SDBaseActivity implements FTitle.Callback
{
    private FTitle view_title;

    public FTitle getTitleView()
    {
        return view_title;
    }

    @Override
    protected int onCreateTitleViewResId()
    {
        return R.layout.lib_core_include_title;
    }

    @Override
    protected void onInitTitleView(View view)
    {
        super.onInitTitleView(view);
        view_title = view.findViewById(R.id.view_title);
        if (view_title != null)
        {
            view_title.setCallback(this);
        }
    }

    @Override
    public void onClickItemLeftTitleBar(int index, FTitleItem item)
    {
        finish();
    }

    @Override
    public void onClickItemMiddleTitleBar(int index, FTitleItem item)
    {

    }

    @Override
    public void onClickItemRightTitleBar(int index, FTitleItem item)
    {

    }
}
