package com.fanwe.library.adapter.iml;

import android.app.Activity;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.model.SelectableModel;

/**
 * 指示器适配器
 */
public abstract class SDSimpleIndicatorAdapter extends SDSimpleAdapter<SelectableModel>
{
    public SDSimpleIndicatorAdapter(Activity activity)
    {
        super(null, activity);
    }

    /**
     * 更新指示器数量
     *
     * @param count
     */
    public void updateIndicatorCount(int count)
    {
        int delta = count - getDataCount();
        if (delta < 0)
        {
            //数量变少
            for (int i = 0; i < Math.abs(delta); i++)
            {
                removeData(0);
            }
        } else if (delta == 0)
        {
            //数量不变
        } else
        {
            //数量变多
            for (int i = 0; i < delta; i++)
            {
                appendData(new SelectableModel());
            }
        }
    }
}
