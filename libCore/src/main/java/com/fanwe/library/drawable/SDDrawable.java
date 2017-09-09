package com.fanwe.library.drawable;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

public class SDDrawable extends LayerDrawable
{

    private static final int DEFAULT_COLOR = Color.parseColor("#ffffff");

    private GradientDrawable mDrawableFirst;
    private GradientDrawable mDrawableSecond;

    private int mStrokeWidthLeft;
    private int mStrokeWidthTop;
    private int mStrokeWidthRight;
    private int mStrokeWidthBottom;

    private float mCornerTopLeft;
    private float mCornerTopRight;
    private float mCornerBottomLeft;
    private float mCornerBottomRight;

    private int mWidth = -1;
    private int mHeight = -1;

    public SDDrawable()
    {
        this(new GradientDrawable[]{new GradientDrawable(), new GradientDrawable()});
    }

    public SDDrawable(Drawable[] layers)
    {
        super(layers);
        init();
    }

    private void init()
    {
        mDrawableFirst = (GradientDrawable) this.getDrawable(0);
        mDrawableSecond = (GradientDrawable) this.getDrawable(1);

        mDrawableFirst.setShape(GradientDrawable.RECTANGLE);
        mDrawableSecond.setShape(GradientDrawable.RECTANGLE);
        color(DEFAULT_COLOR);
    }

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    public SDDrawable width(int width)
    {
        mWidth = width;
        updateSize();
        return this;
    }

    /**
     * 设置高度
     *
     * @param height
     * @return
     */
    public SDDrawable height(int height)
    {
        mHeight = height;
        updateSize();
        return this;
    }

    /**
     * 设置宽高
     *
     * @param size
     * @return
     */
    public SDDrawable size(int size)
    {
        mWidth = size;
        mHeight = size;
        updateSize();
        return this;
    }

    private void updateSize()
    {
        mDrawableFirst.setSize(mWidth, mHeight);
        mDrawableSecond.setSize(mWidth, mHeight);
    }

    /**
     * 透明度
     *
     * @param alpha
     * @return
     */
    public SDDrawable alpha(int alpha)
    {
        setAlpha(alpha);
        return this;
    }

    /**
     * 图片颜色
     *
     * @param color
     * @return
     */
    public SDDrawable color(int color)
    {
        mDrawableSecond.setColor(color);
        return this;
    }

    /**
     * 边框颜色
     *
     * @param color
     * @return
     */
    public SDDrawable strokeColor(int color)
    {
        mDrawableFirst.setColor(color);
        return this;
    }

    /**
     * 设置圆角
     *
     * @param topLeft
     * @param topRight
     * @param bottomLeft
     * @param bottomRight
     * @return
     */
    public SDDrawable corner(float topLeft, float topRight, float bottomLeft, float bottomRight)
    {
        mCornerTopLeft = topLeft;
        mCornerTopRight = topRight;
        mCornerBottomLeft = bottomLeft;
        mCornerBottomRight = bottomRight;

        mDrawableFirst.setCornerRadii(new float[]{mCornerTopLeft, mCornerTopLeft, mCornerTopRight, mCornerTopRight, mCornerBottomRight,
                mCornerBottomRight, mCornerBottomLeft, mCornerBottomLeft});
        mDrawableSecond.setCornerRadii(new float[]{mCornerTopLeft, mCornerTopLeft, mCornerTopRight, mCornerTopRight, mCornerBottomRight,
                mCornerBottomRight, mCornerBottomLeft, mCornerBottomLeft});
        return this;
    }

    public SDDrawable cornerAll(float radius)
    {
        corner(radius, radius, radius, radius);
        return this;
    }

    public SDDrawable cornerTopLeft(float radius)
    {
        corner(radius, mCornerTopRight, mCornerBottomLeft, mCornerBottomRight);
        return this;
    }

    public SDDrawable cornerTopRight(float radius)
    {
        corner(mCornerTopLeft, radius, mCornerBottomLeft, mCornerBottomRight);
        return this;
    }

    public SDDrawable cornerBottomLeft(float radius)
    {
        corner(mCornerTopLeft, mCornerTopRight, radius, mCornerBottomRight);
        return this;
    }

