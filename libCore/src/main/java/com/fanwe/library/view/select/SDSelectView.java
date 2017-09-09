package com.fanwe.library.view.select;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewUtil;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public abstract class SDSelectView extends LinearLayout
{
    private Map<View, SDSelectViewConfig> mMapViewConfig = new HashMap<>();
    private SDSelectViewStateCallback mStateCallback;

    public SDSelectView(Context context)
    {
        super(context);
        baseInit();
    }

    public SDSelectView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        baseInit();
    }

    private void baseInit()
    {
        onBaseInit();
    }

    /**
     * 基类构造方法调用的初始化方法<br>
     * 如果子类在此方法内访问子类定义属性时候直接new的属性，如：private String value = "value"，则value的值将为null
     */
    protected void onBaseInit()
    {

    }

    /**
     * 若需要设置默认config，为统一规范，重写此方法进行设置
     */
    public void setDefaultConfig()
    {

    }

    /**
     * 若需要设置默认config反转，为统一规范，重写此方法进行设置
     */
    public void reverseDefaultConfig()
    {

    }

    /**
     * 设置状态回调
     *
     * @param stateCallback
     */
    public void setStateCallback(SDSelectViewStateCallback stateCallback)
    {
        this.mStateCallback = stateCallback;
    }

    public SDSelectViewConfig getViewConfig(View view)
    {
        SDSelectViewConfig config = null;
        if (view != null)
        {
            config = mMapViewConfig.get(view);
            if (config == null)
            {
                config = new SDSelectViewConfig();
                mMapViewConfig.put(view, config);
            }
        }
        return config;
    }

    // util method

    protected SDSelectView normalImageView_image(ImageView view)
    {
        int resId = getViewConfig(view).getImageNormalResId();
        if (resId != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setImageResource(resId);
        }
        return this;
    }

    protected SDSelectView selectImageView_image(ImageView view)
    {
        int resId = getViewConfig(view).getImageSelectedResId();
        if (resId != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setImageResource(resId);
        }
        return this;
    }

    protected SDSelectView normalTextView_textColor(TextView view)
    {
        int color = getViewConfig(view).getTextColorNormal();
        if (color != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setTextColor(color);
        }
        return this;
    }

    protected SDSelectView selectTextView_textColor(TextView view)
    {
        int color = getViewConfig(view).getTextColorSelected();
        if (color != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setTextColor(color);
        }
        return this;
    }

    protected SDSelectView normalTextView_textSize(TextView view)
    {
        int size = getViewConfig(view).getTextSizeNormal();
        if (size != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    protected SDSelectView selectTextView_textSize(TextView view)
    {
        int size = getViewConfig(view).getTextSizeSelected();
        if (size != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    protected SDSelectView normalView_alpha(View view)
    {
        float alpha = getViewConfig(view).getAlphaNormal();
        if (alpha != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setAlpha(alpha);
        }
        return this;
    }

    protected SDSelectView selectView_alpha(View view)
    {
        float alpha = getViewConfig(view).getAlphaSelected();
        if (alpha != SDSelectViewConfig.EMPTY_VALUE)
        {
            view.setAlpha(alpha);
        }
        return this;
    }

    protected SDSelectView normalView_background(View view)
    {
        Drawable drawable = getViewConfig(view).getBackgroundNormal();
        if (drawable != null)
        {
            SDViewUtil.setBackgroundDrawable(view, drawable);
        }
        return this;
    }

    protected SDSelectView selectView_background(View view)
    {
        Drawable drawable = getViewConfig(view).getBackgroundSelected();
        if (drawable != null)
        {
            SDViewUtil.setBackgroundDrawable(view, drawable);
        }
        return this;
    }

    /**
     * 设置布局
     *
     * @param resId
     */
    protected void setContentView(int resId)
    {
        removeAllViews();
        mMapViewConfig.clear();
        LayoutInflater.from(getContext()).inflate(resId, this, true);
    }

    /**
     * 设置布局
     *
     * @param view
     */
    protected void setContentView(View view)
    {
        removeAllViews();
        mMapViewConfig.clear();
        addView(view);
    }

    /**
     * 设置布局
     *
     * @param view
     * @param params
     */
    protected void setContentView(View view, ViewGroup.LayoutParams params)
    {
        removeAllViews();
        mMapViewConfig.clear();
        addView(view, params);
    }

    @Override
    public void setSelected(boolean selected)
    {
        super.setSelected(selected);

        updateViewState(true);
    }

    /**
     * 根据当前选中的状态刷新view界面
     *
     * @param notifyCallback 是否通知回调
     */
    public void updateViewState(boolean notifyCallback)
    {
        if (isSelected())
        {
            onSelected();
            if (notifyCallback)
            {
                notifySelected();
            }
        } else
        {
            onNormal();
            if (notifyCallback)
            {
                notifyNormal();
            }
        }
    }

    /**
     * 根据当前选中的状态刷新view界面
     */
    public void updateViewState()
    {
        updateViewState(false);
    }

    /**
     * 切换选中和正常状态
     */
    public void toggleSelected()
    {
        setSelected(!isSelected());
    }

    /**
     * 正常状态回调
     */
    public abstract void onNormal();

    /**
     * 选中状态回调
     */
    public abstract void onSelected();

    private void notifyNormal()
    {
        if (mStateCallback != null)
        {
            mStateCallback.onNormal(this);
        }
    }

    private void notifySelected()
    {
        if (mStateCallback != null)
        {
            mStateCallback.onSelected(this);
        }
    }

    public interface SDSelectViewStateCallback
    {
        void onNormal(View v);

        void onSelected(View v);
    }
}
