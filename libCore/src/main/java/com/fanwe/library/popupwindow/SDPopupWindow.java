package com.fanwe.library.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.fanwe.lib.utils.FViewUtil;
import com.fanwe.library.SDLibrary;

public class SDPopupWindow extends PopupWindow implements View.OnClickListener
{

    private Context context;

    public SDPopupWindow()
    {
        this(SDLibrary.getInstance().getContext());
    }

    public SDPopupWindow(Context context)
    {
        super(context);
        this.context = context;
        baseInit();
    }

    public Activity getActivity()
    {
        Activity activity = null;
        if (context instanceof Activity)
        {
            activity = (Activity) context;
        }
        return activity;
    }

    private void baseInit()
    {
        FViewUtil.wrapperPopupWindow(this);
    }

    @Override
    public void onClick(View v)
    {

    }

    public void setContentView(int resId)
    {
        View contentView = LayoutInflater.from(SDLibrary.getInstance().getContext()).inflate(resId, null);
        setContentView(contentView);
    }
}
