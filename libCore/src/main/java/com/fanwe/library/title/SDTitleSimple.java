package com.fanwe.library.title;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;

import com.fanwe.library.R;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;

import java.util.ArrayList;
import java.util.List;

public class SDTitleSimple extends LinearLayout implements OnClickListener
{
    public View mView;
    public SDTitleItem mTitleLeft;
    public SDTitleItem mTitleMiddle;
    public LinearLayout mLlLeft;
    public LinearLayout mLlMiddle;
    public LinearLayout mLlRight;

    private int mWidthRight;
    private int mWidthMiddle;
    private int mWidthLeft;

    private int mBackgroundItemResource;
    private SDTitleSimpleListener mListener;

    private List<SDTitleItem> mListRightItem = new ArrayList<SDTitleItem>();

    public void setmListener(SDTitleSimpleListener listener)
    {
        this.mListener = listener;
    }

    public List<SDTitleItem> getmListRightItem()
    {
        return mListRightItem;
    }

    public SDTitleSimple(Context context)
    {
        this(context, null);
    }

    public SDTitleSimple(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.title_simple, null);
        this.addView(mView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        mTitleLeft = (SDTitleItem) findViewById(R.id.title_left);
        mTitleMiddle = (SDTitleItem) findViewById(R.id.title_middle);
        mLlLeft = (LinearLayout) findViewById(R.id.ll_left);
        mLlMiddle = (LinearLayout) findViewById(R.id.ll_middle);
        mLlRight = (LinearLayout) findViewById(R.id.ll_right);

        mTitleLeft.setOnClickListener(this);
        mTitleMiddle.setOnClickListener(this);
        setDefaultConfig();
        notifyItemBackgroundChanged();
        addLayoutListener();
    }

