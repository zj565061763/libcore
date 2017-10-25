package com.fanwe.library.event;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface SDEventObserver
{
    void onEventMainThread(SDEvent event);
}
