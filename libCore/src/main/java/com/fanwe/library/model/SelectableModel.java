package com.fanwe.library.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by Administrator on 2017/5/15.
 */
public class SelectableModel implements SDSelectManager.Selectable
{
    private boolean selected;

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
