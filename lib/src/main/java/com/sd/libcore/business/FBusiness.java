package com.sd.libcore.business;

import android.support.annotation.CallSuper;

import com.sd.lib.stream.FStream;
import com.sd.libcore.business.stream.BSProgress;

public abstract class FBusiness
{
    private final String mTag;

    public FBusiness(String tag)
    {
        mTag = tag;
    }

    public final String getTag()
    {
        return mTag;
    }

    protected final <T extends FStream> T getStream(Class<T> clazz)
    {
        return new FStream.ProxyBuilder().setTag(getTag()).build(clazz);
    }

    protected final BSProgress getProgress()
    {
        return getStream(BSProgress.class);
    }

    @CallSuper
    public void onDestroy()
    {
    }
}
