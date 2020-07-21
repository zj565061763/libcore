package com.sd.libcore.activity;

import android.os.Bundle;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.business.holder.FActivityBusinessHolder;
import com.sd.libcore.business.holder.FBusinessHolder;

public abstract class FStreamActivity extends FActivity implements FStream
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FStreamManager.getInstance().register(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FStreamManager.getInstance().unregister(this);
    }

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz)
    {
        return getStreamTag();
    }

    public final String getStreamTag()
    {
        return FStreamActivity.this.toString();
    }

    public final FBusinessHolder getBusinessHolder()
    {
        return FActivityBusinessHolder.with(this);
    }
}
