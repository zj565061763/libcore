package com.fanwe.library.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.fanwe.library.utils.SDViewUtil;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SDMoreLinearLayout extends SDGridLinearLayout
{

    private boolean mIsOpen = false;
    private int mMaxShowCount = 0;
    private int mViewMoreLayoutId;
    private OnClickListener mListenerOnClickViewMore;
    private OnOpenCloseListener mListenerOnOpenClose;
    private View mViewMore;

    private List<View> mListView = new ArrayList<View>();

    public SDMoreLinearLayout(Context context)
    {
        this(context, null);
    }

    public SDMoreLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setmListenerOnOpenClose(OnOpenCloseListener listenerOnOpenClose)
    {
        this.mListenerOnOpenClose = listenerOnOpenClose;
    }

    public void setmListenerOnClickViewMore(OnClickListener listenerOnClickViewMore)
    {
        this.mListenerOnClickViewMore = listenerOnClickViewMore;
    }

    public void setmMaxShowCount(int maxShowCount)
    {
        if (this.mMaxShowCount != maxShowCount)
        {
            this.mMaxShowCount = maxShowCount;
            notifyDataSetChanged();
        }
    }

    public void setmViewMoreLayoutId(int viewMoreLayoutId)
    {
        this.mViewMoreLayoutId = viewMoreLayoutId;
        initMoreView();
    }

    public View getmViewMore()
    {
        return mViewMore;
    }

    public void setmIsOpen(boolean isOpen)
    {
        this.mIsOpen = isOpen;
    }

    public boolean isOpen()
    {
        return mIsOpen;
    }

    public void open()
    {
        if (!mListView.isEmpty())
        {
            for (View view : mListView)
            {
                SDViewUtil.setVisible(view);
            }
            mIsOpen = true;
            notifyOpen();
        }
    }

    public void close()
    {
        if (!mListView.isEmpty())
        {
            for (View view : mListView)
            {
                SDViewUtil.setGone(view);
            }
            mIsOpen = false;
            notifyClose();
        }
    }

    private void notifyOpen()
    {
        if (mListenerOnOpenClose != null && !mListView.isEmpty())
        {
            mListenerOnOpenClose.onOpen(mListView, mViewMore);
        }
    }

    private void notifyClose()
    {
        if (mListenerOnOpenClose != null && !mListView.isEmpty())
        {
            mListenerOnOpenClose.onClose(mListView, mViewMore);
        }
    }

    public void toggle()
    {
        if (mIsOpen)
        {
            close();
        } else
        {
            open();
        }
    }

    @Override
    protected void create()
    {
        mListView.clear();
        super.create();
        initMoreView();
    }

    private void initMoreView()
    {
        if (mListView.size() > 0)
        {
            if (mViewMoreLayoutId != 0)
            {
                // 先移除
                removeMoreView();
                mViewMore = LayoutInflater.from(getContext()).inflate(mViewMoreLayoutId, null);
                this.addView(mViewMore);
                if (mListenerOnClickViewMore != null)
                {
                    mViewMore.setOnClickListener(mListenerOnClickViewMore);
                } else
                {
                    mViewMore.setOnClickListener(mListenerOnClickViewMoreDefault);
                }
                if (mIsOpen)
                {
                    notifyOpen();
                } else
                {
                    notifyClose();
                }
            } else
            {
                removeMoreView();
                notifyOpen();
            }
        } else
        {
            removeMoreView();
        }
    }

    private void removeMoreView()
    {
        if (mViewMore != null)
        {
            this.removeView(mViewMore);
        }
    }

    private OnClickListener mListenerOnClickViewMoreDefault = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            toggle();
        }
    };

    @Override
    protected void wrapperItemView(int i, View itemView)
    {
        if (mMaxShowCount > 0 && i >= mMaxShowCount)
        {
            mListView.add(itemView);
            if (mIsOpen)
            {
                SDViewUtil.setVisible(itemView);
            } else
            {
                SDViewUtil.setGone(itemView);
            }
        }
        super.wrapperItemView(i, itemView);
    }

    public interface OnOpenCloseListener
    {
        public void onOpen(List<View> listView, View viewMore);

        public void onClose(List<View> listView, View viewMore);
    }

}
