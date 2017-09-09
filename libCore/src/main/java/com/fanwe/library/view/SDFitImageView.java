package com.fanwe.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.library.utils.SDViewUtil;

public class SDFitImageView extends ImageView
{
    public SDFitImageView(Context context)
    {
        super(context);
        init();
    }

    public SDFitImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public SDFitImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        SDViewUtil.scaleHeight(this, getDrawable());
    }
}
