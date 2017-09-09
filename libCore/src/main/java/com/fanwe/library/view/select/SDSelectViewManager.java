package com.fanwe.library.view.select;

import android.view.View;
import android.view.View.OnClickListener;

import com.fanwe.library.common.SDSelectManager;

public class SDSelectViewManager<T extends View> extends SDSelectManager<T>
{

    private boolean mClickAble = true;

    @Override
    protected void initItem(int index, T item)
    {
        item.setOnClickListener(mOnClickListener);
        notifyNormal(index, item);
        super.initItem(index, item);
    }

    private OnClickListener mOnClickListener = new OnClickListener()
    {

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View v)
        {
            if (mClickAble)
            {
                performClick((T) v);
            }
        }
    };

    public boolean isClickAble()
    {
        return mClickAble;
    }

    public void setClickAble(boolean clickAble)
    {
        mClickAble = clickAble;
    }

    @Override
    protected void notifyNormal(int index, T item)
    {
        item.setSelected(false);
        super.notifyNormal(index, item);
    }

    @Override
    protected void notifySelected(int index, T item)
    {
        item.setSelected(true);
        super.notifySelected(index, item);
    }

}