    private void addLayoutListener()
    {
        mLlLeft.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                int newWidth = mLlLeft.getWidth();
                if (mWidthLeft != newWidth)
                {
                    mWidthLeft = newWidth;
                    changeViewOnLayoutChange();
                }
            }
        });
        mLlMiddle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                int newWidth = mLlMiddle.getWidth();
                if (mWidthMiddle != newWidth)
                {
                    mWidthMiddle = newWidth;
                    changeViewOnLayoutChange();
                }
            }
        });
        mLlRight.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                int newWidth = mLlRight.getWidth();
                if (mWidthRight != newWidth)
                {
                    mWidthRight = newWidth;
                    changeViewOnLayoutChange();
                }
            }
        });
    }

    protected void changeViewOnLayoutChange()
    {
        int width = 0;
        if (mWidthRight >= mWidthLeft)
        {
            width = mWidthRight;
        } else
        {
            width = mWidthLeft;
        }

        int maxMiddleWidth = ((SDViewUtil.getWidth(this) / 2) - width) * 2;

        if (mWidthMiddle > maxMiddleWidth)
        {
            SDViewUtil.setWidth(mLlMiddle, maxMiddleWidth);
        } else
        {

        }
    }

    private void setDefaultConfig()
    {
        setBackgroundColor(getResources().getColor(R.color.res_bg_title_bar));
        setHeightTitle(getResources().getDimensionPixelSize(R.dimen.res_height_title_bar));
    }

    public SDTitleSimple setHeightTitle(int height)
    {
        SDViewUtil.setHeight(mView, height);
        return this;
    }

    private Drawable getBackgroundItem()
    {
        SDDrawable none = new SDDrawable();
        none.color(getResources().getColor(R.color.res_bg_title_bar));

        SDDrawable pressed = new SDDrawable();
        pressed.color(getResources().getColor(R.color.res_bg_title_bar_pressed));

        return SDDrawable.getStateListDrawable(none, null, null, pressed);
    }

    public SDTitleSimple initRightItem(int count)
    {
        mLlRight.removeAllViews();
        mListRightItem.clear();
        if (count <= 0)
        {
            return this;
        }

        for (int i = 0; i < count; i++)
        {
            addItemRight();
        }
        notifyItemBackgroundChanged();
        return this;
    }

    public SDTitleItem addItemRight()
    {
        SDTitleItem item = new SDTitleItem(getContext());
        item.setOnClickListener(this);
        if (mBackgroundItemResource != 0)
        {
            SDViewUtil.setBackgroundResource(item, mBackgroundItemResource);
        } else
        {
            SDViewUtil.setBackgroundDrawable(item, getBackgroundItem());
        }
        LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLlRight.addView(item, params);
        mListRightItem.add(item);
        return item;
    }

    public void setmBackgroundItemResource(int resId)
    {
        this.mBackgroundItemResource = resId;
        notifyItemBackgroundChanged();
    }

    private void notifyItemBackgroundChanged()
    {
        if (mBackgroundItemResource != 0)
        {
            SDViewUtil.setBackgroundResource(mTitleLeft, mBackgroundItemResource);
            for (SDTitleItem item : mListRightItem)
            {
                SDViewUtil.setBackgroundResource(item, mBackgroundItemResource);
            }
        } else
        {
            setDefaultItemBackground();
        }
    }

    private void setDefaultItemBackground()
    {
        SDViewUtil.setBackgroundDrawable(mTitleLeft, getBackgroundItem());
        for (SDTitleItem item : mListRightItem)
        {
            SDViewUtil.setBackgroundDrawable(item, getBackgroundItem());
        }
    }

    public SDTitleItem getItemRight(int index)
    {
        return SDCollectionUtil.get(mListRightItem, index);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mTitleLeft)
        {
            clickLeft(v);
        } else if (v == mTitleMiddle)
        {
            clickMiddle(v);
        } else
        {
            clickRight(v);
        }

    }

    private void clickLeft(View v)
    {
        if (mListener != null)
        {
            mListener.onCLickLeft_SDTitleSimple(mTitleLeft);
        }
    }

    private void clickMiddle(View v)
    {
        if (mListener != null)
        {
            mListener.onCLickMiddle_SDTitleSimple(mTitleMiddle);
        }
    }

    private void clickRight(View v)
    {
        if (mListener != null)
        {
            int index = mListRightItem.indexOf(v);
            if (index >= 0)
            {
                mListener.onCLickRight_SDTitleSimple(mListRightItem.get(index), index);
            }
        }
    }

    // ---------------------setCustomView
    public SDTitleSimple setCustomViewLeft(View view)
    {
        mLlLeft.removeAllViews();
        if (view != null)
        {
            mLlLeft.addView(view);
        }
        return this;
    }

    public SDTitleSimple setCustomViewLeft(int resId)
    {
        View view = LayoutInflater.from(getContext()).inflate(resId, null);
        setCustomViewLeft(view);
        return this;
    }

    public SDTitleSimple setCustomViewMiddle(View view)
    {
        mLlMiddle.removeAllViews();
        if (view != null)
        {
            mLlMiddle.addView(view);
        }
        return this;
    }

    public SDTitleSimple setCustomViewMiddle(int resId)
    {
        View view = LayoutInflater.from(getContext()).inflate(resId, null);
        setCustomViewMiddle(view);
        return this;
    }

    public SDTitleSimple setCustomViewRight(View view)
    {
        mLlRight.removeAllViews();
        if (view != null)
        {
            mLlRight.addView(view);
        }
        return this;
    }

    public SDTitleSimple setCustomViewRight(int resId)
    {
        View view = LayoutInflater.from(getContext()).inflate(resId, null);
        setCustomViewRight(view);
        return this;
    }

    // friendly method
    public SDTitleSimple setMiddleTextTop(String text)
    {
        mTitleMiddle.setTextTop(text);
        return this;
    }

    public SDTitleSimple setMiddleTextBot(String text)
    {
        mTitleMiddle.setTextBot(text);
        return this;
    }

    public SDTitleSimple setMiddleImageLeft(int resId)
    {
        mTitleMiddle.setImageLeft(resId);
        return this;
    }

    public SDTitleSimple setMiddleImageRight(int resId)
    {
        mTitleMiddle.setImageRight(resId);
        return this;
    }

    public SDTitleSimple setMiddleBackgroundText(int resId)
    {
        mTitleMiddle.setBackgroundText(resId);
        return this;
    }

    public SDTitleSimple setLeftTextTop(String text)
    {
        mTitleLeft.setTextTop(text);
        return this;
    }

    public SDTitleSimple setLeftTextBot(String text)
    {
        mTitleLeft.setTextBot(text);
        return this;
    }

    public SDTitleSimple setLeftImageLeft(int resId)
    {
        mTitleLeft.setImageLeft(resId);
        return this;
    }

    public SDTitleSimple setLeftImageRight(int resId)
    {
        mTitleLeft.setImageRight(resId);
        return this;
    }

    public SDTitleSimple setLeftBackgroundText(int resId)
    {
        mTitleLeft.setBackgroundText(resId);
        return this;
    }

    public interface SDTitleSimpleListener
    {
        public void onCLickLeft_SDTitleSimple(SDTitleItem v);

        public void onCLickMiddle_SDTitleSimple(SDTitleItem v);

        public void onCLickRight_SDTitleSimple(SDTitleItem v, int index);
    }

}
