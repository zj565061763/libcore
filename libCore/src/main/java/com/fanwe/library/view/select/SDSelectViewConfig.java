package com.fanwe.library.view.select;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.fanwe.library.utils.SDResourcesUtil;

@Deprecated
public class SDSelectViewConfig implements Cloneable
{
    public static final int EMPTY_VALUE = -Integer.MAX_VALUE;

    //
    private int textColorNormal = EMPTY_VALUE;
    private int textColorSelected = EMPTY_VALUE;
    private int textColorNormalResId;
    private int textColorSelectedResId;

    //
    private int textSizeNormal = EMPTY_VALUE;
    private int textSizeSelected = EMPTY_VALUE;

    //
    private int imageNormalResId = EMPTY_VALUE;
    private int imageSelectedResId = EMPTY_VALUE;

    //
    private Drawable backgroundNormal;
    private Drawable backgroundSelected;

    //
    private int backgroundNormalResId;
    private int backgroundSelectedResId;

    //
    private int backgroundColorNormal;
    private int backgroundColorSelected;
    private int backgroundColorNormalResId;
    private int backgroundColorSelectedResId;

    //
    private float alphaNormal = EMPTY_VALUE;
    private float alphaSelected = EMPTY_VALUE;

    @Override
    public SDSelectViewConfig clone()
    {
        try
        {
            return (SDSelectViewConfig) super.clone();
        } catch (Exception e)
        {
            return null;
        }
    }

    // ----------------------setter getter

    public int getTextColorNormal()
    {
        return textColorNormal;
    }

    public SDSelectViewConfig setTextColorNormal(int textColorNormal)
    {
        this.textColorNormal = textColorNormal;
        return this;
    }

    public int getTextColorSelected()
    {
        return textColorSelected;
    }

    public SDSelectViewConfig setTextColorSelected(int textColorSelected)
    {
        this.textColorSelected = textColorSelected;
        return this;
    }

    public int getTextColorNormalResId()
    {
        return textColorNormalResId;
    }

    //
    public SDSelectViewConfig setTextColorNormalResId(int textColorNormalResId)
    {
        this.textColorNormalResId = textColorNormalResId;
        this.textColorNormal = SDResourcesUtil.getColor(textColorNormalResId);
        return this;
    }

    public int getTextColorSelectedResId()
    {
        return textColorSelectedResId;
    }

    //
    public SDSelectViewConfig setTextColorSelectedResId(int textColorSelectedResId)
    {
        this.textColorSelectedResId = textColorSelectedResId;
        this.textColorSelected = SDResourcesUtil.getColor(textColorSelectedResId);
        return this;
    }

    public int getTextSizeNormal()
    {
        return textSizeNormal;
    }

    public SDSelectViewConfig setTextSizeNormal(int textSizeNormal)
    {
        this.textSizeNormal = textSizeNormal;
        return this;
    }

    public int getTextSizeSelected()
    {
        return textSizeSelected;
    }

    public SDSelectViewConfig setTextSizeSelected(int textSizeSelected)
    {
        this.textSizeSelected = textSizeSelected;
        return this;
    }

    public int getImageNormalResId()
    {
        return imageNormalResId;
    }

    public SDSelectViewConfig setImageNormalResId(int imageNormalResId)
    {
        this.imageNormalResId = imageNormalResId;
        return this;
    }

    public int getImageSelectedResId()
    {
        return imageSelectedResId;
    }

    public SDSelectViewConfig setImageSelectedResId(int imageSelectedResId)
    {
        this.imageSelectedResId = imageSelectedResId;
        return this;
    }

    public Drawable getBackgroundNormal()
    {
        return backgroundNormal;
    }

    public SDSelectViewConfig setBackgroundNormal(Drawable backgroundNormal)
    {
        this.backgroundNormal = backgroundNormal;
        return this;
    }

    public Drawable getBackgroundSelected()
    {
        return backgroundSelected;
    }

    public SDSelectViewConfig setBackgroundSelected(Drawable backgroundSelected)
    {
        this.backgroundSelected = backgroundSelected;
        return this;
    }

    public int getBackgroundNormalResId()
    {
        return backgroundNormalResId;
    }

    //
    public SDSelectViewConfig setBackgroundNormalResId(int backgroundNormalResId)
    {
        this.backgroundNormalResId = backgroundNormalResId;
        this.backgroundNormal = SDResourcesUtil.getDrawable(backgroundNormalResId);
        return this;
    }

    public int getBackgroundSelectedResId()
    {
        return backgroundSelectedResId;
    }

    //
    public SDSelectViewConfig setBackgroundSelectedResId(int backgroundSelectedResId)
    {
        this.backgroundSelectedResId = backgroundSelectedResId;
        this.backgroundSelected = SDResourcesUtil.getDrawable(backgroundSelectedResId);
        return this;
    }

    public int getBackgroundColorNormal()
    {
        return backgroundColorNormal;
    }

    //
    public SDSelectViewConfig setBackgroundColorNormal(int backgroundColorNormal)
    {
        this.backgroundColorNormal = backgroundColorNormal;
        this.backgroundNormal = new ColorDrawable(backgroundColorNormal);
        return this;
    }

    public int getBackgroundColorNormalResId()
    {
        return backgroundColorNormalResId;
    }

    //
    public SDSelectViewConfig setBackgroundColorNormalResId(int backgroundColorNormalResId)
    {
        this.backgroundColorNormalResId = backgroundColorNormalResId;
        this.backgroundNormal = new ColorDrawable(SDResourcesUtil.getColor(backgroundColorNormalResId));
        return this;
    }

    public int getBackgroundColorSelected()
    {
        return backgroundColorSelected;
    }

    //
    public SDSelectViewConfig setBackgroundColorSelected(int backgroundColorSelected)
    {
        this.backgroundColorSelected = backgroundColorSelected;
        this.backgroundSelected = new ColorDrawable(backgroundColorSelected);
        return this;
    }

    public int getBackgroundColorSelectedResId()
    {
        return backgroundColorSelectedResId;
    }

    public SDSelectViewConfig setBackgroundColorSelectedResId(int backgroundColorSelectedResId)
    {
        this.backgroundColorSelectedResId = backgroundColorSelectedResId;
        this.backgroundSelected = new ColorDrawable(SDResourcesUtil.getColor(backgroundColorSelectedResId));
        return this;
    }

    public float getAlphaNormal()
    {
        return alphaNormal;
    }

    public SDSelectViewConfig setAlphaNormal(float alphaNormal)
    {
        this.alphaNormal = alphaNormal;
        return this;
    }

    public float getAlphaSelected()
    {
        return alphaSelected;
    }

    public SDSelectViewConfig setAlphaSelected(float alphaSelected)
    {
        this.alphaSelected = alphaSelected;
        return this;
    }
}
