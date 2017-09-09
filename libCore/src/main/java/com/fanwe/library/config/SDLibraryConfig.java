package com.fanwe.library.config;

import android.graphics.Color;

public class SDLibraryConfig
{
    /**
     * 主题色
     */
    private int colorMain;
    /**
     * 主题色按下的颜色
     */
    private int colorMainPress;
    /**
     * 按下的灰色颜色
     */
    private int colorGrayPress;
    /**
     * 边框颜色
     */
    private int colorStroke;
    /**
     * 边框大小
     */
    private int widthStroke;
    /**
     * 圆角半径
     */
    private int corner;
    /**
     * 标题栏高度
     */
    private int heightTitleBar;
    /**
     * 标题栏背景颜色
     */
    private int colorTitleBarBackground;
    /**
     * 标题栏item按下的颜色
     */
    private int colorTitleBarItemPressed;
    /**
     * 标题栏文字颜色
     */
    private int colorTitleText;

    public SDLibraryConfig()
    {
        setWidthStroke(1);
        setCorner(10);
        setColorGrayPress(Color.parseColor("#E5E5E5"));
        setColorStroke(Color.parseColor("#E5E5E5"));
        setColorMain(Color.parseColor("#FC7507"));
        setColorMainPress(Color.parseColor("#FFCC66"));
        setHeightTitleBar(80);
        setColorTitleBarBackground(Color.parseColor("#FC7507"));
        setColorTitleBarItemPressed(Color.parseColor("#FFCC66"));
        setColorTitleText(Color.parseColor("#FFFFFF"));
    }

    public int getColorTitleText()
    {
        return colorTitleText;
    }

    /**
     * 设置标题栏文字颜
     *
     * @param colorTitleText
     */
    public void setColorTitleText(int colorTitleText)
    {
        this.colorTitleText = colorTitleText;
    }

    public int getColorTitleBarItemPressed()
    {
        return colorTitleBarItemPressed;
    }

    /**
     * 设置标题栏item按下的颜色
     *
     * @param colorTitleBarItemPressed
     */
    public void setColorTitleBarItemPressed(int colorTitleBarItemPressed)
    {
        this.colorTitleBarItemPressed = colorTitleBarItemPressed;
    }

    public int getColorTitleBarBackground()
    {
        return colorTitleBarBackground;
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param colorTitleBarBackground
     */
    public void setColorTitleBarBackground(int colorTitleBarBackground)
    {
        this.colorTitleBarBackground = colorTitleBarBackground;
    }

    public int getHeightTitleBar()
    {
        return heightTitleBar;
    }

    /**
     * 设置标题栏高度
     *
     * @param heightTitleBar
     */
    public void setHeightTitleBar(int heightTitleBar)
    {
        this.heightTitleBar = heightTitleBar;
    }

    public int getCorner()
    {
        return corner;
    }

    /**
     * 设置圆角半径
     *
     * @param corner
     */
    public void setCorner(int corner)
    {
        this.corner = corner;
    }

    public int getColorStroke()
    {
        return colorStroke;
    }

    /**
     * 设置边框颜色
     *
     * @param colorStroke
     */
    public void setColorStroke(int colorStroke)
    {
        this.colorStroke = colorStroke;
    }

    public int getColorGrayPress()
    {
        return colorGrayPress;
    }

    /**
     * 设置按下的灰色颜色
     *
     * @param colorGrayPress
     */
    public void setColorGrayPress(int colorGrayPress)
    {
        this.colorGrayPress = colorGrayPress;
    }

    public int getWidthStroke()
    {
        return widthStroke;
    }

    /**
     * 设置边框大小
     *
     * @param widthStroke
     */
    public void setWidthStroke(int widthStroke)
    {
        this.widthStroke = widthStroke;
    }

    public int getColorMainPress()
    {
        return colorMainPress;
    }

    /**
     * 设置主题色按下的颜色
     *
     * @param colorMainPress
     */
    public void setColorMainPress(int colorMainPress)
    {
        this.colorMainPress = colorMainPress;
    }

    public int getColorMain()
    {
        return colorMain;
    }

    /**
     * 设置主题色
     *
     * @param colorMain
     */
    public void setColorMain(int colorMain)
    {
        this.colorMain = colorMain;
    }

}
