package com.sd.libcore.business;

import com.sd.lib.stream.FStream;
import com.sd.libcore.business.stream.BSProgress;

public abstract class FBusiness
{
    private final String mTag;

    public FBusiness(String tag)
    {
        mTag = tag;
        onCreate();
    }

    public String getStreamTag()
    {
        return mTag;
    }

    protected final <T extends FStream> T getStream(Class<T> clazz)
    {
        return new FStream.ProxyBuilder().setTag(getStreamTag()).build(clazz);
    }

    protected BSProgress getProgress()
    {
        return getStream(BSProgress.class);
    }

    public void onCreate()
    {
    }

    public void onDestroy()
    {
    }
}
