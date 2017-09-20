package com.fanwe.library.title;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewBinder;

public class SDTitleItem extends LinearLayout
{
    public View mView;
    public ImageView mIvLeft;
    public ImageView mIvRight;
    public TextView mTvTop;
    public TextView mTvBot;
    public LinearLayout mLlText;
    private Drawable mBackgroundDrawable;
    private OnClickListener mListenerOnClick;

    public SDTitleItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDTitleItem(Context context)
    {
        this(context, null);
    }

    private void init()
    {
        this.setGravity(Gravity.CENTER);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mView = LayoutInflater.from(getContext()).inflate(R.layout.title_item, null);
        this.addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mIvLeft = (ImageView) findViewById(R.id.title_item_iv_left);
        mIvRight = (ImageView) findViewById(R.id.title_item_iv_right);

        mLlText = (LinearLayout) findViewById(R.id.title_item_ll_text);
        mTvTop = (TextView) findViewById(R.id.title_item_tv_top);
        mTvBot = (TextView) findViewById(R.id.title_item_tv_bot);

        setDefaultConfig();

        setAllViewsVisibility(View.GONE);
    }

    private void setDefaultConfig()
    {
        int titleTextColor = getResources().getColor(R.color.res_text_title_bar);
        mTvTop.setTextColor(titleTextColor);
        mTvBot.setTextColor(titleTextColor);
    }

    public void setAllViewsVisibility(int visibility)
    {
        mIvLeft.setVisibility(visibility);
        mIvRight.setVisibility(visibility);

        mLlText.setVisibility(visibility);
        mTvTop.setVisibility(visibility);
        mTvBot.setVisibility(visibility);
        dealClickListener();
    }

    public SDTitleItem setTextTop(String text)
    {
        SDViewBinder.setTextViewVisibleOrGone(mTvTop, text);
        dealClickListener();
        return this;
    }

    public SDTitleItem setTextBot(String text)
    {
        SDViewBinder.setTextViewVisibleOrGone(mTvBot, text);
        dealClickListener();
        return this;
    }

    public SDTitleItem setBackgroundText(int resId)
    {
        mLlText.setBackgroundResource(resId);
        return this;
    }

    public SDTitleItem setImageLeft(int resId)
    {
        if (resId == 0)
        {
            mIvLeft.setVisibility(View.GONE);
        } else
        {
            mIvLeft.setVisibility(View.VISIBLE);
            mIvLeft.setImageResource(resId);
        }
        dealClickListener();
        return this;
    }

    public SDTitleItem setImageRight(int resId)
    {
        if (resId == 0)
        {
            mIvRight.setVisibility(View.GONE);
        } else
        {
            mIvRight.setVisibility(View.VISIBLE);
            mIvRight.setImageResource(resId);
        }
        dealClickListener();
        return this;
    }

    public boolean hasViewVisible()
    {
        if (mIvLeft.getVisibility() == View.VISIBLE || mTvTop.getVisibility() == View.VISIBLE || mTvBot.getVisibility() == View.VISIBLE
                || mIvRight.getVisibility() == View.VISIBLE)
        {
            if (mTvTop.getVisibility() == View.VISIBLE || mTvBot.getVisibility() == View.VISIBLE)
            {
                mLlText.setVisibility(View.VISIBLE);
            } else
            {
                mLlText.setVisibility(View.GONE);
            }
            return true;
        } else
        {
            return false;
        }
    }

    private void dealClickListener()
    {
        if (hasViewVisible())
        {
            setBackgroundDrawableSaved();
            super.setOnClickListener(mListenerOnClick);
        } else
        {
            setBackgroundTransparent();
            super.setOnClickListener(null);
        }
    }

    private void setBackgroundDrawableSaved()
    {
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int left = getPaddingLeft();
        int right = getPaddingRight();
        super.setBackgroundDrawable(mBackgroundDrawable);
        setPadding(left, top, right, bottom);
    }

    private void setBackgroundTransparent()
    {
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int left = getPaddingLeft();
        int right = getPaddingRight();
        super.setBackgroundDrawable(null);
        setPadding(left, top, right, bottom);
    }

    @Override
    public void setOnClickListener(OnClickListener l)
    {
        this.mListenerOnClick = l;
        super.setOnClickListener(l);
        dealClickListener();
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background)
    {
        this.mBackgroundDrawable = background;
        super.setBackgroundDrawable(background);
    }

}
