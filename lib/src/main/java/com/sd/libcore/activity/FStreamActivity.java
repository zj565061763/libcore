package com.sd.libcore.activity;

import android.os.Bundle;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.business.holder.FActivityBusinessHolder;
import com.sd.libcore.business.holder.FBusinessHolder;
import com.sd.libcore.business.stream.StreamActivityBackPressed;

import java.lang.reflect.Method;

public abstract class FStreamActivity extends FActivity implements FStream
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FStreamManager.getInstance().register(this);
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

    @Override
    public void onBackPressed()
    {
        final StreamActivityBackPressed stream = new ProxyBuilder()
                .setDispatchCallback(new DispatchCallback()
                {
                    @Override
                    public boolean beforeDispatch(FStream stream, Method method, Object[] methodParams)
                    {
                        return false;
                    }

                    @Override
                    public boolean afterDispatch(FStream stream, Method method, Object[] methodParams, Object methodResult)
                    {
                        if (Boolean.TRUE.equals(methodResult))
                            return true;
                        return false;
                    }
                })
                .setTag(getStreamTag())
                .build(StreamActivityBackPressed.class);

        if (stream.onActivityBackPressed())
            return;

        super.onBackPressed();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FStreamManager.getInstance().unregister(this);
    }
}
