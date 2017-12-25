package com.fanwe.library.model;

import android.graphics.Rect;
import android.view.View;

import com.fanwe.lib.utils.FViewUtil;

public class ViewRecter implements Recter
{

    private View view;

    public ViewRecter(View view)
    {
        super();
        this.view = view;
    }

    public View getView()
    {
        return view;
    }

    @Override
    public Rect getRect()
    {
        return FViewUtil.getGlobalVisibleRect(view);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (!(o instanceof ViewRecter))
        {
            return false;
        }

        ViewRecter viewRecter = (ViewRecter) o;
        if (!view.equals(viewRecter.getView()))
        {
            return false;
        }

        return true;
    }

}
