package com.sd.libcore.activity;

import com.sd.libcore.business.holder.FActivityBusinessHolder;
import com.sd.libcore.business.holder.FBusinessHolder;

public abstract class FStreamActivity extends FActivity
{
    public final String getStreamTag()
    {
        return FStreamActivity.this.toString();
    }

    public final FBusinessHolder getBusinessHolder()
    {
        return FActivityBusinessHolder.with(this);
    }
}
