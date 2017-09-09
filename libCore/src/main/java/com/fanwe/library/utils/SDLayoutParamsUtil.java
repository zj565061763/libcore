package com.fanwe.library.utils;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/4/11.
 */
public class SDLayoutParamsUtil
{
    public static LinearLayout.LayoutParams newParamsLinearLayoutWW()
    {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams newParamsLinearLayoutMM()
    {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams newParamsLinearLayoutMW()
    {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams newParamsLinearLayoutWM()
    {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    // RelativeLayout
    public static RelativeLayout.LayoutParams newParamsRelativeLayoutWW()
    {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams newParamsRelativeLayoutMM()
    {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams newParamsRelativeLayoutMW()
    {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams newParamsRelativeLayoutWM()
    {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    // FrameLayout
    public static FrameLayout.LayoutParams newParamsFrameLayoutWW()
    {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams newParamsFrameLayoutMM()
    {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams newParamsFrameLayoutMW()
    {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams newParamsFrameLayoutWM()
    {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    // ViewGroup
    public static ViewGroup.LayoutParams newParamsViewGroupWW()
    {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams newParamsViewGroupMM()
    {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams newParamsViewGroupMW()
    {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams newParamsViewGroupWM()
    {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
