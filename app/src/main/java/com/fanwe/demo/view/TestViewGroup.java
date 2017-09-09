package com.fanwe.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.utils.SDViewUtil;

/**
 * Created by Administrator on 2017/6/27.
 */

public class TestViewGroup extends ViewGroup
{
    public TestViewGroup(Context context)
    {
        super(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height*2, MeasureSpec.EXACTLY);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        View child = getChildAt(0);
        int dis = SDViewUtil.dp2px(50);
        int height = getHeight();

        child.layout(0, -dis, getWidth(), height + dis);
    }
}