    public SDDrawable cornerBottomRight(float radius)
    {
        corner(mCornerTopLeft, mCornerTopRight, mCornerBottomLeft, radius);
        return this;
    }

    /**
     * 边框宽度
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public SDDrawable strokeWidth(int left, int top, int right, int bottom)
    {
        mStrokeWidthLeft = left;
        mStrokeWidthTop = top;
        mStrokeWidthRight = right;
        mStrokeWidthBottom = bottom;
        setLayerInset(1, mStrokeWidthLeft, mStrokeWidthTop, mStrokeWidthRight, mStrokeWidthBottom);
        return this;
    }

    public SDDrawable strokeWidthAll(int width)
    {
        strokeWidth(width, width, width, width);
        return this;
    }

    public SDDrawable strokeWidthLeft(int width)
    {
        strokeWidth(width, mStrokeWidthTop, mStrokeWidthRight, mStrokeWidthBottom);
        return this;
    }

    public SDDrawable strokeWidthTop(int width)
    {
        strokeWidth(mStrokeWidthLeft, width, mStrokeWidthRight, mStrokeWidthBottom);
        return this;
    }

    public SDDrawable strokeWidthRight(int width)
    {
        strokeWidth(mStrokeWidthLeft, mStrokeWidthTop, width, mStrokeWidthBottom);
        return this;
    }

    public SDDrawable strokeWidthBottom(int width)
    {
        strokeWidth(mStrokeWidthLeft, mStrokeWidthTop, mStrokeWidthRight, width);
        return this;
    }

    // -------------------------------StateListDrawable

    /**
     * 获得可以根据状态变化的drawable
     *
     * @param normal   正常drawable
     * @param focus    获得焦点drawable
     * @param selected 选中drawable
     * @param pressed  按下drawable
     * @return
     */
    public static StateListDrawable getStateListDrawable(Drawable normal, Drawable focus, Drawable selected, Drawable pressed)
    {
        StateListDrawable stateListDrawable = new StateListDrawable();
        if (normal != null)
        {
            stateListDrawable.addState(getStateNormal(), normal);
        }
        if (focus != null)
        {
            stateListDrawable.addState(getStateFocus(), focus);
        }
        if (selected != null)
        {
            stateListDrawable.addState(getStateSelected(), selected);
        }
        if (pressed != null)
        {
            stateListDrawable.addState(getStatePressed(), pressed);
        }
        return stateListDrawable;
    }

    public static ColorStateList getStateListColor(int normal, int pressed)
    {
        return getStateListColor(normal, 0, 0, pressed);
    }

    /**
     * 获得可以根据状态变化的ColorStateList
     *
     * @param normal   正常颜色
     * @param focus    获得焦点颜色
     * @param selected 选中颜色
     * @param pressed  按下颜色
     * @return
     */
    public static ColorStateList getStateListColor(int normal, int focus, int selected, int pressed)
    {
        int[][] states = new int[4][];
        states[0] = getStateNormal();
        states[1] = getStateFocus();
        states[2] = getStateSelected();
        states[3] = getStatePressed();

        int[] colors = new int[4];

        int defaultColor = Color.BLACK;
        if (normal == 0)
        {
            normal = defaultColor;
        } else
        {
            defaultColor = normal;
        }

        if (focus == 0)
        {
            focus = defaultColor;
        }

        if (selected == 0)
        {
            selected = defaultColor;
        }

        if (pressed == 0)
        {
            pressed = defaultColor;
        }

        colors[0] = normal;
        colors[1] = focus;
        colors[2] = selected;
        colors[3] = pressed;

        ColorStateList colorStateList = new ColorStateList(states, colors);

        return colorStateList;
    }

    // -------------------------------States
    public static int[] getStateNormal()
    {
        return new int[]{-android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed};
    }

    public static int[] getStateFocus()
    {
        return new int[]{android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed};
    }

    public static int[] getStateSelected()
    {
        return new int[]{-android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed};
    }

    public static int[] getStatePressed()
    {
        return new int[]{-android.R.attr.state_focused, -android.R.attr.state_selected, android.R.attr.state_pressed};
    }

}
