package com.fanwe.library.event;

/**
 * Created by Administrator on 2017/10/25.
 */
public final class SDEvent
{
    public Object data;
    public String tag;

    public SDEvent()
    {
    }

    public SDEvent(Object data, String tag)
    {
        this.data = data;
        this.tag = tag;
    }
}
