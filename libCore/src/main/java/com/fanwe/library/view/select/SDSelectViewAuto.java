package com.fanwe.library.view.select;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
public abstract class SDSelectViewAuto extends SDSelectView
{
    private List<View> mListAutoView = new ArrayList<>();

    public SDSelectViewAuto(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDSelectViewAuto(Context context)
    {
        super(context);
    }

    protected void addAutoView(View... views)
    {
        if (views == null)
        {
            return;
        }
        for (View item : views)
        {
            if (!mListAutoView.contains(item))
            {
                mListAutoView.add(item);
            }
        }
    }

    protected void removeAutoView(View... views)
    {
        if (views == null)
        {
            return;
        }
        for (View item : views)
        {
            mListAutoView.remove(item);
        }
    }

    @Override
    protected void setContentView(int resId)
    {
        mListAutoView.clear();
        super.setContentView(resId);
    }

    @Override
    protected void setContentView(View view)
    {
        mListAutoView.clear();
        super.setContentView(view);
    }

    @Override
    protected void setContentView(View view, android.view.ViewGroup.LayoutParams params)
    {
        mListAutoView.clear();
        super.setContentView(view, params);
    }

    @Override
    public void onNormal()
    {
        normalAutoViews();
    }

    @Override
    public void onSelected()
    {
        selectAutoViews();
    }

    private void normalAutoViews()
    {
        Iterator<View> it = mListAutoView.iterator();
        while (it.hasNext())
        {
            View item = it.next();
            normalAutoView(item);
        }
    }

    private void selectAutoViews()
    {
        Iterator<View> it = mListAutoView.iterator();
        while (it.hasNext())
        {
            View item = it.next();
            selectAutoView(item);
        }
    }

    private void normalAutoView(View view)
    {
        normalView_background(view);
        normalView_alpha(view);
        if (view instanceof TextView)
        {
            TextView tv = (TextView) view;
            normalTextView_textColor(tv);
            normalTextView_textSize(tv);
        } else if (view instanceof ImageView)
        {
            ImageView iv = (ImageView) view;
            normalImageView_image(iv);
        }
    }

    private void selectAutoView(View view)
    {
        selectView_background(view);
        selectView_alpha(view);
        if (view instanceof TextView)
        {
            TextView tv = (TextView) view;
            selectTextView_textColor(tv);
            selectTextView_textSize(tv);
        } else if (view instanceof ImageView)
        {
            ImageView iv = (ImageView) view;
            selectImageView_image(iv);
        }
    }

}
