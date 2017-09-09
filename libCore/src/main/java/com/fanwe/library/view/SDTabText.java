package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewAuto;

@Deprecated
public class SDTabText extends SDSelectViewAuto
{

    public TextView mTv_title;

    public SDTabText(Context context)
    {
        super(context);
        init();
    }

    public SDTabText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_tab_text);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        addAutoView(mTv_title);

        setDefaultConfig();
        onNormal();
    }

    @Override
    public void setDefaultConfig()
    {
        getViewConfig(mTv_title).setTextColorNormalResId(R.color.gray);
        getViewConfig(mTv_title).setTextColorSelected(getResources().getColor(R.color.res_main_color));
        super.setDefaultConfig();
    }

    public void setTextSizeTitleSp(int textSizeSp)
    {
        SDViewUtil.setTextSizeSp(mTv_title, textSizeSp);
    }

    public void setTextTitle(CharSequence content)
    {
        SDViewBinder.setTextView(mTv_title, content);
    }

}
